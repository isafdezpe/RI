package uo.ri.business.transaction.mechanicManagement;

import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.MechanicGateway;

public class ListMechanicBusiness {
	
	private Connection c;
	private MechanicGateway mechanicGateway;

	/**
	 * @return lista de todos los mecánicos del sistema,
	 * lista vací si no hay ninguno
	 * @throws BusinessException
	 */
	public List<MechanicDto> execute() throws BusinessException {
		List<MechanicDto> mechanics;
		
		try {
			c = Jdbc.getConnection();

			mechanicGateway = PersistenceFactory.getMechanicGateway();
			mechanicGateway.setConnection(c);

			mechanics = mechanicGateway.read();
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
		
		return mechanics;
	}

}
