package uo.ri.ui.admin.action.payrolls;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.PayrollDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

public class FindAllPayrollsAction implements Action {

	@Override
	public void execute() throws BusinessException {
		Console.println("\nListado de todas las nóminas\n");  
		
		printPayrolls(ServicesFactory.getPayrollService().findAllPayrolls());
	}
	
	private void printPayrolls(List<PayrollDto> payrolls) {
		for(PayrollDto p: payrolls) {
			Printer.printPayrollSummary( p );
		}
		Console.printf("%d nóminas %n", payrolls.size());
	}

}
