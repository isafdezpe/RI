package uo.ri.ui.admin.action.contractCategories;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

public class FindCategoryByIdAction implements Action {

	@Override
	public void execute() throws BusinessException {
		Long idCategoria = Console.readLong("Id de la categoría"); 
		
		ContractCategoryDto cc = ServicesFactory.getContractCategoryCrudService().findContractCategoryById(idCategoria);
		
		if (cc == null)
			throw new BusinessException("La categoría no existe");
			
		Printer.printContractCategory(cc);
	}

}
