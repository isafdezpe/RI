package uo.ri.ui.admin;

import alb.util.menu.BaseMenu;
import uo.ri.ui.admin.action.contractTypes.*;

public class TiposContratoMenu extends BaseMenu{
	
	public TiposContratoMenu() {
		menuOptions = new Object[][] { 
			{"Administrador > Gestión de tipos de contrato", null},
			
			{ "Añadir tipo", 				AddTypeAction.class }, 
			{ "Actualizar tipo", 			UpdateTypeAction.class }, 
			{ "Eliminar tipo", 				DeleteTypeAction.class }, 
			{ "Listar tipos", 				ListTypesAction.class },
			{ "Buscar tipo por su id", 		FindTypeByIdAction.class },
		};
	}

}
