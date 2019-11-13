package uo.ri.ui.admin.action.mechanics;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

public class ListMechanicsAction implements Action {
	
	@Override
	public void execute() throws BusinessException {

		Console.println("\nListado de mecánicos\n");  

		printMechanics(ServicesFactory.getMechanicService().findAllMechanics());
	}

	private void printMechanics(List<MechanicDto> mechanics) {
		for (MechanicDto m : mechanics)
			Printer.printMechanic(m);
	}
}
