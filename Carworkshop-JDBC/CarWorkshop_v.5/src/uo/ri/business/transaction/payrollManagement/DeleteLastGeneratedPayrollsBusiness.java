package uo.ri.business.transaction.payrollManagement;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.PayrollGateway;

public class DeleteLastGeneratedPayrollsBusiness {
	
	private Connection c;
	private PayrollGateway payrollGateway;

	/**
	 * Elimina las últimas nóminas que se hayan generado
	 * @return número de nóminas eliminadas
	 * @throws BusinessException
	 */
	public int execute() throws BusinessException {
		int count;
		
		try {
			c = Jdbc.getConnection();
			c.setAutoCommit(false);
			
			payrollGateway = PersistenceFactory.getPayrollGateway();
			payrollGateway.setConnection(c);
			
			count = payrollGateway.deleteLastGeneratedPayrolls();
			
			c.commit();
		} catch (SQLException e) {
			try {
				if (c != null) {
					c.rollback();
				} 
			}catch (SQLException e2) {
			};
			throw new BusinessException(e.getMessage());
		} finally {
			Jdbc.close(c);
		}
		
		return count;
	}

}
