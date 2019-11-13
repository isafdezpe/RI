package uo.ri.business.impl.contractType.command;

import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.ContractTypeRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractType;

public class FindContractTypeById implements Command<ContractTypeDto> {

	private Long id;
	private ContractTypeRepository repo = Factory.repository.forContractType();

	public FindContractTypeById(Long id) {
		this.id = id;
	}

	@Override
	public ContractTypeDto execute() throws BusinessException {
		ContractType ct = repo.findById(id);
		return ct == null ? null : DtoAssembler.toDto(ct);
	}

}
