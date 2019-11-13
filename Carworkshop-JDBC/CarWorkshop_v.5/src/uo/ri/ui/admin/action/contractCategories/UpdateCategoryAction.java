package uo.ri.ui.admin.action.contractCategories;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;

public class UpdateCategoryAction implements Action {

	@Override
	public void execute() throws BusinessException {
		// Pedir datos
		Long id = Console.readLong("Id de la categoría"); 
		Double trienio = Console.readDouble("Importe de los trienios");
		Double productividad = Console.readDouble("Plus de productividad (%)");

		// Procesar
		ContractCategoryDto cc = new ContractCategoryDto();
		cc.id = id;
		cc.trieniumSalary = trienio;
		cc.productivityPlus = productividad;
		ServicesFactory.getContractCategoryCrudService().updateContractCategory(cc);

		// Mostrar resultado
		Console.println("Categoría de contrato actualizada");
	}

}
