package uo.ri.ui.mechanic;

import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;

public class MainMenu extends BaseMenu {

	public MainMenu() {
		menuOptions = new Object[][] { 
			{ "Mecánico", null },
			{ "Listar reparaciones asignadas", 		NotYetImplementedAction.class }, 
			{ "Añadir repuestos a reparación", 		NotYetImplementedAction.class },
			{ "Eliminar repuestos a reparación", 	NotYetImplementedAction.class },
			{ "Cerrar una reparación", 				NotYetImplementedAction.class },
		};
	}

	public static void main(String[] args) {
		new MainMenu().execute();
	}

}
