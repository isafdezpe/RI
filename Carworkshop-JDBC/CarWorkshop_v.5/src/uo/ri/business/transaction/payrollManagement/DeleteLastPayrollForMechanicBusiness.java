package uo.ri.business.transaction.payrollManagement;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.PayrollGateway;

public class DeleteLastPayrollForMechanicBusiness {
	
	private Long idMecanico;
	private Connection c;
	private PayrollGateway payrollGateway;

	public DeleteLastPayrollForMechanicBusiness(Long id) {
		this.idMecanico = id;
	}

	/**
	 * Elimina la última nómina del mecánico con id dado
	 * @throws BusinessException si:
	 * 		no hay nóminas para ese mecánico
	 */
	public void execute() throws BusinessException {
		try {
			c = Jdbc.getConnection();
			c.setAutoCommit(false);
			
			payrollGateway = PersistenceFactory.getPayrollGateway();
			payrollGateway.setConnection(c);
			
			payrollGateway.deleteLastPayrollForMechanic(idMecanico);
			
			c.commit();
		} catch (SQLException | RuntimeException e) {
			try {
				if (c != null) {
					c.rollback();
				} 
			}catch (SQLException e2) {
			};
			throw new BusinessException("No se ha podido eliminar la nómina");
		} finally {
			Jdbc.close(c);
		}
	}

}
