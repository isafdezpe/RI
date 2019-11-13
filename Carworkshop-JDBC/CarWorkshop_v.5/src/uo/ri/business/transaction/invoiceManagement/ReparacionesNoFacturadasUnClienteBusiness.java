package uo.ri.business.transaction.invoiceManagement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.BreakdownDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.BreakdownGateway;

public class ReparacionesNoFacturadasUnClienteBusiness {
	
	private String dni;
	private Connection c;
	private BreakdownGateway breakdownGateway;

	public ReparacionesNoFacturadasUnClienteBusiness(String dni) {
		this.dni = dni;
	}

	/**
	 * @return lista de las reparaciones no facturadas de un cliente,
	 * lista vacía si no hay ninguna
	 * @throws BusinessException
	 */
	public List<BreakdownDto> execute() throws BusinessException {
		List<BreakdownDto> breakdowns;
		
		try {
			c = Jdbc.getConnection();
			
			breakdownGateway = PersistenceFactory.getBreakdownGateway();
			breakdownGateway.setConnection(c);
			
			breakdowns = breakdownGateway.reparacionesNoFacturadasUnCliente(dni);	
		} catch (SQLException e) {
			try {
				if (c != null) {
					c.rollback();
				}
			} catch (SQLException ex) {
			}
			;
			throw new BusinessException(e.getMessage());
		} finally {
			Jdbc.close(c);
		}
		
		return breakdowns;
	}

}
