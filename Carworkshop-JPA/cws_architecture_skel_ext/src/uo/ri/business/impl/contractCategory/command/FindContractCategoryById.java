package uo.ri.business.impl.contractCategory.command;

import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.ContractCategoryRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractCategory;

public class FindContractCategoryById implements Command<ContractCategoryDto> {

	private Long id;
	private ContractCategoryRepository repo = Factory.repository
			.forContractCategory();

	public FindContractCategoryById(Long id) {
		this.id = id;
	}

	@Override
	public ContractCategoryDto execute() throws BusinessException {
		ContractCategory cc = repo.findById(id);
		return cc == null ? null : DtoAssembler.toDto(cc);
	}

}
