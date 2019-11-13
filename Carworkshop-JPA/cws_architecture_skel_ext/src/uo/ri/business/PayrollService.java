package uo.ri.business;

import java.util.List;

import uo.ri.business.dto.PayrollDto;
import uo.ri.business.exception.BusinessException;

public interface PayrollService {

	/**
	 * @return a list with all PayrollDto registered or an empty list
	 * @throws BusinessException DOES NOT
	 */
	List<PayrollDto> findAllPayrolls() throws BusinessException;
	
	/**
	 * @param id of the mechanic
	 * @return a list with PayrollDto of the mechanic, or 
	 * 		an empty list if the mechanic has none
	 * @throws BusinessException DOES NOT
	 */
	List<PayrollDto> findPayrollsByMechanicId(Long id) throws BusinessException;
	
	/**
	 * @param id of the payroll
	 * @return the PayrtolDto object for payroll, or
	 * 		null if does not exist
	 * @throws BusinessException DOES NOT
	 */
	PayrollDto findPayrollById(Long id) throws BusinessException;
	
	/**
	 * @param id
	 * @throws BusinessException if there are no payroll with that id
	 */
	void deleteLastPayrollForMechanicId(Long id) throws BusinessException;
	
	/**
	 * Deletes all the payrolls generated the same day as the most recent 
	 * payroll registered in the system
	 * @return the number of deleted payrolls, might be 0
	 * @throws BusinessException DOES NOT
	 */
	int deleteLastGenetaredPayrolls() throws BusinessException;
	
	/**
	 * Generates the payrolls for all the active contracts and for those 
	 * which have been extinguished the month for which the payrolls are 
	 * being generated:
	 * 		month(contract.endDate) == month of the payroll generation
	 * 
	 * @return the number of payrolls generated, might be 0
	 * @throws BusinessException DOES NOT
	 */
	int generatePayrolls() throws BusinessException;

}
