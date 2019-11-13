package uo.ri.ui.foreman.cliente;

import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;

public class ClientesMenu extends BaseMenu {

	public ClientesMenu() {
		menuOptions = new Object[][] { 
			{ "Jefe de Taller > Gestión de Clientes", null },

			{ "Añadir cliente", NotYetImplementedAction.class }, 
			{ "Modificar datos de cliente", NotYetImplementedAction.class }, 
			{ "Eliminar cliente", NotYetImplementedAction.class }, 
			{ "Listar clientes", NotYetImplementedAction.class }, 
		};
	}

}
