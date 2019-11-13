package uo.ri.business.impl.contractType.command;

import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractTypeRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractType;

public class DeleteContractType implements Command<Void> {

	private Long id;
	private ContractTypeRepository repo = Factory.repository.forContractType();

	public DeleteContractType(Long id) {
		this.id = id;
	}

	@Override
	public Void execute() throws BusinessException {
		ContractType ct = repo.findById(id);
		checkCanBeDeleted(ct);
		repo.remove(ct);
		return null;
	}

	/**
	 * Comprueba que el tipo de contrato pueda borrarse
	 * 
	 * @param ct : tipo de contrato
	 * @throws BusinessException si: 
	 * - el tipo no existe 
	 * - el tipo tiene contratos asignados
	 */
	private void checkCanBeDeleted(ContractType ct) throws BusinessException {
		BusinessCheck.isNotNull(ct, "El tipo de contrato no existe");
		BusinessCheck.isTrue(ct.getContracts().size() <= 0,
				"No puede ser borrado");
	}

}
