package uo.ri.util;

import uo.ri.model.ContractType;

public class ContractTypeBuilder implements Builder<ContractType> {

	private String name = Values.newString("type", 3);
	private int compensationDays = 30;

	@Override
	public ContractType build() {
		return new ContractType(name, compensationDays);
	}

	public ContractTypeBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public ContractTypeBuilder withCompensationDays(int compensationDays) {
		this.compensationDays = compensationDays;
		return this;
	}

}
