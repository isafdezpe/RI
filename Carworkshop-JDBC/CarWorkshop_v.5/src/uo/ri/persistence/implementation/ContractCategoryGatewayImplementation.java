package uo.ri.persistence.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.conf.Conf;
import uo.ri.persistence.ContractCategoryGateway;

public class ContractCategoryGatewayImplementation 
		implements ContractCategoryGateway {

	private Connection c;

	/* (non-Javadoc)
	 * @see uo.ri.persistence.SQLGateway#setConnection(java.sql.Connection)
	 */
	@Override
	public void setConnection(Connection c) {
		this.c = c;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.ContractCategoryGateway#add(uo.ri.business.dto.ContractCategoryDto)
	 */
	@Override
	public void add(ContractCategoryDto contractCategory) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_INSERT_CONTRACT_CATEGORY"));
			pst.setString(1, contractCategory.name);
			pst.setDouble(2, contractCategory.trieniumSalary);
			pst.setDouble(3, contractCategory.productivityPlus);

			pst.executeUpdate();

		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.ContractCategoryGateway#update(uo.ri.business.dto.ContractCategoryDto)
	 */
	@Override
	public void update(ContractCategoryDto contractCategory) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_UPDATE_CONTRACT_CATEGORY"));
			pst.setDouble(1, contractCategory.trieniumSalary);
			pst.setDouble(2, contractCategory.productivityPlus);
			pst.setLong(3, contractCategory.id);

			int count = pst.executeUpdate();
			if (count <= 0)
				throw new RuntimeException(
						"No se ha podido actualizar la categoría");

		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.ContractCategoryGateway#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_DELETE_CATEGORY"));
			pst.setLong(1, id);
			int count = pst.executeUpdate();
			if (count <= 0)
				throw new RuntimeException(
						"No se ha podido eliminar la categoría.");

		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.ContractCategoryGateway#read()
	 */
	@Override
	public List<ContractCategoryDto> read() throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<ContractCategoryDto> categories = new ArrayList<>();

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_SELECT_ALL_CATEGORIES"));

			rs = pst.executeQuery();
			while(rs.next()) {
				ContractCategoryDto cc = new ContractCategoryDto();
				cc.id = rs.getLong(1);
				cc.name = rs.getString(2);
				cc.trieniumSalary = rs.getDouble(3);
				cc.productivityPlus = rs.getDouble(4);
				categories.add(cc);
			}
		} finally {
			Jdbc.close(rs, pst);
		}

		return categories;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.ContractCategoryGateway#findById(java.lang.Long)
	 */
	@Override
	public ContractCategoryDto findById(Long id) throws SQLException {
		ContractCategoryDto cc = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_FIND_CATEGORY_BY_ID"));
			pst.setLong(1, id);

			rs = pst.executeQuery();
			while (rs.next()) {
				cc = new ContractCategoryDto();
				cc.id = id;
				cc.name = rs.getString(1);
				cc.trieniumSalary = rs.getDouble(2);
				cc.productivityPlus = rs.getDouble(3);
			}

		} finally {
			Jdbc.close(rs, pst);
		}
		return cc;
	}

}
