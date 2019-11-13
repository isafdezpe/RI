package uo.ri.ui.admin.action.mechanics;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;

public class DeleteMechanicAction implements Action {

	@Override
	public void execute() throws BusinessException {
		Long idMecanico = Console.readLong("Id de mecánico"); 
		
		ServicesFactory.getMechanicService().deleteMechanic(idMecanico);
		
		Console.println("Se ha eliminado el mecánico");
	}

}
