package uo.ri.ui.cash.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.InvoiceService;
import uo.ri.business.dto.BreakdownDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.Factory;
import uo.ri.ui.util.Printer;

public class ReparacionesNoFacturadasUnClienteAction implements Action {

	@Override
	public void execute() throws BusinessException {
		InvoiceService cs = Factory.service.forInvoice();

		String dni = Console.readString("DNI de cliente");
		
		Console.println("\nReparaciones no facturadas del cliente\n");  
		
		List<BreakdownDto> reps = cs.findRepairsByClient( dni ); 
		
		if (reps.size() == 0) {
			Console.printf("No tiene reparaciones pendientes\n");
			return;
		}
		
		for(BreakdownDto rep : reps) {
			Printer.printRepairing( rep );
		}
	}

}
