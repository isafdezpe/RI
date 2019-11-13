package uo.ri.util;

import java.util.Date;

import alb.util.date.Dates;
import uo.ri.model.Association;
import uo.ri.model.Contract;
import uo.ri.model.ContractCategory;
import uo.ri.model.ContractType;
import uo.ri.model.Mecanico;

public class ContractBuilder implements Builder<Contract> {
	private static final int DEFAULT_BASE_SALARY = 10000;
	
	private Mecanico mechanic = new MechanicBuilder().build();
	private ContractCategory category = new CategoryBuilder().build();
	private ContractType type = new ContractTypeBuilder().build();
	private Date startDate = Dates.addMonths( Dates.today(), 1); // next month
	private Date endDate = null;
	private double baseSalary = DEFAULT_BASE_SALARY;
	
	@Override
	public Contract build() {
		Contract c = new Contract(mechanic, startDate, endDate, baseSalary);
		Association.Categorize.link(c, category);
		Association.Typefy.link(c, type);
		return c;
	}

	public ContractBuilder withCategory(ContractCategory category) {
		this.category = category;
		return this;
	}

	public ContractBuilder withType(ContractType type) {
		this.type = type;
		return this;
	}

	public ContractBuilder withStartDate(Date startDate) {
		this.startDate = startDate;
		return this;
	}

	public ContractBuilder withEndDate(Date endDate) {
		this.endDate = endDate;
		return this;
	}

	public ContractBuilder withBaseSalary(double baseSalary) {
		this.baseSalary = baseSalary;
		return this;
	}

	public ContractBuilder forMechanic(Mecanico mechanic) {
		this.mechanic = mechanic;
		return this;
	}

}
