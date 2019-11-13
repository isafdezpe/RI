package uo.ri.ui.admin.action.payrolls;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;

public class DeleteLastGeneratedPayrollsAction implements Action {

	@Override
	public void execute() throws BusinessException {
		String res = Console.readString("Se van a borrar nóminas, ¿está seguro? [s/n]");
		if ( ! ("s".equals(res) || "S".equals(res)) ) {
			return;
		}
			
		int qty = ServicesFactory.getPayrollService().deleteLastGenetaredPayrolls();
		
		Console.printf("Se han eliminado las %d últimas nóminas%n", qty);
	}

}
