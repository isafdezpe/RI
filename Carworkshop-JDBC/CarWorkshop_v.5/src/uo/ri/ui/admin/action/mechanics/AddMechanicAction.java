package uo.ri.ui.admin.action.mechanics;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;

public class AddMechanicAction implements Action {

	@Override
	public void execute() throws BusinessException {
		
		// Pedir datos
		String dni = Console.readString("Dni"); 
		String nombre = Console.readString("Nombre"); 
		String apellidos = Console.readString("Apellidos");
		
		// Procesar
		MechanicDto m = new MechanicDto();
		m.dni = dni;
		m.name = nombre;
		m.surname = apellidos;
		ServicesFactory.getMechanicService().addMechanic(m);		
		
		// Mostrar resultado
		Console.println("Nuevo mecánico añadido");
	}

}
