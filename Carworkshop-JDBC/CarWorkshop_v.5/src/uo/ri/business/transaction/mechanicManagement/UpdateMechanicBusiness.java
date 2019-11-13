package uo.ri.business.transaction.mechanicManagement;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.MechanicGateway;

public class UpdateMechanicBusiness {
	
	private MechanicDto mechanic;
	private Connection c;
	private MechanicGateway mechanicGateway;

	public UpdateMechanicBusiness(MechanicDto mechanic) {
		this.mechanic = mechanic;
	}

	/**
	 * Actualiza el nombre y los apellidos del mecánico con id dado
	 * @throws BusinessException si:
	 * 		el mecánico no existe
	 */
	public void execute() throws BusinessException {
		try {
			c = Jdbc.getConnection();
			c.setAutoCommit(false);

			mechanicGateway = PersistenceFactory.getMechanicGateway();
			mechanicGateway.setConnection(c);

			mechanicGateway.update(mechanic);
			c.commit();
		} catch (SQLException | RuntimeException e) {
			try {
				if (c != null) {
					c.rollback();
				} 
			}catch (SQLException e2) {
			};
			throw new BusinessException("No se ha podido actualizar el mecánico");
		} finally {
			Jdbc.close(c);
		}
	}

}
