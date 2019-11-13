package uo.ri.ui.admin.contracttype.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ContractTypeCrudService;
import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.Factory;

public class UpdateContractTypeAction implements Action {
	
	@Override
	public void execute() throws BusinessException {

		Long id = Console.readLong("Id del tipo de contrato");
		
		ContractTypeCrudService service = Factory.service.forContractTypeCrud();
		ContractTypeDto dto = service.findContractTypeById( id );
		BusinessCheck.isNotNull( dto, "No existe el tipo de contrato");
		
		dto.compensationDays = Console.readInt("Días de compensación");		
		service.updateContractType( dto );
		
		Console.println("Tipo de contrato actualizado");

	}

}
