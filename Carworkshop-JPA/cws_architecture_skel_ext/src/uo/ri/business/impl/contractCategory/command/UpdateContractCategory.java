package uo.ri.business.impl.contractCategory.command;

import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractCategoryRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractCategory;

public class UpdateContractCategory implements Command<Void> {

	private ContractCategoryDto contractCategory;
	private ContractCategoryRepository repo = Factory.repository
			.forContractCategory();

	public UpdateContractCategory(ContractCategoryDto dto) {
		this.contractCategory = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		ContractCategory cc = repo.findById(contractCategory.id);
		checkCanBeUpdated(cc);
		cc.setProductivityPlus(contractCategory.productivityPlus);
		cc.setTrieniumSalary(contractCategory.trieniumSalary);
		return null;
	}

	/**
	 * Comprueba que la categoria pueda ser modificada
	 * @param cc : categoria
	 * @throws BusinessException si:
	 *  - la categoria no existe
	 *  - la productividad o el trienio son negativos
	 */
	void checkCanBeUpdated(ContractCategory cc) throws BusinessException {
		BusinessCheck.isNotNull(cc, "La categoría no existe");
		BusinessCheck.isTrue(
				cc.getProductivityPlus() > 0
						&& cc.getTrieniumSalary() > 0,
				"No se puede modificar");
	}
}
