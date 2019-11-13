package uo.ri.ui.admin.action.contractTypes;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;

public class UpdateTypeAction implements Action {

	@Override
	public void execute() throws BusinessException {
		// Pedir datos
		Long id = Console.readLong("Id del tipo"); 
		int dias = Console.readInt("Número de días de indemnización");

		// Procesar
		ContractTypeDto ct = new ContractTypeDto();
		ct.id = id;
		ct.compensationDays = dias;
		ServicesFactory.getContractTypeCrudService().updateContractType(ct);

		// Mostrar resultado
		Console.println("Tipo de contrato actualizado");
	}

}
