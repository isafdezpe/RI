package uo.ri.ui.admin.contractcategory.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ContractCategoryCrudService;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.Factory;

public class DeleteContractCategoryAction implements Action {

	@Override
	public void execute() throws BusinessException {

		Long id = Console.readLong("Id de categoría"); 
		
		ContractCategoryCrudService as = Factory.service.forContractCategoryCrud();
		as.deleteContractCategory( id );
		
		Console.println("Se ha eliminado la categoría");

	}

}
