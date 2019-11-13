package uo.ri.business;

import java.util.List;

import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessException;

public interface ContractCategoryCrudService {

	/**
	 * Add a new category with the given data
	 * @param dto, the id value, if any, will be ignored
	 * @throws BusinessException if:
	 * 		- another category with the same name already exists, or
	 * 		- triennium salary is negative, or
	 * 		- productivity plus is negative
	 */
	void addContractCategory(ContractCategoryDto dto) throws BusinessException;
	
	/**
	 * Removes the given category
	 * @param id, of the category
	 * @throws BusinessException if:
	 * 		- the category does not exist, or
	 * 		- the category has contracts assigned
	 */
	void deleteContractCategory(Long id) throws BusinessException;

	/**
	 * Updates only the productivityPlus and the triennium salary 
	 * @param dto, only id, trienniumSalary and productivitySalary fields 
	 * 	are useful for this operation, the rest will be ignored
	 * @throws BusinessException if:
	 * 		- the category does not exist, or
	 * 		- the new trienniumSalary is negative, or
	 * 		- the new productivitySalary is negative
	 */
	void updateContractCategory(ContractCategoryDto dto) throws BusinessException;
	
	/**
	 * @param id, of the category
	 * @return the contract category dto, or 
	 * 		null if the id is not registered
	 * @throws BusinessException DOES NOT
	 */
	ContractCategoryDto findContractCategoryById(Long id) throws BusinessException;

	/**
	 * @return the list of all categories, or 
	 * 		an empty list if there is none
	 * @throws BusinessException DOES NOT
	 */
	List<ContractCategoryDto> findAllContractCategories() throws BusinessException;

}
