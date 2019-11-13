package uo.ri.persistence.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import alb.util.date.Dates;
import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.PayrollDto;
import uo.ri.conf.Conf;
import uo.ri.persistence.PayrollGateway;

public class PayrollGatewayImplementation implements PayrollGateway {
	
	private Connection c;

	/* (non-Javadoc)
	 * @see uo.ri.persistence.SQLGateway#setConnection(java.sql.Connection)
	 */
	@Override
	public void setConnection(Connection c) {
		this.c = c;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.PayrollGateway#findById(java.lang.Long)
	 */
	@Override
	public PayrollDto findById(Long id) throws SQLException {
		PayrollDto payroll = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_FIND_PAYROLL_BY_ID"));
			pst.setLong(1, id);
			
			rs = pst.executeQuery();
			
			while (rs.next()) {
				payroll = new PayrollDto();
				payroll.id = rs.getLong(1);
				payroll.date = rs.getDate(2);
				payroll.baseSalary = rs.getDouble(3);
				payroll.extraSalary = rs.getDouble(4);
				payroll.productivity = rs.getDouble(5);
				payroll.triennium = rs.getDouble(6);
				payroll.irpf = rs.getDouble(7);
				payroll.socialSecurity = rs.getDouble(8);
				payroll.grossTotal = payroll.baseSalary + payroll.extraSalary 
						+ payroll.productivity + payroll.triennium;
				payroll.discountTotal = payroll.irpf + payroll.socialSecurity;
				payroll.netTotal = payroll.grossTotal - payroll.discountTotal;
			}
			
		} finally {
			Jdbc.close(rs, pst);
		}
		return payroll;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.PayrollGateway#findByMechanic(java.lang.Long)
	 */
	@Override
	public List<PayrollDto> findByMechanic(Long idMecanico) throws SQLException {
		List<PayrollDto> payrolls = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_FIND_PAYROLL_BY_MECHANIC_ID"));
			pst.setLong(1, idMecanico);
			
			rs = pst.executeQuery();
			
			while (rs.next()) {
				PayrollDto p = new PayrollDto();
				p.id = rs.getLong(1);
				p.date = rs.getDate(2);
				p.baseSalary = rs.getDouble(3);
				p.extraSalary = rs.getDouble(4);
				p.productivity = rs.getDouble(5);
				p.triennium = rs.getDouble(6);
				p.irpf = rs.getDouble(7);
				p.socialSecurity = rs.getDouble(8);
				p.grossTotal = p.baseSalary + p.extraSalary 
						+ p.productivity + p.triennium;
				p.discountTotal = p.irpf + p.socialSecurity;
				p.netTotal = p.grossTotal - p.discountTotal;
				payrolls.add(p);
			}
			
		} finally {
			Jdbc.close(rs, pst);
		}
		return payrolls;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.PayrollGateway#findAll()
	 */
	@Override
	public List<PayrollDto> findAll() throws SQLException {
		List<PayrollDto> payrolls = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_FIND_ALL_PAYROLLS"));
			
			rs = pst.executeQuery();
			
			while (rs.next()) {
				PayrollDto p = new PayrollDto();
				p.id = rs.getLong(1);
				p.date = rs.getDate(2);
				p.baseSalary = rs.getDouble(3);
				p.extraSalary = rs.getDouble(4);
				p.productivity = rs.getDouble(5);
				p.triennium = rs.getDouble(6);
				p.irpf = rs.getDouble(7);
				p.socialSecurity = rs.getDouble(8);
				p.grossTotal = p.baseSalary + p.extraSalary 
						+ p.productivity + p.triennium;
				p.discountTotal = p.irpf + p.socialSecurity;
				p.netTotal = p.grossTotal - p.discountTotal;
				payrolls.add(p);
			}
			
		} finally {
			Jdbc.close(rs, pst);
		}
		return payrolls;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.PayrollGateway#generatePayrolls(java.util.Map)
	 */
	@Override
	public int generatePayrolls(Map<Long, PayrollDto> payrolls) 
			throws SQLException {
		int count = 0;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_ADD_PAYROLL"));
			
			for (Long id : payrolls.keySet()) {
				PayrollDto p = payrolls.get(id);
				pst.setDate(1, new java.sql.Date(p.date.getTime()));
				pst.setDouble(2, p.baseSalary);
				pst.setDouble(3, p.extraSalary);
				pst.setDouble(4, p.triennium);
				pst.setDouble(5, p.productivity);
				pst.setDouble(6, p.irpf);
				pst.setDouble(7, p.socialSecurity);
				pst.setLong(8, id);
				count += pst.executeUpdate();
			}
		} finally {
			Jdbc.close(rs, pst);
		}
		
		return count;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.PayrollGateway#deleteLastGeneratedPayrolls()
	 */
	@Override
	public int deleteLastGeneratedPayrolls() throws SQLException {
		int count = 0;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Date date;
		
		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_GET_DATE_LAST_PAYROLLS"));
			
			rs = pst.executeQuery();
			if (rs.next())
				date = rs.getDate(1);
			else
				throw new RuntimeException("No hay nóminas que eliminar");
			
			Jdbc.close(rs, pst);
			
			date = Dates.trunc(date);
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_DELETE_LAST_GENERATED_PAYROLLS"));
			pst.setDate(1, new java.sql.Date(date.getTime()));
			
			count = pst.executeUpdate();
		} finally {
			Jdbc.close(rs, pst);
		}
		
		return count;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.PayrollGateway#deleteLastPayrollForMechanic(java.lang.Long)
	 */
	@Override
	public void deleteLastPayrollForMechanic(Long idMecanico) 
			throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Long idNomina;
		
		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_SELECT_LAST_PAYROLL_MECHANIC"));
			pst.setLong(1, idMecanico);
			
			rs = pst.executeQuery();
			if (rs.next())
				idNomina = rs.getLong(1);
			else
				throw new RuntimeException("No se ha podido eliminar la nómina");
			
			Jdbc.close(rs, pst);
			
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_DELETE_LAST_PAYROLL_MECHANIC"));
			pst.setLong(1, idNomina);
			
			int count = pst.executeUpdate();
			if (count <= 0)
				throw new RuntimeException("No se ha podido eliminar la nómina");
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.PayrollGateway#findByContract(java.lang.Long)
	 */
	@Override
	public List<PayrollDto> findByContract(Long idContrato) 
			throws SQLException {
		List<PayrollDto> payrolls = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_FIND_PAYROLL_BY_CONTRACT_ID"));
			pst.setLong(1, idContrato);
			
			rs = pst.executeQuery();
			
			while (rs.next()) {
				PayrollDto p = new PayrollDto();
				p.id = rs.getLong(1);
				p.date = rs.getDate(2);
				p.baseSalary = rs.getDouble(3);
				p.extraSalary = rs.getDouble(4);
				p.productivity = rs.getDouble(5);
				p.triennium = rs.getDouble(6);
				p.irpf = rs.getDouble(7);
				p.socialSecurity = rs.getDouble(8);
				p.grossTotal = p.baseSalary + p.extraSalary 
						+ p.productivity + p.triennium;
				p.discountTotal = p.irpf + p.socialSecurity;
				p.netTotal = p.grossTotal - p.discountTotal;
				payrolls.add(p);
			}
			
		} finally {
			Jdbc.close(rs, pst);
		}
		return payrolls;
	}

}
