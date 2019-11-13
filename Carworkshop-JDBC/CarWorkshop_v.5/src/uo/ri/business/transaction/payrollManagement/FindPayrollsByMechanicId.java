package uo.ri.business.transaction.payrollManagement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.PayrollDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.PayrollGateway;

public class FindPayrollsByMechanicId {
	
	private Long idMecanico;
	private Connection c;
	private PayrollGateway payrollGateway;

	public FindPayrollsByMechanicId(Long id) {
		this.idMecanico = id;
	}

	/**
	 * @return lista con todas las nóminas del mecánico con id dado,
	 * lista vacía si no hay ninguna
	 * @throws BusinessException
	 */
	public List<PayrollDto> execute() throws BusinessException {
		List<PayrollDto> payrolls;
		try {
			c = Jdbc.getConnection();
			
			payrollGateway = PersistenceFactory.getPayrollGateway();
			payrollGateway.setConnection(c);
			
			payrolls = payrollGateway.findByMechanic(idMecanico);
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
		
		return payrolls;
	}

}
