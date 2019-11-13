package uo.ri.business.service.implementation;

import java.util.List;

import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.service.ContractTypeCrudService;
import uo.ri.business.transaction.contractTypeManagement.*;

public class ContractTypeCrudServiceImplementation 
		implements ContractTypeCrudService {

	/* (non-Javadoc)
	 * @see uo.ri.business.service.ContractTypeCrudService#addContractType(uo.ri.business.dto.ContractTypeDto)
	 */
	@Override
	public void addContractType(ContractTypeDto dto) throws BusinessException {
		new AddContractTypeBusiness(dto).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.ContractTypeCrudService#deleteContractType(java.lang.Long)
	 */
	@Override
	public void deleteContractType(Long id) throws BusinessException {
		new DeleteContractTypeBusiness(id).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.ContractTypeCrudService#updateContractType(uo.ri.business.dto.ContractTypeDto)
	 */
	@Override
	public void updateContractType(ContractTypeDto dto) throws BusinessException {
		new UpdateContractTypeBusiness(dto).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.ContractTypeCrudService#findContractTypeById(java.lang.Long)
	 */
	@Override
	public ContractTypeDto findContractTypeById(Long id) throws BusinessException {
		return new FindContractTypeByIdBusiness(id).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.ContractTypeCrudService#findAllContractTypes()
	 */
	@Override
	public List<ContractTypeDto> findAllContractTypes() throws BusinessException {
		return new FindAllContractTypesBusiness().execute();
	}

}
