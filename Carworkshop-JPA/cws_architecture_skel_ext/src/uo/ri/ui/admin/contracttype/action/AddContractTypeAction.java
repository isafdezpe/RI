package uo.ri.ui.admin.contracttype.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ContractTypeCrudService;
import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.Factory;

public class AddContractTypeAction implements Action {

	@Override
	public void execute() throws BusinessException {

		ContractTypeDto dto = new ContractTypeDto();
		dto.name = Console.readString("Nombre del tipo");
		dto.compensationDays = Console.readInt("Días de compensación");
		
		ContractTypeCrudService service = Factory.service.forContractTypeCrud();
		service.addContractType( dto );
		
		Console.println("Nuevo tipo de contrato añadido");
	}

}
