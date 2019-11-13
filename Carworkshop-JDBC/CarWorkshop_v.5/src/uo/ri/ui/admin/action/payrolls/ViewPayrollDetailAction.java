package uo.ri.ui.admin.action.payrolls;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.PayrollDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

public class ViewPayrollDetailAction implements Action {

	@Override
	public void execute() throws BusinessException {
		Long id = Console.readLong("Id de nómina");
		
		PayrollDto p = ServicesFactory.getPayrollService().findPayrollById(id);
		
		if (p == null)
			throw new BusinessException("No existe la nómina");
		
		Printer.printPayrollDetail(p);
	}

}
