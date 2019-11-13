package uo.ri.business.transaction.contractManagement;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.ContractGateway;
import uo.ri.persistence.MechanicGateway;
import uo.ri.persistence.PayrollGateway;

public class DeleteContractBusiness {
	
	private Long idContrato;
	private Connection c;
	private ContractGateway contractGateway;
	private PayrollGateway payrollGateway;
	private MechanicGateway mechanicGateway;
	
	public DeleteContractBusiness(Long id) {
		this.idContrato = id;
	}

	/**
	 * Borra un contrato 
	 * @throws BusinessException si:
	 * 		el contrato no existe
	 * 		el mecánico ha realizado actividades durante la vigencia del contrato
	 * 		hay nóminas generadas para el contrato
	 */
	public void execute() throws BusinessException {
		try {
			c = Jdbc.getConnection();
			c.setAutoCommit(false);

			contractGateway = PersistenceFactory.getContractGateway();
			contractGateway.setConnection(c);
			
			payrollGateway = PersistenceFactory.getPayrollGateway();
			payrollGateway.setConnection(c);
			
			mechanicGateway = PersistenceFactory.getMechanicGateway();
			mechanicGateway.setConnection(c);
			
			ContractDto contract = contractGateway.findById(idContrato);
			if (contract == null)
				throw new BusinessException(
						"No se ha podido eliminar el contrato");
			
			if (payrollGateway.findByContract(idContrato).size() > 0
					|| mechanicGateway.hasActivities(contract.mechanicId,
							contract.startDate, contract.endDate))
				throw new BusinessException(
						"No se ha podido eliminar el contrato");
			
			contractGateway.delete(idContrato);
			c.commit();
		} catch (SQLException | RuntimeException e) {
			try {
				if (c != null) {
					c.rollback();
				} 
			}catch (SQLException e2) {
			};
			throw new BusinessException("No se ha podido eliminar el contrato");
		} finally {
			Jdbc.close(c);
		}
	}

}
