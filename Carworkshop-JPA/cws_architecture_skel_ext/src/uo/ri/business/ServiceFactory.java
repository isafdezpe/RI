package uo.ri.business;

public interface ServiceFactory {

	// Admin services
	MechanicCrudService forMechanicCrudService();
	ContractCrudService forContractCrud();
	ContractTypeCrudService forContractTypeCrud();
	ContractCategoryCrudService forContractCategoryCrud();
	PayrollService forPayroll();

	// Cash use cases
	InvoiceService forInvoice();
	
	// Foreman use cases
	VehicleReceptionService forVehicleReception();

	// Mechanic services
	CloseBreakdownService forClosingBreakdown();

}
