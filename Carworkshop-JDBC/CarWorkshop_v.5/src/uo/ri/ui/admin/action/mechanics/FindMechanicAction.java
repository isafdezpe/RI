package uo.ri.ui.admin.action.mechanics;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

public class FindMechanicAction implements Action {

	@Override
	public void execute() throws Exception {
		Long idMecanico = Console.readLong("Id de mecánico"); 
		
		MechanicDto m = ServicesFactory.getMechanicService().findMechanicById(idMecanico);
		
		if (m == null)
			throw new BusinessException("El mecánico no existe");
		
		Printer.printMechanic(m);
	}

}
