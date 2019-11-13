package uo.ri.ui.admin.action.payrolls;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;

public class DeleteLastPayrollForMechanicAction implements Action {

	@Override
	public void execute() throws BusinessException{
		Long idMecanico = Console.readLong("Id de mecánico"); 
		
		ServicesFactory.getPayrollService().deleteLastPayrollForMechanicId(idMecanico);
		
		Console.println("Se ha eliminado la última nómina");
	}

}
