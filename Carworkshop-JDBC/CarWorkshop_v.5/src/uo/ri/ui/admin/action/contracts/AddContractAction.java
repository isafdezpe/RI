package uo.ri.ui.admin.action.contracts;

import java.util.Date;
import java.util.List;

import alb.util.console.Console;
import alb.util.date.Dates;
import alb.util.menu.Action;
import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.dto.ContractDto;
import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

public class AddContractAction implements Action {

	@Override
	public void execute() throws BusinessException {
		Long idMecanico = Console.readLong("Id del mecánico");
		showContractTypes();
		Long idTipo = Console.readLong("Id del tipo de contrato");
		showCategories();
		Long idCategoria = Console.readLong("Id de la categoría del contrato");
		Date inicio = askForDate("Fecha de inicio del contrato (dd/mm/aaaa)");
		Date fin = askOptionalForDate("Fecha de fin del contrato (dd/mm/aaaa)");
		Long salario = Console.readLong("Salario base anual");
		
		ContractDto c = new ContractDto();
		c.mechanicId = idMecanico;
		c.typeId = idTipo;
		c.categoryId = idCategoria;
		c.startDate = inicio;
		c.endDate = fin;
		c.yearBaseSalary = salario;
		
		ServicesFactory.getContractCrudService().addContract(c);
		
		Console.println("Nuevo contrato añadido");
	}

	private void showCategories() throws BusinessException {
		List<ContractCategoryDto> categories = ServicesFactory.getContractCategoryCrudService().findAllContractCategories();
		for(ContractCategoryDto c: categories) {
			Printer.printContractCategory( c );
		}
	}

	private void showContractTypes() throws BusinessException{
		List<ContractTypeDto> types = ServicesFactory.getContractTypeCrudService().findAllContractTypes();
		for(ContractTypeDto t: types) {
			Printer.printContractType( t );
		}
	}

	private Date askOptionalForDate(String msg) {
		while( true ) {
			try {
				Console.print( msg + "[optional]: ");
				String asString = Console.readString();
				
				comprobarFecha(asString);
				
				return ( "".equals(asString) )
					? null
					: Dates.fromString( asString );
			} catch (NumberFormatException | BusinessException nfe) {
				Console.println("--> Fecha inválida");
			}			
		}
	}

	private Date askForDate(String msg) {
		while( true ) {
			try {
				String asString = Console.readString(msg);
				comprobarFecha(asString);
				return Dates.fromString( asString );
			} catch (NumberFormatException | BusinessException nfe) {
				Console.println("--> Fecha inválida");
			}			
		}
	}

	private void comprobarFecha(String asString) throws BusinessException {
		String dateString[] = asString.split("/");
		int day = Integer.parseInt(dateString[0]);
		int month = Integer.parseInt(dateString[1]);
		int year = Integer.parseInt(dateString[2]);
		if (day < 1 || month < 1 || month > 12 || year < 1980)
			throw new BusinessException();
		switch (month) {
		case 1: case 3: case 5: case 7: case 8: case 10: case 12:
			if (day > 31)
				throw new BusinessException();
			break;
		case 2:
			if (day > 28)
				throw new BusinessException();
			break;
		default: 
			if (day > 30)
				throw new BusinessException();
			break;
		}
	}
}
