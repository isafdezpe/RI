package uo.ri.ui.admin.contract.action;

import java.util.Date;

import alb.util.console.Console;
import alb.util.date.Dates;
import alb.util.menu.Action;
import uo.ri.business.ContractCrudService;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.Factory;

public class FinishContractAction implements Action {

	@Override
	public void execute() throws BusinessException {
		
		Long id = Console.readLong("Id del contrato");
		Date endDate = askForDate("Fecha de extinción del contrato");
				
		ContractCrudService service = Factory.service.forContractCrud();
		service.finishContract(id, endDate);
		
		Console.println("El contrato ha sido cancelado");

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
