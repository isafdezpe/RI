package uo.ri.persistence.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.MechanicDto;
import uo.ri.conf.Conf;
import uo.ri.persistence.MechanicGateway;

public class MechanicGatewayImplementation implements MechanicGateway {

	private Connection c;

	/* (non-Javadoc)
	 * @see uo.ri.persistence.SQLGateway#setConnection(java.sql.Connection)
	 */
	@Override
	public void setConnection(Connection c) {
		this.c = c;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.MechanicGateway#add(uo.ri.business.dto.MechanicDto)
	 */
	@Override
	public void add(MechanicDto mechanic) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance(
					).getProperty("SQL_INSERT_MECHANIC"));
			pst.setString(1, mechanic.dni);
			pst.setString(2, mechanic.name);
			pst.setString(3, mechanic.surname);

			pst.executeUpdate();

		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.MechanicGateway#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_DELETE_MECHANIC"));
			pst.setLong(1, id);
			int count = pst.executeUpdate();
			if (count <= 0)
				throw new RuntimeException(
						"No se ha podido eliminar el mecánico.");

		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.MechanicGateway#canBeDeleted(java.lang.Long)
	 */
	@Override
	public boolean canBeDeleted(Long id) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_CHECK_MECHANIC_TO_DELETE"));
			pst.setLong(1, id);

			rs = pst.executeQuery();
			rs.next();
			int count = rs.getInt(1);

			return (count == 0);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.MechanicGateway#update(uo.ri.business.dto.MechanicDto)
	 */
	@Override
	public void update(MechanicDto mechanic) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_UPDATE_MECHANIC"));
			pst.setString(1, mechanic.name);
			pst.setString(2, mechanic.surname);
			pst.setLong(3, mechanic.id);

			int count = pst.executeUpdate();
			if (count <= 0)
				throw new RuntimeException(
						"No se ha podido actualizar el mecánico.");

		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.MechanicGateway#read()
	 */
	@Override
	public List<MechanicDto> read() throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<MechanicDto> mechanics = new ArrayList<MechanicDto>();

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_SELECT_ALL_MECHANICS"));

			rs = pst.executeQuery();
			while(rs.next()) {
				MechanicDto m = new MechanicDto();
				m.id = rs.getLong(1);
				m.dni = rs.getString(2);
				m.name = rs.getString(3);
				m.surname = rs.getString(4);
				mechanics.add(m);
			}
		} finally {
			Jdbc.close(rs, pst);
		}

		return mechanics;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.MechanicGateway#findById(java.lang.Long)
	 */
	@Override
	public MechanicDto findById(Long id) throws SQLException {
		MechanicDto mechanic = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_FIND_MECHANIC_BY_ID"));
			pst.setLong(1, id);

			rs = pst.executeQuery();

			while (rs.next()) {
				mechanic = new MechanicDto();
				mechanic.id = id;
				mechanic.dni = rs.getString(1);
				mechanic.name = rs.getString(2);
				mechanic.surname = rs.getString(3);
			}

		} finally {
			Jdbc.close(rs, pst);
		}
		return mechanic;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.MechanicGateway#listActive()
	 */
	@Override
	public List<MechanicDto> listActive() throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<MechanicDto> mechanics = new ArrayList<MechanicDto>();

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_SELECT_ACTIVE_MECHANICS"));

			rs = pst.executeQuery();
			while(rs.next()) {
				MechanicDto m = new MechanicDto();
				m.id = rs.getLong(1);
				m.dni = rs.getString(2);
				m.name = rs.getString(3);
				m.surname = rs.getString(4);
				mechanics.add(m);
			}
		} finally {
			Jdbc.close(rs, pst);
		}

		return mechanics;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.MechanicGateway#hasActivities(java.lang.Long, java.util.Date, java.util.Date)
	 */
	@Override
	public boolean hasActivities(Long mechanicId, Date startDate, Date endDate) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_CHECK_CONTRACT_TO_DELETE"));
			pst.setLong(1, mechanicId);
			pst.setDate(2, new java.sql.Date(startDate.getTime()));
			pst.setDate(3, new java.sql.Date(endDate.getTime()));

			rs = pst.executeQuery();
			rs.next();
			int count = rs.getInt(1);

			return (count != 0);

		} finally {
			Jdbc.close(rs, pst);
		}
	}

}
