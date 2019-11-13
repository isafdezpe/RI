package uo.ri.ui.admin.contracttype.action;

import java.util.List;

import alb.util.menu.Action;
import uo.ri.business.ContractTypeCrudService;
import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.Factory;
import uo.ri.ui.util.Printer;

public class ListContractTypesAction implements Action {

	@Override
	public void execute() throws BusinessException {

		ContractTypeCrudService service = Factory.service.forContractTypeCrud();
		List<ContractTypeDto> cts = service.findAllContractTypes();
		
		for(ContractTypeDto t: cts) {
			Printer.printContractType( t );
		}
	}

}
