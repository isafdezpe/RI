package uo.ri.ui.admin.action.payrolls;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;

public class GeneratePayrollsAction implements Action {

	@Override
	public void execute() throws BusinessException {
		int qty = ServicesFactory.getPayrollService().generatePayrolls();
		
		Console.printf("Se han generado %d nóminas%n", qty);
	}

}
