package uo.ri.ui.cash.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.BreakdownDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

public class ReparacionesNoFacturadasUnClienteAction implements Action {

	/**
	 * Proceso:
	 * 
	 *   - Pide el DNI del cliente
	 *    
	 *   - Muestra en pantalla todas sus averias no facturadas 
	 *   		(status <> 'FACTURADA'). De cada avería muestra su 
	 *   		id, fecha, status, importe y descripción
	 */
	@Override
	public void execute() throws BusinessException {
		String dni = Console.readString("DNI del cliente: ");
		
		List<BreakdownDto> breakdowns = ServicesFactory.getInvoiceSevice().findRepairsByClient(dni);
		printBreakdowns(breakdowns);
	}

	private void printBreakdowns(List<BreakdownDto> breakdowns) {
		for (BreakdownDto b : breakdowns) {
			Printer.printRepairing(b);
		}
	}

}
