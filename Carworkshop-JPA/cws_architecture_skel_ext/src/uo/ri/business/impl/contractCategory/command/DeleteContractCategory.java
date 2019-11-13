package uo.ri.business.impl.contractCategory.command;

import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractCategoryRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractCategory;

public class DeleteContractCategory implements Command<Void> {

	private Long id;
	private ContractCategoryRepository repo = Factory.repository
			.forContractCategory();

	public DeleteContractCategory(Long id) {
		this.id = id;
	}

	@Override
	public Void execute() throws BusinessException {
		ContractCategory cc = repo.findById(id);
		checkCanBeDeleted(cc);
		repo.remove(cc);
		return null;
	}

	/**
	 * Comprueba que la categoría pueda borrarse
	 * 
	 * @param cc : categoria
	 * @throws BusinessException si: 
	 * - la categoria no existe 
	 * - la categoria tiene contratos asignados
	 */
	private void checkCanBeDeleted(ContractCategory cc)
			throws BusinessException {
		BusinessCheck.isNotNull(cc, "La categoria de contrato no existe");
		BusinessCheck.isTrue(cc.getContracts().size() <= 0,
				"No puede ser borrado");
	}

}
