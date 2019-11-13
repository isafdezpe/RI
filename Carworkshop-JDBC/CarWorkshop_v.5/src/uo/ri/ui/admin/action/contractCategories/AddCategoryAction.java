package uo.ri.ui.admin.action.contractCategories;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;

public class AddCategoryAction implements Action{

	@Override
	public void execute() throws BusinessException {
		
		// Pedir datos
		String nombre = Console.readString("Nombre de la categoría"); 
		Double trienios = Console.readDouble("Importe de los trienios"); 
		Double productividad = Console.readDouble("Plus de productividad (%)");
		
		// Procesar
		ContractCategoryDto cc = new ContractCategoryDto();
		cc.name = nombre;
		cc.trieniumSalary = trienios;
		cc.productivityPlus = productividad;
		ServicesFactory.getContractCategoryCrudService().addContractCategory(cc);
		
		// Mostrar resultado
		Console.println("Nueva categoría añadida");
	}
	
}
