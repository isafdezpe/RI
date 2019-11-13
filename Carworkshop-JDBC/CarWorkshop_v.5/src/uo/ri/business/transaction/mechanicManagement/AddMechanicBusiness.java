package uo.ri.business.transaction.mechanicManagement;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.MechanicGateway;

public class AddMechanicBusiness {
	
	private MechanicDto mechanic;
	private Connection c;
	private MechanicGateway mechanicGateway;
	
	public AddMechanicBusiness(MechanicDto mechanic) {
		this.mechanic = mechanic;
	}

	/**
	 * Añade un nuevo mecánico en el sistema
	 * @throws BusinessException si:
	 * 		ya existe un mecánico con el mismo dni
	 */
	public void execute() throws BusinessException {
		try {
			c = Jdbc.getConnection();
			c.setAutoCommit(false);

			mechanicGateway = PersistenceFactory.getMechanicGateway();
			mechanicGateway.setConnection(c);

			mechanicGateway.add(mechanic);
			c.commit();
		} catch (SQLException e) {
			try {
				if (c != null) {
					c.rollback();
				} 
			}catch (SQLException e2) {
			};
			throw new BusinessException("No se ha podido insertar el mecánico");
		} finally {
			Jdbc.close(c);
		}
	}

}
