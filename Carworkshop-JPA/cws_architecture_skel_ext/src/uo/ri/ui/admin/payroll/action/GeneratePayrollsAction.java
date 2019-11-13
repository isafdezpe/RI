package uo.ri.ui.admin.payroll.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.PayrollService;
import uo.ri.conf.Factory;

public class GeneratePayrollsAction implements Action {

	@Override
	public void execute() throws Exception {
		
		PayrollService ps = Factory.service.forPayroll();
		int qty = ps.generatePayrolls();
		
		Console.printf("Se han generado %d nóminas%n", qty);
	}

}
