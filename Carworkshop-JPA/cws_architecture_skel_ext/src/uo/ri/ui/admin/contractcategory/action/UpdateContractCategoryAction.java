package uo.ri.ui.admin.contractcategory.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ContractCategoryCrudService;
import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.Factory;

public class UpdateContractCategoryAction implements Action {

	@Override
	public void execute() throws BusinessException {

		Long id = Console.readLong("Id de la categoría de contrato");
		
		ContractCategoryCrudService service = Factory.service.forContractCategoryCrud();
		ContractCategoryDto dto = service.findContractCategoryById( id );
		BusinessCheck.isNotNull( dto, "No existe el tipo de contrato");
		
		dto.trieniumSalary = Console.readInt("Importe de trienio");		
		dto.productivityPlus = Console.readInt("Plus de productividad");		
		service.updateContractCategory( dto );
		
		Console.println("Tipo de contrato actualizado");

	}

}
