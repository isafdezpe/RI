package uo.ri.business.transaction.mechanicManagement;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.MechanicGateway;

public class DeleteMechanicBusiness {
	
	private Long idMecanico;
	private Connection c;
	private MechanicGateway mechanicGateway;
	
	public DeleteMechanicBusiness(Long mechanicId) {
		this.idMecanico = mechanicId;
	}

	/**
	 * Elimina un mecánico
	 * @throws BusinessException si:
	 * 		el mecánico no existe
	 * 		el mecánico tiene actividades o contratos asignados 
	 */
	public void execute() throws BusinessException {
		try {
			c = Jdbc.getConnection();
			c.setAutoCommit(false);

			mechanicGateway = PersistenceFactory.getMechanicGateway();
			mechanicGateway.setConnection(c);
			
			if (!mechanicGateway.canBeDeleted(idMecanico))
				throw new BusinessException(
						"No se ha podido eliminar el mecánico");
			
			mechanicGateway.delete(idMecanico);
			c.commit();
		} catch (SQLException | RuntimeException e) {
			try {
				if (c != null) {
					c.rollback();
				} 
			}catch (SQLException e2) {
			};
			throw new BusinessException("No se ha podido eliminar el mecánico");
		} finally {
			Jdbc.close(c);
		}
	}

}
