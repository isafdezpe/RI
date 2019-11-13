package uo.ri.ui.admin.contract.action;

import java.util.Date;
import java.util.List;

import alb.util.console.Console;
import alb.util.date.Dates;
import alb.util.menu.Action;
import uo.ri.business.ContractCategoryCrudService;
import uo.ri.business.ContractCrudService;
import uo.ri.business.ContractTypeCrudService;
import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.dto.ContractDto;
import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.Factory;
import uo.ri.ui.util.Printer;

public class AddContractAction implements Action {

	@Override
	public void execute() throws BusinessException {
		
		// Pedir datos
		ContractDto c = new ContractDto();
		c.mechanicId = Console.readLong("Id de mecanico"); 
		
		showContractTypes();
		c.typeId = Console.readLong("Id de tipo de contrato");
		
		showCategories();
		c.categoryId = Console.readLong("Id de categoria"); 
		
		c.startDate = askForDate("Fecha de inicio (dd/mm/aaaa)");
		c.endDate = askOptionalForDate("Fecha de fin (dd/mm/aaaa)");
		c.yearBaseSalary = Console.readLong("Salario base anual");
	
		// Procesar
		ContractCrudService as = Factory.service.forContractCrud();
		as.addContract( c );
				
		// Mostrar resultado
		Console.println("Nuevo contrato añadido");
	}

	private void showCategories() throws BusinessException {
		ContractCategoryCrudService as = Factory.service.forContractCategoryCrud();
		List<ContractCategoryDto> categories = as.findAllContractCategories();
		for(ContractCategoryDto c: categories) {
			Printer.printContractCategory( c );
		}
	}

	private void showContractTypes() throws BusinessException {
		ContractTypeCrudService as = Factory.service.forContractTypeCrud();
		List<ContractTypeDto> types = as.findAllContractTypes();
		for(ContractTypeDto t: types) {
			Printer.printContractType( t );
		}
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
