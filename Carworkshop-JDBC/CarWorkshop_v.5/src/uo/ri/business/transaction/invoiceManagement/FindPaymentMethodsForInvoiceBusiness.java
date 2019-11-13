package uo.ri.business.transaction.invoiceManagement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.*;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.InvoiceGateway;
import uo.ri.persistence.PaymentMeanGateway;

public class FindPaymentMethodsForInvoiceBusiness {
	
	private Long idFactura;
	private Connection c;
	private InvoiceGateway invoiceGateway;
	private PaymentMeanGateway paymentMeanGateway;

	public FindPaymentMethodsForInvoiceBusiness(Long idFactura) {
		this.idFactura = idFactura;
	}

	/**
	 * @return lista de medios de pago para una factura
	 * @throws BusinessException
	 */
	public List<PaymentMeanDto> execute() throws BusinessException {
		List<PaymentMeanDto> paymentMeans;
		
		try {
			c = Jdbc.getConnection();
			c.setAutoCommit(false);
			
			invoiceGateway = PersistenceFactory.getInvoiceGateway();
			invoiceGateway.setConnection(c);
			
			paymentMeanGateway = PersistenceFactory.getPaymentMeanGateway();
			paymentMeanGateway.setConnection(c);
			
			InvoiceDto i = invoiceGateway.findInvoiceById(idFactura);
			if (i.status.equals("ABONADA"))
				throw new BusinessException("La factura ya se encuentra abonada");

			paymentMeans = paymentMeanGateway
					.findPaymentMethodsForInvoice(idFactura);
			c.commit();
		} catch (SQLException e) {
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
		
		return paymentMeans;
	}

}
