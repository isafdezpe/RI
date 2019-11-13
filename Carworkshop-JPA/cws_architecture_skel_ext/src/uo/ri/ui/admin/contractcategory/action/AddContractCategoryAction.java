package uo.ri.ui.admin.contractcategory.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ContractCategoryCrudService;
import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.Factory;

public class AddContractCategoryAction implements Action {

	@Override
	public void execute() throws BusinessException {

		ContractCategoryDto dto = new ContractCategoryDto();
		dto.name = Console.readString("Nombre de la categoría");
		dto.trieniumSalary = Console.readInt("Por tienio");
		dto.productivityPlus = Console.readDouble("Por productividad");
		
		ContractCategoryCrudService service = Factory.service.forContractCategoryCrud();
		service.addContractCategory( dto );
		
		Console.println("Nueva categoría de contrato añadida");

	}

}
