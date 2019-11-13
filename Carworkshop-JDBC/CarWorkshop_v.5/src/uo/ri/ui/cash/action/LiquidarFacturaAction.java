package uo.ri.ui.cash.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.*;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.service.InvoiceService;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

public class LiquidarFacturaAction implements Action {

	/**
	 * Proceso:
	 * 
	 *  - Pedir al usuario el nº de factura
	 *  - Recuperar los datos de la factura
	 *  - Mostrar la factura en pantalla
	 *  - Verificar que está sin abonar (status <> 'ABONADA')
	 *  - Listar en pantalla los medios de pago registrados para el cliente
	 *  - Mostrar los medios de pago
	 *  - Pedir el importe a cargar en cada medio de pago. 
	 *  	Verificar que la suma de los cargos es igual al importe de la factura
	 *  - Registrar los cargos en la BDD
	 *  - Incrementar el acumulado de cada medio de pago
	 *  - Si se han empleado bonos, decrementar el saldo disponible 
	 *  - Finalmente, marcar la factura como abonada 
	 *  
	 */
	@Override
	public void execute() throws BusinessException {
		long invoiceNumber = Console.readLong("Nº de factura: ");

		InvoiceService service = ServicesFactory.getInvoiceSevice();

		InvoiceDto invoice = service.findInvoice(invoiceNumber);
		Printer.printInvoice(invoice);

		List<PaymentMeanDto> paymentMeans = service.findPayMethodsForInvoice(invoice.id);
		Printer.printPaymentMeans(paymentMeans);

		Map<Long, Double> cargos= new HashMap<Long, Double>();
		Double total = 0.0;

		while ((invoice.total - total) > 0) {
			Long id;
			Double importe;
			do {
				id = Console.readLong("ID del medio de pago: ");
			} while (!isValidId(id, paymentMeans));

			do {
				importe = Console.readDouble("Importe a cargar: ");
			} while (total + importe > invoice.total);

			if (cargos.containsKey(id)) {
				cargos.replace(id, cargos.get(id) + importe);
			} else {
				cargos.put(id, importe);
			}

			total += importe;
		}

		service.settleInvoice(invoice.id, cargos);
}

private boolean isValidId(Long id, List<PaymentMeanDto> listaMetodosDePago) {
	for (PaymentMeanDto paymentMeanDto : listaMetodosDePago) {
		if (paymentMeanDto.id.equals(id)) {
			return true;
		}
	}
	return false;
}

}
