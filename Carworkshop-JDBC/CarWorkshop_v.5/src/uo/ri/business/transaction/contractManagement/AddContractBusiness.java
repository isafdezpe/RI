package uo.ri.business.transaction.contractManagement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import alb.util.date.Dates;
import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.ContractCategoryGateway;
import uo.ri.persistence.ContractGateway;
import uo.ri.persistence.ContractTypeGateway;
import uo.ri.persistence.MechanicGateway;

public class AddContractBusiness {
	
	private ContractDto contract;
	private Connection c;
	private ContractGateway contractGateway;
	private ContractCategoryGateway contractCategoryGateway;
	private ContractTypeGateway contractTypeGateway;
	private MechanicGateway mechanicGateway;

	public AddContractBusiness(ContractDto dto) {
		this.contract = dto;
	}

	/**
	 * Añade un contrato a un mecánico. Si el mecánico tenía un contrato activo,
	 * debe finalizarse.
	 * @throws BusinessException si:
	 * 		la categoria, el tipo de contrato o el mecánico no existe
	 * 		la fecha de fin es anterior a la de inicio
	 * 		el salario es negativo
	 */
	public void execute() throws BusinessException {
		if (contract.yearBaseSalary < 0)
			throw new BusinessException("No se ha podido insertar el contrato");
		
		if (contract.endDate != null) 
			if (Dates.isAfter(contract.startDate, contract.endDate))
				throw new BusinessException(
						"No se ha podido insertar el contrato");
		
		contract.startDate = Dates.firstDayOfMonth(contract.startDate);
		
		try {
			c = Jdbc.getConnection();
			c.setAutoCommit(false);

			contractGateway = PersistenceFactory.getContractGateway();
			contractGateway.setConnection(c);
			
			contractCategoryGateway = 
					PersistenceFactory.getContractCategoryGateway();
			contractCategoryGateway.setConnection(c);
			
			contractTypeGateway = PersistenceFactory.getContractTypeGateway();
			contractTypeGateway.setConnection(c);
			
			mechanicGateway = PersistenceFactory.getMechanicGateway();
			mechanicGateway.setConnection(c);
			
			if (contractCategoryGateway.findById(contract.categoryId) == null
					|| contractTypeGateway.findById(contract.typeId) == null
					|| mechanicGateway.findById(contract.mechanicId) == null)
				throw new BusinessException(
						"No se ha podido insertar el contrato");
			
			List<ContractDto> list = contractGateway
					.findContractsByMechanicId(contract.mechanicId);
			for (ContractDto co : list) {
				if (co.status.equals("ACTIVO")) {
					co.endDate = Dates.subMonths(contract.startDate, 1);
					co.endDate = Dates.lastDayOfMonth(co.endDate);
					new FinishContractBusiness(co.id, co.endDate).execute();
					break;
				}
			}

			contract.status = "ACTIVO";
			contractGateway.add(contract);
			c.commit();
		} catch (SQLException e) {
			try {
				if (c != null) {
					c.rollback();
				} 
			}catch (SQLException e2) {
			};
			throw new BusinessException("No se ha podido insertar el contrato");
		} finally {
			Jdbc.close(c);
		}
	}

}
