package uo.ri.persistence;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import uo.ri.business.dto.BreakdownDto;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.dto.MechanicDto;

public interface BreakdownGateway extends SQLGateway {

	/**
	 * @param id id de la factura
	 * @return la factura de la avería con id dado
	 * @throws SQLException
	 */
	InvoiceDto findInvoiceForBreakdown(Long id) throws SQLException;
	
	/**
	 * Cambia el estado de cada avería de la lista al nuevo estado pasado
	 * @param ids lista de averías
	 * @param status estado nuevo para las averías
	 * @throws SQLException
	 */
	void cambiarEstadoAverias(List<Long> ids, String status) throws SQLException;
	
	/**
	 * Vincula cada avería de la lista con su factura correspondiente
	 * @param idFactura factura para las averías dadas
	 * @param idsAveria lista de averías
	 * @throws SQLException
	 */
	void vincularAveriasConFactura(Long idFactura, List<Long> idsAveria) throws SQLException;

	/**
	 * Actualiza el importe para la avería dada
	 * @param idAveria avería a actualizar
	 * @param totalAveria nuevo importe
	 * @throws SQLException
	 */
	void actualizarImporteAveria(Long idAveria, double totalAveria) throws SQLException;

	/**
	 * @param idAveria avería a consultar
	 * @return importe de los respuestos de la avería
	 * @throws SQLException
	 */
	double consultaImporteRepuestos(Long idAveria) throws SQLException;

	/**
	 * @param idAveria avería a consultar
	 * @return importe de la mano de obra de la avería
	 * @throws SQLException
	 */double consultaImporteManoObra(Long idAveria) throws SQLException;
	
	/**
	 * @param dni dni del cliente
	 * @return lista de las reparaciones no facturadas del cliente dado
	 * @throws SQLException
	 */
	List<BreakdownDto> reparacionesNoFacturadasUnCliente(String dni) throws SQLException;

	/**
	 * @param mechanic mecanico que realiza las intervenciones
	 * @param payrollDate fecha de la nómina
	 * @return importe de las intervenciones realizadas por el mecánico en el mes
	 * de la nómina a generar
	 * @throws SQLException
	 */
	double calcularImporteIntervenciones(MechanicDto mechanic, Date payrollDate) throws SQLException;
}
