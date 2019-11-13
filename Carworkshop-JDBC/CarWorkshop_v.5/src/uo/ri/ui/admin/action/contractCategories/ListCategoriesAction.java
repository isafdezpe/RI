package uo.ri.ui.admin.action.contractCategories;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

public class ListCategoriesAction implements Action {

	@Override
	public void execute() throws BusinessException {
		Console.println("\nListado de categorías de contrato\n");  

		printCategories(ServicesFactory.getContractCategoryCrudService().findAllContractCategories());
	}

	private void printCategories(List<ContractCategoryDto> categories) {
		for (ContractCategoryDto cc : categories)
			Printer.printContractCategory(cc);
	}

}
