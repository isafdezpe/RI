package uo.ri.ui.admin.action.contractTypes;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;

public class DeleteTypeAction implements Action {

	@Override
	public void execute() throws BusinessException {
		Long idTipo = Console.readLong("Id del tipo"); 
		
		ServicesFactory.getContractTypeCrudService().deleteContractType(idTipo);
		
		Console.println("Se ha eliminado el tipo");
	}

}
