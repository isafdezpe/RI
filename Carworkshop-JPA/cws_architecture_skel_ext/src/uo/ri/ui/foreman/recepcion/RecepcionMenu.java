package uo.ri.ui.foreman.recepcion;

import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;

public class RecepcionMenu extends BaseMenu {

	public RecepcionMenu() {
		menuOptions = new Object[][] { 
			{"Jefe de Taller > Recepción en taller", null},
			
			{"Registrar avería", 		NotYetImplementedAction.class }, 
			{"Modificar averia", 		NotYetImplementedAction.class },
			{"Eliminar una averia", 	NotYetImplementedAction.class },
			{"", null},
			{"Listar averías", 			NotYetImplementedAction.class }, 
			{"Ver una avería", 			NotYetImplementedAction.class },
			{"", null},
			{"Listar mecánicos", 		NotYetImplementedAction.class }, 
			{"Asignar una avería",  	NotYetImplementedAction.class },
		};
	}

}
