package uo.ri.persistence;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import uo.ri.business.dto.InvoiceDto;

public interface InvoiceGateway extends SQLGateway {

	/**
	 * Añade una factura
	 * @param numeroFactura numero de la factura a añadir
	 * @param fechaFactura fecha de la factura a añadir
	 * @param iva iva de la factura
	 * @param totalConIva importe de la factura
	 * @return id de la factura añadida
	 * @throws SQLException
	 */
	long crearFactura(long numeroFactura, Date fechaFactura, double iva, double totalConIva) throws SQLException;

	/**
	 * @return número para una nueva facutra
	 * @throws SQLException
	 */
	long generarNuevoNumeroFactura() throws SQLException;

	/**
	 * @param invoiceNumber numero de la factura a buscar
	 * @return factura con el número dado, null si no existe
	 * @throws SQLException
	 */
	InvoiceDto findInvoice(Long invoiceNumber) throws SQLException;

	/**
	 * @param idFactura id de la factura a buscar
	 * @return factura con el id dado, null si no existe
	 * @throws SQLException
	 */
	InvoiceDto findInvoiceById(Long idFactura) throws SQLException;

	/**
	 * Añade los cargos a la factura
	 * @param idFactura factura
	 * @param cargos cargos a añadir
	 * @throws SQLException
	 */
	void añadirCargos(Long idFactura, Map<Long, Double> cargos) throws SQLException;

	
	
}
