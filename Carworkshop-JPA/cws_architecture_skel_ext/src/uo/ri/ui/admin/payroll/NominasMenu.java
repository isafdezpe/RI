package uo.ri.ui.admin.payroll;

import alb.util.menu.BaseMenu;
import uo.ri.ui.admin.payroll.action.DeleteLastMechanicPayrollAction;
import uo.ri.ui.admin.payroll.action.DeleteLastMonthPayrollsAction;
import uo.ri.ui.admin.payroll.action.GeneratePayrollsAction;
import uo.ri.ui.admin.payroll.action.ListMechanicPayrollsAction;
import uo.ri.ui.admin.payroll.action.ViewPayrollDetailAction;

public class NominasMenu extends BaseMenu {

	public NominasMenu() {
		menuOptions = new Object[][] { 
			{"Administrador > Gestión de nóminas", null},
			
			{ "Listar nóminas de un empleado", 		ListMechanicPayrollsAction.class },
			{ "Ver detalle de una nómina", 			ViewPayrollDetailAction.class }, 
			{ "Eliminar última nómina de empleado", DeleteLastMechanicPayrollAction.class }, 
			{ "Eliminar nóminas del último mes", 	DeleteLastMonthPayrollsAction.class }, 
			{ "Generar nóminas", 					GeneratePayrollsAction.class }, 
		};
	}

}
