package uo.ri.ui.admin.action.contractTypes;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;
import uo.ri.ui.util.Printer;

public class FindTypeByIdAction implements Action {

	@Override
	public void execute() throws BusinessException {
		Long idTipo = Console.readLong("Id del tipo"); 
		
		ContractTypeDto ct = ServicesFactory.getContractTypeCrudService().findContractTypeById(idTipo);
		
		if (ct == null)
			throw new BusinessException("El tipo de contrato no existe");
		
		Printer.printContractType(ct);
	}

}
