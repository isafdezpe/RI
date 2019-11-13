package uo.ri.business.transaction.contractManagement;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.date.Dates;
import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.ContractGateway;

public class UpdateContractBusiness {
	
	private ContractDto contract;
	private Connection c;
	private ContractGateway contractGateway;

	public UpdateContractBusiness(ContractDto dto) {
		this.contract = dto;
	}

	/**
	 * Actualiza la fecha de fin y el salario base anual de un contrato activo
	 * @throws BusinessException si:
	 * 		el contrato no existe o no está activo
	 * 		el salario base anual es negativo
	 * 		la fecha de fin es anterior a la de inicio
	 */
	public void execute() throws BusinessException {
		if (contract.yearBaseSalary < 0 )
			throw new BusinessException("No se ha podido actualizar el contrato");
		
		try {
			c = Jdbc.getConnection();
			c.setAutoCommit(false);

			contractGateway = PersistenceFactory.getContractGateway();
			contractGateway.setConnection(c);
			
			ContractDto co = contractGateway.findById(contract.id);
			if (!co.status.equals("ACTIVO")
					|| contract.endDate != null 
							&& Dates.isAfter(co.startDate, contract.endDate))
				throw new BusinessException(
						"No se ha podido actualizar el contrato");

			contractGateway.update(contract);
			c.commit();
		} catch (SQLException | RuntimeException e) {
			try {
				if (c != null) {
					c.rollback();
				} 
			}catch (SQLException e2) {
			};
			throw new BusinessException("No se ha podido actualizar el contrato");
		} finally {
			Jdbc.close(c);
		}
	}

}
