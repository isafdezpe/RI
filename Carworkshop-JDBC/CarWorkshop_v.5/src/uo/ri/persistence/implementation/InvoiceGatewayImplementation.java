package uo.ri.persistence.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.conf.Conf;
import uo.ri.persistence.InvoiceGateway;

public class InvoiceGatewayImplementation implements InvoiceGateway {

	private Connection c;
	
	/* (non-Javadoc)
	 * @see uo.ri.persistence.SQLGateway#setConnection(java.sql.Connection)
	 */
	@Override
	public void setConnection(Connection c) {
		this.c = c;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.InvoiceGateway#crearFactura(long, java.util.Date, double, double)
	 */
	@Override
	public long crearFactura(long numeroFactura, Date fechaFactura, double iva,
			double totalConIva) throws SQLException {
		PreparedStatement pst = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_INSERTAR_FACTURA"));
			pst.setLong(1, numeroFactura);
			pst.setDate(2, new java.sql.Date(fechaFactura.getTime()));
			pst.setDouble(3, iva);
			pst.setDouble(4, totalConIva);
			pst.setString(5, "SIN_ABONAR");

			pst.executeUpdate();

			return getGeneratedKey(numeroFactura); // Id de la nueva factura generada

		} finally {
			Jdbc.close(pst);
		}
	}

	/**
	 * @param numeroFactura numero de la nueva factura
	 * @return id para la nueva factura
	 * @throws SQLException
	 */
	private long getGeneratedKey(long numeroFactura) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_RECUPERAR_CLAVE_GENERADA"));
			pst.setLong(1, numeroFactura);
			rs = pst.executeQuery();
			rs.next();

			return rs.getLong(1);

		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.InvoiceGateway#generarNuevoNumeroFactura()
	 */
	@Override
	public long generarNuevoNumeroFactura() throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_ULTIMO_NUMERO_FACTURA"));
			rs = pst.executeQuery();

			if (rs.next()) {
				return rs.getLong(1) + 1; // +1, el siguiente
			} else {  // todavía no hay ninguna
				return 1L;
			}
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.InvoiceGateway#findInvoice(java.lang.Long)
	 */
	@Override
	public InvoiceDto findInvoice(Long invoiceNumber) throws SQLException {
		InvoiceDto invoice = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getConnection();
			
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_RECUPERAR_FACTURA"));
			pst.setLong(1, invoiceNumber);
			
			rs = pst.executeQuery();
			
			while (rs.next()) {
				invoice = new InvoiceDto();
				invoice.id = rs.getLong(1);
				invoice.number = rs.getLong(2);
				invoice.date = rs.getDate(3);
				invoice.vat = rs.getDouble(4);
				invoice.total = rs.getDouble(5);
				invoice.status = rs.getString(6);
			}
			
		} finally {
			Jdbc.close(rs, pst);
		}
		
		return invoice;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.InvoiceGateway#findInvoiceById(java.lang.Long)
	 */
	@Override
	public InvoiceDto findInvoiceById(Long idFactura) throws SQLException {
		InvoiceDto invoice = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getConnection();
			
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_RECUPERAR_FACTURA_POR_ID"));
			pst.setLong(1, idFactura);
			
			rs = pst.executeQuery();
			
			while (rs.next()) {
				invoice = new InvoiceDto();
				invoice.id = rs.getLong(1);
				invoice.number = rs.getLong(2);
				invoice.date = rs.getDate(3);
				invoice.vat = rs.getDouble(4);
				invoice.total = rs.getDouble(5);
				invoice.status = rs.getString(6);
			}
			
		} finally {
			Jdbc.close(rs, pst);
		}
		
		return invoice;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.InvoiceGateway#añadirCargos(java.lang.Long, java.util.Map)
	 */
	@Override
	public void añadirCargos(Long idFactura, Map<Long, Double> cargos) 
			throws SQLException {
		PreparedStatement pst = null, pst1 = null, pst2 = null;
		ResultSet rs = null;

		try {
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_INSERTAR_CARGOS"));
			pst1 = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_LIQUIDAR_FACTURA_ACTUALIZAR_MEDIO_PAGO"));
			pst2 = c.prepareStatement(Conf.getInstance()
					.getProperty(
							"SQL_LIQUIDAR_FACTURA_ACTUALIZAR_ESTAOD_FACTURA"));
			
			for (long id : cargos.keySet()) {
				pst.setDouble(1, cargos.get(id));
				pst.setLong(2, idFactura);
				pst.setLong(3, id);
				
				pst.executeUpdate();
			}
			
			pst2.setString(1, "ABONADA");
			for (long id : cargos.keySet()) {
				pst1.setDouble(1, cargos.get(id));
				pst1.setLong(2, id);

				pst2.setLong(2, idFactura);

				pst1.executeUpdate();
				pst2.executeUpdate();
			}
			
		} finally {
			Jdbc.close(rs, pst);
		}
	}

}
