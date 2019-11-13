package uo.ri.ui.admin.contractcategory.action;

import java.util.List;

import alb.util.menu.Action;
import uo.ri.business.ContractCategoryCrudService;
import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.Factory;
import uo.ri.ui.util.Printer;

public class ListContractCategoriesAction implements Action {

	@Override
	public void execute() throws BusinessException {

		ContractCategoryCrudService service = Factory.service.forContractCategoryCrud();
		List<ContractCategoryDto> cts = service.findAllContractCategories();
		
		for(ContractCategoryDto t: cts) {
			Printer.printContractCategory( t );
		}

	}

}
