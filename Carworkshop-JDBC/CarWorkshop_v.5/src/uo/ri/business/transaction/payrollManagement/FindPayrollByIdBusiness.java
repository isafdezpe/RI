package uo.ri.business.transaction.payrollManagement;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.PayrollDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.PayrollGateway;

public class FindPayrollByIdBusiness {
	
	private Long id;
	private Connection c;
	private PayrollGateway payrollGateway;

	public FindPayrollByIdBusiness(Long id) {
		this.id = id;
	}

	/**
	 * @return nómina con el id dado,
	 * null si no existe
	 * @throws BusinessException
	 */
	public PayrollDto execute() throws BusinessException {
		PayrollDto payroll = new PayrollDto();
		
		try {
			c = Jdbc.getConnection();
			
			payrollGateway = PersistenceFactory.getPayrollGateway();
			payrollGateway.setConnection(c);
			
			payroll = payrollGateway.findById(id);
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
		
		return payroll;
	}

}
