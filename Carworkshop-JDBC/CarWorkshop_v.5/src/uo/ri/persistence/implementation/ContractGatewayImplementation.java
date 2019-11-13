package uo.ri.persistence.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.ContractDto;
import uo.ri.conf.Conf;
import uo.ri.persistence.ContractGateway;

public class ContractGatewayImplementation implements ContractGateway {

	private Connection c;

	/* (non-Javadoc)
	 * @see uo.ri.persistence.SQLGateway#setConnection(java.sql.Connection)
	 */
	@Override
	public void setConnection(Connection c) {
		this.c = c;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.ContractGateway#add(uo.ri.business.dto.ContractDto)
	 */
	@Override
	public void add(ContractDto contract) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_INSERT_CONTRACT"));
			pst.setLong(1, contract.mechanicId);
			pst.setLong(2, contract.typeId);
			pst.setLong(3, contract.categoryId);
			pst.setDate(4, new java.sql.Date(contract.startDate.getTime()));
			if (contract.endDate != null)
				pst.setDate(5, new java.sql.Date(contract.endDate.getTime()));
			else
				pst.setNull(5, Types.TIMESTAMP);
			pst.setDouble(6, contract.yearBaseSalary);
			pst.setString(7, contract.status);

			pst.executeUpdate();

		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.ContractGateway#findContractsByMechanicId(java.lang.Long)
	 */
	@Override
	public List<ContractDto> findContractsByMechanicId(Long mechanicId) 
			throws SQLException {
		List<ContractDto> contracts = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_FIND_CONTRACT_BY_MECHANIC_ID"));
			pst.setLong(1, mechanicId);

			rs = pst.executeQuery();

			while (rs.next()) {
				ContractDto contract = new ContractDto();
				contract.mechanicId = mechanicId;
				contract.id = rs.getLong(1);
				contract.dni = rs.getString(2);
				contract.categoryName = rs.getString(3);
				contract.typeName = rs.getString(4);
				contract.status = rs.getString(5);
				contract.startDate = rs.getDate(6);
				contract.yearBaseSalary = rs.getDouble(7);
				contract.compensation = rs.getDouble(8);
				contract.endDate = rs.getDate(9);
				contract.categoryId = rs.getLong(10);
				contracts.add(contract);
			}

		} finally {
			Jdbc.close(rs, pst);
		}
		return contracts;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.ContractGateway#finishContract(java.lang.Long, java.util.Date, double)
	 */
	@Override
	public void finishContract(Long id, Date endDate, double compensation) 
			throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_FINISH_CONTRACT"));
			pst.setDate(1, new java.sql.Date(endDate.getTime()));
			pst.setDouble(2, compensation);
			pst.setLong(3, id);

			int count = pst.executeUpdate();
			if (count <= 0)
				throw new RuntimeException(
						"No se ha podido finalizar el contrato");

		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.ContractGateway#getCompensationDaysContract(java.lang.Long)
	 */
	@Override
	public int getCompensationDaysContract(Long id) throws SQLException{
		int compensationDays;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_GET_COMPENSATION_DAYS_CONTRACT"));
			pst.setLong(1, id);
			rs = pst.executeQuery();
			rs.next();
			compensationDays = rs.getInt(1);
		} finally {
			Jdbc.close(rs, pst);
		}

		return compensationDays;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.ContractGateway#update(uo.ri.business.dto.ContractDto)
	 */
	@Override
	public void update(ContractDto contract) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_UPDATE_CONTRACT"));
			if (contract.endDate != null)
				pst.setDate(1, new java.sql.Date(contract.endDate.getTime()));
			else
				pst.setNull(1, Types.TIMESTAMP);
			pst.setDouble(2, contract.yearBaseSalary);
			pst.setLong(3, contract.id);

			int count = pst.executeUpdate();
			if (count <= 0)
				throw new RuntimeException(
						"No se ha podido actualizar el contrato");

		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.ContractGateway#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_DELETE_CONTRACT"));
			pst.setLong(1, id);
			int count = pst.executeUpdate();
			if (count <= 0)
				throw new RuntimeException(
						"No se ha podido eliminar el contrato.");

		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.ContractGateway#findById(java.lang.Long)
	 */
	@Override
	public ContractDto findById(Long id) throws SQLException {
		ContractDto contract = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_FIND_CONTRACT_BY_ID"));
			pst.setLong(1, id);

			rs = pst.executeQuery();

			while (rs.next()) {
				contract = new ContractDto();
				contract.id = rs.getLong(1);
				contract.dni = rs.getString(2);
				contract.categoryName = rs.getString(3);
				contract.typeName = rs.getString(4);
				contract.status = rs.getString(5);
				contract.startDate = rs.getDate(6);
				contract.yearBaseSalary = rs.getDouble(7);
				contract.compensation = rs.getDouble(8);
				contract.endDate = rs.getDate(9);
				contract.mechanicId = rs.getLong(10);
			}

		} finally {
			Jdbc.close(rs, pst);
		}
		return contract;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.ContractGateway#findByCategoryId(java.lang.Long)
	 */
	@Override
	public List<ContractDto> findByCategoryId(Long idCategoria) 
			throws SQLException {
		List<ContractDto> contracts = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_FIND_BY_CATEGORY"));
			pst.setLong(1, idCategoria);

			rs = pst.executeQuery();
			while (rs.next()) {
				ContractDto c = new ContractDto();
				c.id = rs.getLong(1);
				contracts.add(c);
			}

		} finally {
			Jdbc.close(rs, pst);
		}

		return contracts;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.ContractGateway#findByTypeId(java.lang.Long)
	 */
	@Override
	public List<ContractDto> findByTypeId(Long idTipo) throws SQLException {
		List<ContractDto> contracts = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_FIND_BY_TYPE"));
			pst.setLong(1, idTipo);

			rs = pst.executeQuery();
			while (rs.next()) {
				ContractDto c = new ContractDto();
				c.id = rs.getLong(1);
				contracts.add(c);
			}

		} finally {
			Jdbc.close(rs, pst);
		}

		return contracts;
	}

}
