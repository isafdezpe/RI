package uo.ri.business.service.implementation;

import java.util.List;
import java.util.Map;

import uo.ri.business.dto.BreakdownDto;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.service.InvoiceService;
import uo.ri.business.transaction.invoiceManagement.FacturarReparacionesBusiness;
import uo.ri.business.transaction.invoiceManagement.FindInvoiceBusiness;
import uo.ri.business.transaction.invoiceManagement.FindPaymentMethodsForInvoiceBusiness;
import uo.ri.business.transaction.invoiceManagement.ReparacionesNoFacturadasUnClienteBusiness;
import uo.ri.business.transaction.invoiceManagement.SettleInvoiceBusiness;

public class InvoiceServiceImplementation implements InvoiceService {

	/* (non-Javadoc)
	 * @see uo.ri.business.service.InvoiceService#createInvoiceFor(java.util.List)
	 */
	@Override
	public InvoiceDto createInvoiceFor(List<Long> idsAveria) 
			throws BusinessException {
		return new FacturarReparacionesBusiness(idsAveria).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.InvoiceService#findInvoice(java.lang.Long)
	 */
	@Override
	public InvoiceDto findInvoice(Long numeroFactura) throws BusinessException {
		return new FindInvoiceBusiness(numeroFactura).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.InvoiceService#findPayMethodsForInvoice(java.lang.Long)
	 */
	@Override
	public List<PaymentMeanDto> findPayMethodsForInvoice(Long idFactura) 
			throws BusinessException {
		return new FindPaymentMethodsForInvoiceBusiness(idFactura).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.InvoiceService#settleInvoice(java.lang.Long, java.util.Map)
	 */
	@Override
	public void settleInvoice(Long idFactura, Map<Long, Double> cargos) 
			throws BusinessException {
		new SettleInvoiceBusiness(idFactura, cargos).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.InvoiceService#findRepairsByClient(java.lang.String)
	 */
	@Override
	public List<BreakdownDto> findRepairsByClient(String dni) 
			throws BusinessException {
		return new ReparacionesNoFacturadasUnClienteBusiness(dni).execute();
	}

}
