package uo.ri.business.service.implementation;

import java.util.Date;
import java.util.List;

import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.service.ContractCrudService;
import uo.ri.business.transaction.contractManagement.*;

public class ContractCrudServiceImplementation 
		implements ContractCrudService {

	/* (non-Javadoc)
	 * @see uo.ri.business.service.ContractCrudService#addContract(uo.ri.business.dto.ContractDto)
	 */
	@Override
	public void addContract(ContractDto c) throws BusinessException {
		new AddContractBusiness(c).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.ContractCrudService#updateContract(uo.ri.business.dto.ContractDto)
	 */
	@Override
	public void updateContract(ContractDto dto) throws BusinessException {
		new UpdateContractBusiness(dto).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.ContractCrudService#deleteContract(java.lang.Long)
	 */
	@Override
	public void deleteContract(Long id) throws BusinessException {
		new DeleteContractBusiness(id).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.ContractCrudService#finishContract(java.lang.Long, java.util.Date)
	 */
	@Override
	public void finishContract(Long id, Date endDate) throws BusinessException {
		new FinishContractBusiness(id, endDate).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.ContractCrudService#findContractById(java.lang.Long)
	 */
	@Override
	public ContractDto findContractById(Long id) throws BusinessException {
		return new FindContractByIdBusiness(id).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.ContractCrudService#findContractsByMechanicId(java.lang.Long)
	 */
	@Override
	public List<ContractDto> findContractsByMechanicId(Long id) 
			throws BusinessException {
		return new FindContractByMechanicIdBusiness(id).execute();
	}

}
