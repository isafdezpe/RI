package uo.ri.business.transaction.contractManagement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import alb.util.date.Dates;
import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.ContractGateway;

public class FinishContractBusiness {
	
	private Long idContrato;
	private Date endDate;
	private Connection c;
	private ContractGateway contractGateway;

	public FinishContractBusiness(Long id, Date endDate) {
		this.idContrato = id;
		this.endDate = Dates.lastDayOfMonth(endDate);
	}

	/**
	 * Finaliza un contrato de un mecánico, calculando la compensación.
	 * @throws BusinessException si:
	 * 		el contrato no existe
	 *      el contrato no está activo
	 *      la fecha de fin es anterior a la de inicio
	 */
	public void execute() throws BusinessException {
		try {
			c = Jdbc.getConnection();
			c.setAutoCommit(false);
			
			contractGateway = PersistenceFactory.getContractGateway();
			contractGateway.setConnection(c);
			
			ContractDto contract = contractGateway.findById(idContrato);
			if (contract == null || contract.status.equals("EXTINTO")
					|| Dates.isAfter(contract.startDate, endDate))
				throw new BusinessException(
						"No se ha podido finalizar el contrato");
			
			int compensationDays = 
					contractGateway.getCompensationDaysContract(idContrato);
			
			double compensation;
			int diffMonths = Dates.diffDays(endDate, contract.startDate) / 30;
			if (diffMonths >= 12) {
				compensation = contract.yearBaseSalary / 365 
						* compensationDays * (diffMonths / 12);
			} else {
				compensation = 0;
			}
			
			contractGateway.finishContract(idContrato, endDate, compensation);
					
			c.commit();
		} catch (SQLException | RuntimeException e) {
			try {
				if (c != null) {
					c.rollback();
				} 
			}catch (SQLException e2) {
			};
			throw new BusinessException("No se ha podido finalizar el contrato");
		} finally {
			Jdbc.close(c);
		}
	}

}
