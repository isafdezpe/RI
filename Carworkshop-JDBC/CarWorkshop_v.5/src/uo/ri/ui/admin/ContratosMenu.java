package uo.ri.ui.admin;

import alb.util.menu.BaseMenu;
import uo.ri.ui.admin.action.contracts.*;

public class ContratosMenu extends BaseMenu{

	public ContratosMenu() {
		menuOptions = new Object[][] { 
			{"Administrador > Gestión de contratos", null},
			
			{"Añadir contrato",					AddContractAction.class},
			{"Actualizar contrato", 			UpdateContractAction.class},
			{"Eliminar contrato",				DeleteContractAction.class},
			{"Finalizar contrato",				FinishContractAction.class},
			{"Buscar contrato por su id",		FindContractById.class},
			{"Listar contratos de un mecánico",	ListContractsOfMechanicAction.class},
		};
	}
}
