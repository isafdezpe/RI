package uo.ri.ui.admin.payroll.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.PayrollService;
import uo.ri.conf.Factory;

public class DeleteLastMechanicPayrollAction implements Action {

	@Override
	public void execute() throws Exception {
		Long idMecanico = Console.readLong("Id de mecánico"); 
		
		PayrollService ps = Factory.service.forPayroll();
		ps.deleteLastPayrollForMechanicId(idMecanico);
		
		Console.println("Se ha eliminado la última nómina");
	}

}
