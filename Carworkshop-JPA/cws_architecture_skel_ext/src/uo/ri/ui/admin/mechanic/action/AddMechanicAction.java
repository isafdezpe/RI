package uo.ri.ui.admin.mechanic.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.MechanicCrudService;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.Factory;

public class AddMechanicAction implements Action {

	@Override
	public void execute() throws BusinessException {
		
		// Pedir datos
		MechanicDto m = new MechanicDto();
		m.dni = Console.readString("Dni"); 
		m.name = Console.readString("Nombre"); 
		m.surname = Console.readString("Apellidos");
		
		// Procesar
		MechanicCrudService as = Factory.service.forMechanicCrudService();
		as.addMechanic( m );
				
		// Mostrar resultado
		Console.println("Nuevo mecánico añadido");
	}

}
