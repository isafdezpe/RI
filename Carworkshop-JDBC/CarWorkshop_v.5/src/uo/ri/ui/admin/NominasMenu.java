package uo.ri.ui.admin;

import alb.util.menu.BaseMenu;
import uo.ri.ui.admin.action.payrolls.*;

public class NominasMenu extends BaseMenu {

	public NominasMenu() {
		menuOptions = new Object[][] { 
			{"Administrador > Gestión de nóminas", null},
			
			{ "Listar nóminas de un empleado", 		FindPayrollsForMechanicAction.class },
			{ "Listar todas las nóminas ", 			FindAllPayrollsAction.class },
			{ "Ver detalle de una nómina", 			ViewPayrollDetailAction.class }, 
			{ "Eliminar última nómina de empleado", DeleteLastPayrollForMechanicAction.class }, 
			{ "Eliminar las últimas nóminas generadas", 	DeleteLastGeneratedPayrollsAction.class }, 
			{ "Generar nóminas", 					GeneratePayrollsAction.class },
		};
	}
}
