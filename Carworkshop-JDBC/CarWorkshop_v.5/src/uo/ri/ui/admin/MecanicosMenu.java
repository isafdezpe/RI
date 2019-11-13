package uo.ri.ui.admin;

import alb.util.menu.BaseMenu;
import uo.ri.ui.admin.action.mechanics.AddMechanicAction;
import uo.ri.ui.admin.action.mechanics.DeleteMechanicAction;
import uo.ri.ui.admin.action.mechanics.FindActiveMechanicsAction;
import uo.ri.ui.admin.action.mechanics.FindMechanicAction;
import uo.ri.ui.admin.action.mechanics.ListMechanicsAction;
import uo.ri.ui.admin.action.mechanics.UpdateMechanicAction;

public class MecanicosMenu extends BaseMenu {

	public MecanicosMenu() {
		menuOptions = new Object[][] { 
			{"Administrador > Gestión de mecánicos", null},
			
			{ "Añadir mecánico", 				AddMechanicAction.class }, 
			{ "Modificar datos de mecánico", 	UpdateMechanicAction.class }, 
			{ "Eliminar mecánico", 				DeleteMechanicAction.class }, 
			{ "Listar mecánicos", 				ListMechanicsAction.class },
			{ "Listar mecánicos activos", 		FindActiveMechanicsAction.class },
			{ "Buscar mecánico por su id", 		FindMechanicAction.class },
		};
	}

}
