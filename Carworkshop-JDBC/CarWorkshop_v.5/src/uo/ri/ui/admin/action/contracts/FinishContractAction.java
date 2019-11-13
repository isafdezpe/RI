package uo.ri.ui.admin.action.contracts;

import java.util.Date;

import alb.util.console.Console;
import alb.util.date.Dates;
import alb.util.menu.Action;
import uo.ri.conf.ServicesFactory;

public class FinishContractAction implements Action {

	@Override
	public void execute() throws Exception {
		Long id = Console.readLong("Id del contrato");
		Date endDate = askForDate("Fecha de extinción del contrato");
				
		ServicesFactory.getContractCrudService().finishContract(id, endDate);
		
		Console.println("El contrato ha sido finalizado");
	}

	private Date askForDate(String msg) {
		while( true ) {
			try {
				String asString = Console.readString(msg);
				return Dates.fromString( asString );
			} catch (NumberFormatException nfe) {
				Console.println("--> Fecha inválida");
			}			
		}
	}	
	
}
