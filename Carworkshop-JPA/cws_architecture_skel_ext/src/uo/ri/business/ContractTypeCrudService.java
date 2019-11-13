package uo.ri.business;

import java.util.List;

import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessException;

public interface ContractTypeCrudService {

	/**
	 * 
	 * @param dto, the id field is meaningless for this operation
	 * @throws BusinessException if:
	 * 		- another type of contract with the same name already exist, or
	 * 		- the number of compensation days is negative
	 */
	void addContractType(ContractTypeDto dto) throws BusinessException;
	
	/**
	 * @param id, of the contract type
	 * @throws BusinessException if the contract type does not exist
	 */
	void deleteContractType(Long id) throws BusinessException;

	/**
	 * Updates just the compensation days
	 * @param dto
	 * @throws BusinessException if:
	 * 		- the contract type does not exist, or
	 * 		- the number of compensation days is negative
	 */
	void updateContractType(ContractTypeDto dto) throws BusinessException;
	
	/**
	 * @param id, of the contract type
	 * @return the dto with all the fields set, or
	 * 		null if does not exist the contract type
	 * @throws BusinessException DOES NOT
	 */
	ContractTypeDto findContractTypeById(Long id) throws BusinessException;
	
	/**
	 * @return a list with all the contract types registered, or
	 * 		an empty list if ther are none
	 * @throws BusinessException DOES NOT
	 */
	List<ContractTypeDto> findAllContractTypes() throws BusinessException;
	
}
