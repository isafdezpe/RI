package uo.ri.ui.admin.action.mechanics;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;

public class UpdateMechanicAction implements Action {

	@Override
	public void execute() throws BusinessException {
		
		// Pedir datos
		Long id = Console.readLong("Id del mecánico"); 
		String nombre = Console.readString("Nombre"); 
		String apellidos = Console.readString("Apellidos");
		
		// Procesar
		MechanicDto m = new MechanicDto();
		m.id = id;
		m.name = nombre;
		m.surname = apellidos;
		ServicesFactory.getMechanicService().updateMechanic(m);
		
		// Mostrar resultado
		Console.println("Mecánico actualizado");
	}

}
