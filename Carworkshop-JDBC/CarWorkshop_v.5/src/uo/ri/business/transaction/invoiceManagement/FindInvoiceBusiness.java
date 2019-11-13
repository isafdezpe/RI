package uo.ri.business.transaction.invoiceManagement;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.InvoiceGateway;

public class FindInvoiceBusiness {
	
	private Long invoiceNumber;
	private Connection c;
	private InvoiceGateway invoiceGateway;

	public FindInvoiceBusiness(Long numeroFactura) {
		this.invoiceNumber = numeroFactura;
	}

	/**
	 * @return factura con el número dado, null si no existe
	 * @throws BusinessException
	 */
	public InvoiceDto execute() throws BusinessException {
		InvoiceDto invoice;
		
		try {
			c = Jdbc.getConnection();

			invoiceGateway = PersistenceFactory.getInvoiceGateway();
			invoiceGateway.setConnection(c);
			
			invoice = invoiceGateway.findInvoice(invoiceNumber);
			c.commit();
		} catch (SQLException e) {
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
		
		return invoice;
	}

}
