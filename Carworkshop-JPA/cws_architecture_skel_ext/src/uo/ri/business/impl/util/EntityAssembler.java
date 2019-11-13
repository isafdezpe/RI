package uo.ri.business.impl.util;

import uo.ri.business.dto.ClientDto;
import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.dto.MechanicDto;
import uo.ri.model.Cliente;
import uo.ri.model.ContractCategory;
import uo.ri.model.ContractType;
import uo.ri.model.Mecanico;
import uo.ri.model.types.Address;

public class EntityAssembler {

	public static Mecanico toEntity(MechanicDto dto) {
		return new Mecanico(dto.dni, dto.name, dto.surname);
	}

	public static Cliente toEntity(ClientDto dto) {
		Cliente c = new Cliente(dto.dni, dto.name, dto.surname);
		Address addr = new Address(dto.addressStreet, dto.addressCity,
				dto.addressZipcode);
		c.setAddress(addr);
		c.setPhone(dto.phone);
		c.setEmail(dto.email);
		return c;
	}

	public static ContractType toEntity(ContractTypeDto dto) {
		return new ContractType(dto.name, dto.compensationDays);
	}

	public static ContractCategory toEntity(ContractCategoryDto dto) {
		return new ContractCategory(dto.name, dto.trieniumSalary,
				dto.productivityPlus);
	}

}
