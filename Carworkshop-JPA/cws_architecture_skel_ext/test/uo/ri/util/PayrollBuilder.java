package uo.ri.util;

import java.util.Date;

import alb.util.date.Dates;
import uo.ri.model.Contract;
import uo.ri.model.Payroll;

public class PayrollBuilder implements Builder<Payroll> {

	private Contract contract = new ContractBuilder().build();
	private Date date = Dates.addMonths(contract.getStartDate(), 1);
	private double totalOfInterventions = 0.0;

	@Override
	public Payroll build() {
		return new Payroll(contract, date, totalOfInterventions);
	}

	public PayrollBuilder forContract(Contract contract) {
		this.contract = contract;
		return this;
	}

	public PayrollBuilder withDate(Date date) {
		this.date = date;
		return this;
	}

	public PayrollBuilder withTotalOfInterventions(double totalOfInterventions) {
		this.totalOfInterventions = totalOfInterventions;
		return this;
	}

}
