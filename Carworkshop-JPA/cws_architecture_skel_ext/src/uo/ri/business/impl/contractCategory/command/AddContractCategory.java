package uo.ri.business.impl.contractCategory.command;

import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.EntityAssembler;
import uo.ri.business.repository.ContractCategoryRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractCategory;

public class AddContractCategory implements Command<Void> {

	private ContractCategoryDto contractCategory;
	private ContractCategoryRepository repo = Factory.repository
			.forContractCategory();

	public AddContractCategory(ContractCategoryDto dto) {
		this.contractCategory = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		checkCanBeAdded(contractCategory);
		ContractCategory ct = EntityAssembler.toEntity(contractCategory);
		repo.add(ct);
		return null;
	}

	/**
	 * Comprueba que la categoria pueda añadirse
	 * 
	 * @param cc : categoria
	 * @throws BusinessException: 
	 * - si ya existe una categoria del mismo nombre
	 * - si el plus de productividad o el trienio son negativos
	 */
	private void checkCanBeAdded(ContractCategoryDto cc)
			throws BusinessException {
		BusinessCheck.isNull(repo.findByName(cc.name),
				"La categoría ya existe");
		BusinessCheck.isTrue(cc.productivityPlus > 0 && cc.trieniumSalary > 0,
				"No se puede añadir");
	}

}
