package uo.ri.ui.admin.contract.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ContractCrudService;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.Factory;

public class DeleteContractAction implements Action {

	@Override
	public void execute() throws BusinessException {

		Long id = Console.readLong("Id del contrato");
		
		ContractCrudService service = Factory.service.forContractCrud();
		service.deleteContract(id);
		
		Console.println("El contrato ha sido eliminado");

	}

}
