package uo.ri.business;

import java.util.Date;
import java.util.List;

import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessException;

public interface ContractCrudService {

	/**
	 * Adds new contract for the given mechanic. If the mechanic already has one
	 * that will be marked as extinguished and the compensation computed. 
	 * The start date of the contract will be set to the first day of the month 
	 * passed as startDate.
	 *  
	 * @param c, the contract dto. Only the fields mechanicId, typeId, 
	 * 		categoryId, startDate, endDate (if not null) and yearBaseSalary
	 * 		will be taken into account. The rest will be ignored for this operation.
	 * 
	 * @throws BusinessException if:
	 * 		- the contract type does not exist, or
	 * 		- the mechanic does not exist, or
	 * 		- the contract category does not exist, or
	 * 		- the given end date (if any) is not after the start date, or
	 * 		- the base year salary is a negative value
	 */
	void addContract(ContractDto c) throws BusinessException;
	
	/**
	 * Updates a contract that must be active.
	 * 
	 * Just the end date (if not null in the dto) and the year base
	 * salary will be updated. The id is used to locate the mechanic, 
	 * the rest of fields in the dto will be ignored.
	 * 
	 * If the endDate is given (not null) then it must be valid and will be 
	 * applied to the contract, if it is null then the endDate of the contract
	 * will be set to null. 
	 * 
	 * @param dto, just id, endDate and yearBase salary have value
	 * @throws BusinessException if:
	 * 		- the contract does not exist, or
	 * 		- the contract is no longer active, or
	 * 		- the base year salary is a negative value, or 
	 * 		- the endDate, being not null, is before the start date
	 */
	void updateContract(ContractDto dto) throws BusinessException;
	
	/**
	 * Removes the contract if possible...
	 * 
	 * @param id of the contract
	 * @throws BusinessException if:
	 * 		- the contract does not exist, or
	 * 		- the mechanic have had interventions during the contract period, or
	 * 		- the are payrolls generated for this contract
	 */
	void deleteContract(Long id) throws BusinessException;
	
	/**
	 * Marks the contract as finished and computes the compensation following
	 * the given rules.
	 *  
	 * @param id, of the contract
	 * @param endDate, the end date of the contract, but the real end date will
	 * 		be the last day of the month/year given by this date
	 *   
	 * @throws BusinessException if:
	 * 		- the contract does not exist, or
	 * 		- the contract is not active, or
	 * 		- the endDate passed is before the start date of the contract
	 */
	void finishContract(Long id, Date endDate) throws BusinessException;

	/**
	 * @param id, of the contract
	 * @return the dto for the contract with all the fields filled, or null
	 * 	if the contract does not exist
	 * @throws BusinessException DOES NOT
	 */
	ContractDto findContractById(Long id) throws BusinessException;
	
	/**
	 * @param id, of the mechanic
	 * @return a list with the dto corresponding to the specified mechanic, or
	 * 		an empty list if there is none 
	 * @throws BusinessException DOES NOT
	 */
	List<ContractDto> findContractsByMechanicId(Long id) throws BusinessException;

}
