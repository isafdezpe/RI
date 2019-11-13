package uo.ri.business.transaction.invoiceManagement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import alb.util.jdbc.Jdbc;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.InvoiceGateway;

public class SettleInvoiceBusiness {
	
	private Long idFactura; 
	private Map<Long, Double> cargos;
	private Connection c;
	private InvoiceGateway invoiceGateway;

	public SettleInvoiceBusiness(Long idFactura, Map<Long, Double> cargos) {
		this.idFactura = idFactura;
		this.cargos = cargos;
	}

	/**
	 * Liquida una factura
	 * @throws BusinessException
	 */
	public void execute() throws BusinessException {
		try {
			c = Jdbc.getConnection();
			c.setAutoCommit(false);
			
			invoiceGateway = PersistenceFactory.getInvoiceGateway();
			invoiceGateway.setConnection(c);
			
			invoiceGateway.añadirCargos(idFactura, cargos);
			c.commit();
		} catch (SQLException | RuntimeException e) {
			try {
				if (c != null) {
					c.rollback();
				}
			} catch (SQLException ex) {
			}
			;
			throw new BusinessException(e.getMessage());
		} finally {
			Jdbc.close(c);
		}
	}

}
