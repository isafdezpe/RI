package uo.ri.conf;

import uo.ri.business.service.*;
import uo.ri.business.service.implementation.*;

public class ServicesFactory {
	
	public static MechanicCrudService getMechanicService() {
		return new MechanicServiceImplementation();
	}
	
	public static InvoiceService getInvoiceSevice() {
		return new InvoiceServiceImplementation();
	}
	
	public static ContractCategoryCrudService getContractCategoryCrudService() {
		return new ContractCategoryCrudServiceImplementation();
	}

	public static ContractTypeCrudService getContractTypeCrudService() {
		return new ContractTypeCrudServiceImplementation();
	}
	
	public static ContractCrudService getContractCrudService() {
		return new ContractCrudServiceImplementation();
	}

	public static PayrollService getPayrollService() {
		return new PayrollServiceImplementation();
	}
	
}
