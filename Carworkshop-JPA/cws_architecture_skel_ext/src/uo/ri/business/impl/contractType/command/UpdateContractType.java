package uo.ri.business.impl.contractType.command;

import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractTypeRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractType;

public class UpdateContractType implements Command<Void> {

	private ContractTypeDto contractType;
	private ContractTypeRepository repo = Factory.repository.forContractType();

	public UpdateContractType(ContractTypeDto dto) {
		this.contractType = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		ContractType ct = repo.findById(contractType.id);
		checkCanBeUpdated(ct);
		ct.setCompensationDays(contractType.compensationDays);
		return null;
	}
	
	/**
	 * Comprueba que el tipo de contrato se pueda añadir
	 * @param ct : tipo de contrato
	 * @throws BusinessException si:
	 *  - ya existe un tipo de contrato con el mismo nombre
	 *  - los dias de compensacion son negativos
	 */
	private void checkCanBeUpdated(ContractType ct) 
			throws BusinessException {
		BusinessCheck.isNotNull(repo.findByName(ct.getName()),
				"El tipo de contrato no existe");
		BusinessCheck.isTrue(ct.getCompensationDays() > 0, 
				"No se puede modificar");
	}

}
