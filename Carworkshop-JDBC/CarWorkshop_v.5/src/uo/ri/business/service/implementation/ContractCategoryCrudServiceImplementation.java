package uo.ri.business.service.implementation;

import java.util.List;

import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.service.ContractCategoryCrudService;
import uo.ri.business.transaction.contractCategoryManagement.AddContractCategoryBusiness;
import uo.ri.business.transaction.contractCategoryManagement.DeleteContractCategoryBusiness;
import uo.ri.business.transaction.contractCategoryManagement.FindAllContractCategoriesBusiness;
import uo.ri.business.transaction.contractCategoryManagement.FindContractCategoryByIdBusiness;
import uo.ri.business.transaction.contractCategoryManagement.UpdateContractCategoryBusiness;

public class ContractCategoryCrudServiceImplementation 
		implements ContractCategoryCrudService {

	/* (non-Javadoc)
	 * @see uo.ri.business.service.ContractCategoryCrudService#addContractCategory(uo.ri.business.dto.ContractCategoryDto)
	 */
	@Override
	public void addContractCategory(ContractCategoryDto dto) 
			throws BusinessException {
		new AddContractCategoryBusiness(dto).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.ContractCategoryCrudService#deleteContractCategory(java.lang.Long)
	 */
	@Override
	public void deleteContractCategory(Long id) 
			throws BusinessException {
		new DeleteContractCategoryBusiness(id).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.ContractCategoryCrudService#updateContractCategory(uo.ri.business.dto.ContractCategoryDto)
	 */
	@Override
	public void updateContractCategory(ContractCategoryDto dto) 
			throws BusinessException {
		new UpdateContractCategoryBusiness(dto).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.ContractCategoryCrudService#findContractCategoryById(java.lang.Long)
	 */
	@Override
	public ContractCategoryDto findContractCategoryById(Long id) 
			throws BusinessException {
		return new FindContractCategoryByIdBusiness(id).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.ContractCategoryCrudService#findAllContractCategories()
	 */
	@Override
	public List<ContractCategoryDto> findAllContractCategories() 
			throws BusinessException {
		return new FindAllContractCategoriesBusiness().execute();
	}

}
