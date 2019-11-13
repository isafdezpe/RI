package uo.ri.business.impl.contractType.command;

import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.EntityAssembler;
import uo.ri.business.repository.ContractTypeRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractType;

public class AddContractType implements Command<Void> {

	private ContractTypeDto contractType;
	private ContractTypeRepository repo = Factory.repository.forContractType();

	public AddContractType(ContractTypeDto dto) {
		this.contractType = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		checkCanBeAdded(contractType);
		ContractType ct = EntityAssembler.toEntity(contractType);
		repo.add(ct);
		return null;
	}

	/**
	 * Comprueba que el tipo de contrato se pueda añadir
	 * @param ct : tipo de contrato
	 * @throws BusinessException si:
	 *  - ya existe un tipo de contrato con el mismo nombre
	 *  - los dias de compensacion son negativos
	 */
	private void checkCanBeAdded(ContractTypeDto ct) 
			throws BusinessException {
		BusinessCheck.isNull(repo.findByName(ct.name),
				"El tipo de contrato ya existe");
		BusinessCheck.isTrue(ct.compensationDays > 0, "No se puede añadir");
	}

}
