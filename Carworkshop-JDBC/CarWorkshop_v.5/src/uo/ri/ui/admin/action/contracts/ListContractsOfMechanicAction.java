package uo.ri.ui.admin.action.contracts;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.ContractDto;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

public class ListContractsOfMechanicAction implements Action {

	@Override
	public void execute() throws Exception {
		Long idMecanico = Console.readLong("Id de mecánico"); 
		
		printContracts(ServicesFactory.getContractCrudService().findContractsByMechanicId(idMecanico));
	}

	private void printContracts(List<ContractDto> contracts) {
		for (ContractDto c : contracts)
			Printer.printContract(c);
	}

}
