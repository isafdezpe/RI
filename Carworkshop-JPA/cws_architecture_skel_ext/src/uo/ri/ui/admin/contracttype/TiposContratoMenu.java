package uo.ri.ui.admin.contracttype;

import alb.util.menu.BaseMenu;
import uo.ri.ui.admin.contracttype.action.AddContractTypeAction;
import uo.ri.ui.admin.contracttype.action.DeleteContractTypeAction;
import uo.ri.ui.admin.contracttype.action.ListContractTypesAction;
import uo.ri.ui.admin.contracttype.action.UpdateContractTypeAction;

public class TiposContratoMenu extends BaseMenu {

	public TiposContratoMenu() {
		menuOptions = new Object[][] { 
			{"Administrador > Gestión de tipos de contrato", null},
			
			{ "Añadir", 	AddContractTypeAction.class }, 
			{ "Modificar", 	UpdateContractTypeAction.class }, 
			{ "Eliminar", 	DeleteContractTypeAction.class }, 
			{ "Listar", 	ListContractTypesAction.class },

		};
	}

}
