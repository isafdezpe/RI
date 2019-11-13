package uo.ri.ui.admin.action.contractCategories;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;

public class DeleteCategoryAction implements Action {

	@Override
	public void execute() throws BusinessException {
		Long idCategoria = Console.readLong("Id de la categoría"); 
		
		ServicesFactory.getContractCategoryCrudService().deleteContractCategory(idCategoria);
		
		Console.println("Se ha eliminado la categoría");
	}

}
