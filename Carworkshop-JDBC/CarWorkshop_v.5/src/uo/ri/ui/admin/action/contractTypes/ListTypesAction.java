package uo.ri.ui.admin.action.contractTypes;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

public class ListTypesAction implements Action {

	@Override
	public void execute() throws BusinessException {
		Console.println("\nListado de tipos de contrato\n");  

		printTypes(ServicesFactory.getContractTypeCrudService().findAllContractTypes());
	}

	private void printTypes(List<ContractTypeDto> types) {
		for (ContractTypeDto ct : types)
			Printer.printContractType(ct);
	}

}
