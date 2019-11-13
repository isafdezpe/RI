package uo.ri.ui.admin.contract.action;

import java.util.Date;

import alb.util.console.Console;
import alb.util.date.Dates;
import alb.util.menu.Action;
import uo.ri.business.ContractCrudService;
import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.Factory;

public class UpdateContractAction implements Action {

	@Override
	public void execute() throws BusinessException {
		ContractCrudService as = Factory.service.forContractCrud();
		
		// Pedir datos
		Long id = Console.readLong("Id del contarto");
		ContractDto c = as.findContractById( id );

		c.endDate = askOptionalForDate("Fecha de fin");
		c.yearBaseSalary = Console.readLong("Salario base anual");
	
		// Procesar
		as.updateContract( c );
				
		// Mostrar resultado
		Console.println("Nuevo contrato añadido");

	}

	private Date askOptionalForDate(String msg) {
		while( true ) {
			try {
				Console.print( msg + "[optional]: ");
				String asString = Console.readString();
				
				return ( "".equals(asString) )
					? null
					: Dates.fromString( asString );
				
			} catch (NumberFormatException nfe) {
				Console.println("--> Fecha inválida");
			}			
		}
	}
	
}
