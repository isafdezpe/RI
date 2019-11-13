package uo.ri.business;

import java.util.List;
import java.util.Map;

import uo.ri.business.dto.BreakdownDto;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.exception.BusinessException;

public interface InvoiceService {

	InvoiceDto createInvoiceFor(List<Long> idsAveria) throws BusinessException;
	InvoiceDto findInvoice(Long numeroFactura) throws BusinessException;
	List<PaymentMeanDto> findPayMethodsForInvoice(Long idFactura) throws BusinessException;
	void settleInvoice(Long idFactura, Map<Long, Double> cargos) throws BusinessException;

	List<BreakdownDto> findRepairsByClient(String dni) throws BusinessException;
	
}
