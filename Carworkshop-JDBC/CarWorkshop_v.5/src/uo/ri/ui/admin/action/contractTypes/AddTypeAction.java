package uo.ri.ui.admin.action.contractTypes;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.ServicesFactory;

public class AddTypeAction implements Action{

	@Override
	public void execute() throws BusinessException {
		
		// Pedir datos
		String nombre = Console.readString("Nombre del tipo"); 
		int dias = Console.readInt("Número de días de indemnización"); 
		
		// Procesar
		ContractTypeDto ct = new ContractTypeDto();
		ct.name = nombre;
		ct.compensationDays = dias;
		ServicesFactory.getContractTypeCrudService().addContractType(ct);
		
		// Mostrar resultado
		Console.println("Nuevo tipo añadido");
	}
	
}
