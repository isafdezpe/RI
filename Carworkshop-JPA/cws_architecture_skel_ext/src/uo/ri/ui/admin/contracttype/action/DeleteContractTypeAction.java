package uo.ri.ui.admin.contracttype.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ContractTypeCrudService;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.Factory;

public class DeleteContractTypeAction implements Action {

	@Override
	public void execute() throws BusinessException {

		Long id = Console.readLong("Id de tipo contrato"); 
		
		ContractTypeCrudService service = Factory.service.forContractTypeCrud();
		service.deleteContractType( id );
		
		Console.println("Se ha eliminado el tipo de contrato");

	}

}
