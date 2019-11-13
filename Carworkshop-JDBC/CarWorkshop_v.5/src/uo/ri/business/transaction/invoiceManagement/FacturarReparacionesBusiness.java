package uo.ri.business.transaction.invoiceManagement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import alb.util.date.Dates;
import alb.util.jdbc.Jdbc;
import alb.util.math.Round;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.BreakdownGateway;
import uo.ri.persistence.InvoiceGateway;

public class FacturarReparacionesBusiness {

	private List<Long> idsAveria;
	private Connection c;	
	private InvoiceGateway invoiceGateway;
	private BreakdownGateway breakdownGateway;

	public FacturarReparacionesBusiness(List<Long> ids) {
		this.idsAveria = ids;
	}

	/**
	 * @return factura con las reparaciones
	 * @throws BusinessException
	 */
	public InvoiceDto execute() throws BusinessException {
		InvoiceDto in = new InvoiceDto();
		try {
			c = Jdbc.getConnection();
			c.setAutoCommit(false);

			invoiceGateway = PersistenceFactory.getInvoiceGateway();
			invoiceGateway.setConnection(c);

			breakdownGateway = PersistenceFactory.getBreakdownGateway();
			breakdownGateway.setConnection(c);

			verificarAveriasTerminadas(idsAveria);

			long numeroFactura = generarNuevoNumeroFactura();
			Date fechaFactura = Dates.today();
			double totalFactura = calcularImportesAverias(idsAveria);
			double iva = porcentajeIva(totalFactura, fechaFactura);
			double importe = totalFactura * (1 + iva/100);
			importe = Round.twoCents(importe);

			long idFactura = 
					crearFactura(numeroFactura, fechaFactura, iva, importe);
			vincularAveriasConFactura(idFactura, idsAveria);
			cambiarEstadoAverias(idsAveria, "FACTURADA");

			c.commit();

			in.number = numeroFactura;
			in.date = fechaFactura;
			in.total = importe;
			in.vat = iva;
			in.status = "SIN_ABONAR";
		}
		catch (SQLException e) {
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
		return in;
	}


	/**
	 * Comprueba si las averías están terminadas
	 * @param idsAveria lista de averías
	 * @throws SQLException
	 * @throws BusinessException si una avería no está terminada
	 */
	private void verificarAveriasTerminadas(List<Long> idsAveria) 
			throws SQLException, BusinessException {
		InvoiceDto i;

		for (Long idAveria : idsAveria) {
			i = breakdownGateway.findInvoiceForBreakdown(idAveria);

			if (! "TERMINADA".equalsIgnoreCase(i.status) ) {
				throw new BusinessException("No está terminada la avería " 
							+ idAveria);
			}
		}
	}

	/**
	 * Cambia el estado de las averías 
	 * @param idsAveria lista de averías
	 * @param status estado nuevo
	 * @throws SQLException
	 */
	private void cambiarEstadoAverias(List<Long> idsAveria, String status) 
			throws SQLException {
		breakdownGateway.cambiarEstadoAverias(idsAveria, status);
	}

	/**
	 * Vincula una lista de averías a una factura
	 * @param idFactura factura
	 * @param idsAveria lista de averías a vincular
	 * @throws SQLException
	 */
	private void vincularAveriasConFactura(long idFactura, List<Long> idsAveria) 
			throws SQLException {
		breakdownGateway.vincularAveriasConFactura(idFactura, idsAveria);
	}

	/**
	 * Crea una factura
	 * @param numeroFactura
	 * @param fechaFactura
	 * @param iva
	 * @param totalConIva
	 * @return
	 * @throws SQLException
	 */
	private long crearFactura(long numeroFactura, Date fechaFactura,
			double iva, double totalConIva) throws SQLException {
		return invoiceGateway.
				crearFactura(numeroFactura, fechaFactura, iva, totalConIva);
	}

	/**
	 * @return numero para una nueva factura
	 * @throws SQLException
	 */
	private Long generarNuevoNumeroFactura() throws SQLException {
		return invoiceGateway.generarNuevoNumeroFactura();
	}

	/**
	 * @param totalFactura
	 * @param fechaFactura 
	 * @return el porcentaje del iva de una factura
	 */
	private double porcentajeIva(double totalFactura, Date fechaFactura) {
		return Dates.fromString("1/7/2012").before(fechaFactura) ? 21.0 : 18.0;
	}

	/**
	 * @param idsAveria lista de averías
	 * @return importe total de las averías
	 * @throws BusinessException
	 * @throws SQLException
	 */
	protected double calcularImportesAverias(List<Long> idsAveria)
			throws BusinessException, SQLException {

		double totalFactura = 0.0;
		for(Long idAveria : idsAveria) {
			double importeManoObra = consultaImporteManoObra(idAveria);
			double importeRepuestos = consultaImporteRepuestos(idAveria);
			double totalAveria = importeManoObra + importeRepuestos;

			actualizarImporteAveria(idAveria, totalAveria);

			totalFactura += totalAveria; 
		}
		return totalFactura;
	}

	/**
	 * Actualiza el importe de una avería
	 * @param idAveria avería a actualizar
	 * @param totalAveria nuevo importe
	 * @throws SQLException
	 */
	private void actualizarImporteAveria(Long idAveria, double totalAveria) 
			throws SQLException {
		breakdownGateway.actualizarImporteAveria(idAveria, totalAveria);
	}

	/**
	 * @param idAveria 
	 * @return importe de los repuestos utilizados en la avería
	 * @throws SQLException
	 */
	private double consultaImporteRepuestos(Long idAveria) throws SQLException {
		return breakdownGateway.consultaImporteRepuestos(idAveria);
	}

	/**
	 * @param idAveria 
	 * @return importe de la mano de obra de la avería
	 * @throws SQLException
	 */
	private double consultaImporteManoObra(Long idAveria) 
			throws BusinessException, SQLException {
		return breakdownGateway.consultaImporteManoObra(idAveria);
	}
}
