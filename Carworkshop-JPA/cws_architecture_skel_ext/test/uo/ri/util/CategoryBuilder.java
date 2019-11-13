package uo.ri.util;

import uo.ri.model.ContractCategory;

public class CategoryBuilder implements Builder<ContractCategory> {

	private static final double DEFAULT_PRODUCTIVITY_PLUS = 0.05;
	private static final double DEFAULT_TRIENNIUM_SALARY = 30.0;
	
	private String name = Values.newString("cat", 3);
	private double productivityPlus = DEFAULT_PRODUCTIVITY_PLUS;
	private double trienniumSalary = DEFAULT_TRIENNIUM_SALARY;

	@Override
	public ContractCategory build() {
		return new ContractCategory(name, trienniumSalary, productivityPlus);
	}

	public CategoryBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public CategoryBuilder withProductivityPlus(double productivityPlus) {
		this.productivityPlus = productivityPlus;
		return this;
	}

	public CategoryBuilder withTrienniumSalary(double trienniumSalary) {
		this.trienniumSalary = trienniumSalary;
		return this;
	}

}
