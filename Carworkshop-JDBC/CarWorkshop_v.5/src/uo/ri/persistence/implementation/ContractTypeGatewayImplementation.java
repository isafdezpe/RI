package uo.ri.persistence.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.ContractTypeDto;
import uo.ri.conf.Conf;
import uo.ri.persistence.ContractTypeGateway;

public class ContractTypeGatewayImplementation implements ContractTypeGateway {

	private Connection c;

	/* (non-Javadoc)
	 * @see uo.ri.persistence.SQLGateway#setConnection(java.sql.Connection)
	 */
	@Override
	public void setConnection(Connection c) {
		this.c = c;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.ContractTypeGateway#add(uo.ri.business.dto.ContractTypeDto)
	 */
	@Override
	public void add(ContractTypeDto contractType) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_INSERT_CONTRACT_TYPE"));
			pst.setString(1, contractType.name);
			pst.setInt(2, contractType.compensationDays);

			pst.executeUpdate();

		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.ContractTypeGateway#update(uo.ri.business.dto.ContractTypeDto)
	 */
	@Override
	public void update(ContractTypeDto contractType) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_UPDATE_CONTRACT_TYPE"));
			pst.setInt(1, contractType.compensationDays);
			pst.setLong(2, contractType.id);

			int count = pst.executeUpdate();
			if (count <= 0)
				throw new RuntimeException("No se ha podido actualizar el tipo");

		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.ContractTypeGateway#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_DELETE_TYPE"));
			pst.setLong(1, id);
			int count = pst.executeUpdate();
			if (count <= 0)
				throw new RuntimeException("No se ha podido eliminar el tipo.");

		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.ContractTypeGateway#read()
	 */
	@Override
	public List<ContractTypeDto> read() throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<ContractTypeDto> types = new ArrayList<ContractTypeDto>();

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_SELECT_ALL_TYPES"));

			rs = pst.executeQuery();
			while(rs.next()) {
				ContractTypeDto ct = new ContractTypeDto();
				ct.id = rs.getLong(1);
				ct.name = rs.getString(2);
				ct.compensationDays = rs.getInt(3);
				types.add(ct);
			}
		} finally {
			Jdbc.close(rs, pst);
		}

		return types;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.ContractTypeGateway#findById(java.lang.Long)
	 */
	@Override
	public ContractTypeDto findById(Long id) throws SQLException {
		ContractTypeDto ct = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_FIND_TYPE_BY_ID"));
			pst.setLong(1, id);

			rs = pst.executeQuery();

			while (rs.next()) {
				ct = new ContractTypeDto();
				ct.id = id;
				ct.name = rs.getString(1);
				ct.compensationDays = rs.getInt(2);
			}

		} finally {
			Jdbc.close(rs, pst);
		}
		return ct;
	}

}
