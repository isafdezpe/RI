package uo.ri.business.transaction.mechanicManagement;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.MechanicGateway;

public class FindMechanicBusiness {
	
	private Long mechanicId;
	private Connection c;
	private MechanicGateway mechanicGateway;

	public FindMechanicBusiness(Long id) {
		this.mechanicId = id;
	}

	/**
	 * @return mecánico con id dado, null si no existe
	 * @throws BusinessException
	 */
	public MechanicDto execute() throws BusinessException {
		MechanicDto m;
		
		try {
			c = Jdbc.getConnection();

			mechanicGateway = PersistenceFactory.getMechanicGateway();
			mechanicGateway.setConnection(c);

			m = mechanicGateway.findById(mechanicId);
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
		
		return m;
	}

}
