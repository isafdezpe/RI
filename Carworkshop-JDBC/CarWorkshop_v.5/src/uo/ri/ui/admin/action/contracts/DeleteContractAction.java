package uo.ri.ui.admin.action.contracts;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;

public class DeleteContractAction implements Action {

	@Override
	public void execute() throws BusinessException {
		Long idContrato = Console.readLong("Id de contrato"); 
		
		ServicesFactory.getContractCrudService().deleteContract(idContrato);
		
		Console.println("Se ha eliminado el contrato");
	}

}
