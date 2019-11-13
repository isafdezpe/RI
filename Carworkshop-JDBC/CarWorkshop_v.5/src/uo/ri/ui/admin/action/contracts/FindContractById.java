package uo.ri.ui.admin.action.contracts;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

public class FindContractById implements Action {

	@Override
	public void execute() throws Exception {
		Long idContrato = Console.readLong("Id de contrato"); 
		
		ContractDto c = ServicesFactory.getContractCrudService().findContractById(idContrato);
		
		if (c == null)
			throw new BusinessException("El contrato no existe");
		
		Printer.printContract(c);
	}

}
