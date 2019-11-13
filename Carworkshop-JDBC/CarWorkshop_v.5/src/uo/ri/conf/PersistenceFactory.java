package uo.ri.conf;

import uo.ri.persistence.*;
import uo.ri.persistence.implementation.*;

public class PersistenceFactory {

	public static MechanicGateway getMechanicGateway() {
		return new MechanicGatewayImplementation();
	}

	public static InvoiceGateway getInvoiceGateway() {
		return new InvoiceGatewayImplementation();
	}

	public static BreakdownGateway getBreakdownGateway() {
		return new BreakdownGatewayImplementation();
	}

	public static PaymentMeanGateway getPaymentMeanGateway() {
		return new PaymentMeanGatewayImplementation();
	}

	public static ContractCategoryGateway getContractCategoryGateway() {
		return new ContractCategoryGatewayImplementation();
	}

	public static ContractTypeGateway getContractTypeGateway() {
		return new ContractTypeGatewayImplementation();
	}

	public static ContractGateway getContractGateway() {
		return new ContractGatewayImplementation();
	}
	
	public static PayrollGateway getPayrollGateway() {
		return new PayrollGatewayImplementation();
	}
	
}
