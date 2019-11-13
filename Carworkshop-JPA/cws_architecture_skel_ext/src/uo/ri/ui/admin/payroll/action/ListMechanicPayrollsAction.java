package uo.ri.ui.admin.payroll.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.PayrollService;
import uo.ri.business.dto.PayrollDto;
import uo.ri.conf.Factory;
import uo.ri.ui.util.Printer;

public class ListMechanicPayrollsAction implements Action {

	@Override
	public void execute() throws Exception {
		Long id = Console.readLong("Id del mecánico");
		
		PayrollService ps = Factory.service.forPayroll();
		List<PayrollDto> payrolls = ps.findPayrollsByMechanicId( id );
		
		for(PayrollDto p: payrolls) {
			Printer.printPayrollSummary( p );
		}
		Console.printf("%d nóminas %n", payrolls.size());
	}

}
