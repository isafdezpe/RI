package uo.ri.ui.admin.payroll.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.PayrollService;
import uo.ri.conf.Factory;

public class DeleteLastMonthPayrollsAction implements Action {

	@Override
	public void execute() throws Exception {
		String res = Console.readString("Se van a borrar nóminas, ¿está seguro? [s/n]");
		if ( ! ("s".equals(res) || "S".equals(res)) ) {
			return;
		}
			
		PayrollService ps = Factory.service.forPayroll();
		int qty = ps.deleteLastGenetaredPayrolls();
		
		Console.printf("Se han eliminado las %d últimas nóminas%n", qty);
	}

}
