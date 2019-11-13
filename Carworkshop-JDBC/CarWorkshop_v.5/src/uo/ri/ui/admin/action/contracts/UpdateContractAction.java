package uo.ri.ui.admin.action.contracts;

import java.util.Date;

import alb.util.console.Console;
import alb.util.date.Dates;
import alb.util.menu.Action;
import uo.ri.business.dto.ContractDto;
import uo.ri.conf.ServicesFactory;

public class UpdateContractAction implements Action {

	@Override
	public void execute() throws Exception {
		// Pedir datos
		Long id = Console.readLong("Id del contrato"); 
		Date fin = askOptionalForDate("Fecha de fin del contrato (dd/mm/aaaa)");
		Long salario = Console.readLong("Salario base anual");

		// Procesar
		ContractDto c = new ContractDto();
		c.id = id;
		c.endDate = fin;
		c.yearBaseSalary = salario;
		ServicesFactory.getContractCrudService().updateContract(c);

		// Mostrar resultado
		Console.println("Contrato actualizado");
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
