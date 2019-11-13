package uo.ri.ui.admin.payroll.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.PayrollService;
import uo.ri.business.dto.PayrollDto;
import uo.ri.conf.Factory;
import uo.ri.ui.util.Printer;

public class ViewPayrollDetailAction implements Action {

	@Override
	public void execute() throws Exception {
		Long id = Console.readLong("Id de la nómina");

		PayrollService ps = Factory.service.forPayroll();
		PayrollDto payroll = ps.findPayrollById(id);

		if (payroll != null) {
			Printer.printPayrollDetail(payroll);
		} else {
			Console.println("No existe la nómina");
		}
	}

}
