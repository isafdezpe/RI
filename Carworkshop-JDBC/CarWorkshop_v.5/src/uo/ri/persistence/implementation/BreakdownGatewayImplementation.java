package uo.ri.persistence.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alb.util.date.Dates;
import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.BreakdownDto;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.dto.MechanicDto;
import uo.ri.conf.Conf;
import uo.ri.persistence.BreakdownGateway;

public class BreakdownGatewayImplementation implements BreakdownGateway {

	private Connection c;

	/* (non-Javadoc)
	 * @see uo.ri.persistence.SQLGateway#setConnection(java.sql.Connection)
	 */
	@Override
	public void setConnection(Connection c) {
		this.c = c;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.BreakdownGateway#findInvoiceForBreakdown(java.lang.Long)
	 */
	@Override
	public InvoiceDto findInvoiceForBreakdown(Long id) throws SQLException {
		InvoiceDto i = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_VERIFICAR_ESTADO_AVERIA"));

			pst.setLong(1, id);

			rs = pst.executeQuery();
			while (rs.next()) {
				i = new InvoiceDto();
				i.id = id;
				i.status = rs.getString(1);
			}

			rs.close();
		} finally {
			Jdbc.close(rs, pst);
		}
		return i;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.BreakdownGateway#cambiarEstadoAverias(java.util.List, java.lang.String)
	 */
	@Override
	public void cambiarEstadoAverias(List<Long> ids, String status) 
			throws SQLException {
		PreparedStatement pst = null;
		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_ACTUALIZAR_ESTADO_AVERIA"));

			for (Long idAveria : ids) {
				pst.setString(1, status);
				pst.setLong(2, idAveria);

				pst.executeUpdate();
			}
		} finally {
			Jdbc.close(pst);
		}
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.BreakdownGateway#vincularAveriasConFactura(java.lang.Long, java.util.List)
	 */
	@Override
	public void vincularAveriasConFactura(Long idFactura, List<Long> idsAveria)
			throws SQLException {
		PreparedStatement pst = null;
		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_VINCULAR_AVERIA_FACTURA"));

			for (Long idAveria : idsAveria) {
				pst.setLong(1, idFactura);
				pst.setLong(2, idAveria);

				pst.executeUpdate();
			}
		} finally {
			Jdbc.close(pst);
		}
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.BreakdownGateway#actualizarImporteAveria(java.lang.Long, double)
	 */
	@Override
	public void actualizarImporteAveria(Long idAveria, double totalAveria) 
			throws SQLException {
		PreparedStatement pst = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_UPDATE_IMPORTE_AVERIA"));
			pst.setDouble(1, totalAveria);
			pst.setLong(2, idAveria);
			pst.executeUpdate();
		}	
		finally {
			Jdbc.close(pst);
		}
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.BreakdownGateway#consultaImporteRepuestos(java.lang.Long)
	 */
	@Override
	public double consultaImporteRepuestos(Long idAveria) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_IMPORTE_REPUESTOS"));
			pst.setLong(1, idAveria);

			rs = pst.executeQuery();
			if (rs.next() == false) {
				return 0.0; // La averia puede no tener repuestos
			}

			return rs.getDouble(1);

		}
		finally {
			Jdbc.close(rs, pst);
		}
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.BreakdownGateway#consultaImporteManoObra(java.lang.Long)
	 */
	@Override
	public double consultaImporteManoObra(Long idAveria) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_IMPORTE_MANO_OBRA"));
			pst.setLong(1, idAveria);

			rs = pst.executeQuery();
			rs.next();

			return rs.getDouble(1);

		} finally {
			Jdbc.close(rs, pst);
		}

	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.BreakdownGateway#reparacionesNoFacturadasUnCliente(java.lang.String)
	 */
	@Override
	public List<BreakdownDto> reparacionesNoFacturadasUnCliente(String dni)
			throws SQLException {
		List<BreakdownDto> breakdowns = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_FACTURA_VERIFICAR_ESTADO_AVERIA"));
			pst.setString(1, dni);

			rs = pst.executeQuery();

			while (rs.next()) {
				BreakdownDto b = new BreakdownDto();
				b.id = rs.getLong(1);
				b.date = rs.getDate(2);
				b.status = rs.getString(3);
				b.total = rs.getDouble(4);
				b.description = rs.getString(5);
				breakdowns.add(b);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			Jdbc.close(rs, pst);
		}

		return breakdowns;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.BreakdownGateway#calcularImporteIntervenciones(uo.ri.business.dto.MechanicDto, java.util.Date)
	 */
	@Override
	public double calcularImporteIntervenciones(MechanicDto mechanic, 
			Date payrollDate) throws SQLException {
		double importe = 0;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_SELECT_IMPORTE_INTERVENCION"));
			pst.setLong(1, mechanic.id);
			pst.setDate(2, new java.sql.Date(Dates
					.firstDayOfMonth(payrollDate).getTime()));
			pst.setDate(3, new java.sql.Date(payrollDate.getTime()));
			
			rs = pst.executeQuery();
			rs.next();
			
			importe = rs.getDouble(1);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			Jdbc.close(rs, pst);
		}
		
		return importe;
	}
}

