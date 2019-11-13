package uo.ri.business.impl.contractType.command;

import java.util.List;

import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.ContractTypeRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractType;

public class FindAllContractTypes implements Command<List<ContractTypeDto>> {

	private ContractTypeRepository repo = Factory.repository.forContractType();

	@Override
	public List<ContractTypeDto> execute() throws BusinessException {
		List<ContractType> contractTypes = repo.findAll();
		return DtoAssembler.toContractTypeDtoList(contractTypes);
	}

}
