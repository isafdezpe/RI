package uo.ri.business.service.implementation;

import java.util.List;

import uo.ri.business.dto.PayrollDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.service.PayrollService;
import uo.ri.business.transaction.payrollManagement.DeleteLastGeneratedPayrollsBusiness;
import uo.ri.business.transaction.payrollManagement.DeleteLastPayrollForMechanicBusiness;
import uo.ri.business.transaction.payrollManagement.FindAllPayrollsBusiness;
import uo.ri.business.transaction.payrollManagement.FindPayrollByIdBusiness;
import uo.ri.business.transaction.payrollManagement.FindPayrollsByMechanicId;
import uo.ri.business.transaction.payrollManagement.GeneratePayrollsBusiness;

public class PayrollServiceImplementation implements PayrollService {

	/* (non-Javadoc)
	 * @see uo.ri.business.service.PayrollService#findAllPayrolls()
	 */
	@Override
	public List<PayrollDto> findAllPayrolls() throws BusinessException {
		return new FindAllPayrollsBusiness().execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.PayrollService#findPayrollsByMechanicId(java.lang.Long)
	 */
	@Override
	public List<PayrollDto> findPayrollsByMechanicId(Long id) 
			throws BusinessException {
		return new FindPayrollsByMechanicId(id).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.PayrollService#findPayrollById(java.lang.Long)
	 */
	@Override
	public PayrollDto findPayrollById(Long id) throws BusinessException {
		return new FindPayrollByIdBusiness(id).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.PayrollService#deleteLastPayrollForMechanicId(java.lang.Long)
	 */
	@Override
	public void deleteLastPayrollForMechanicId(Long id) throws BusinessException {
		new DeleteLastPayrollForMechanicBusiness(id).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.PayrollService#deleteLastGenetaredPayrolls()
	 */
	@Override
	public int deleteLastGenetaredPayrolls() throws BusinessException {
		return new DeleteLastGeneratedPayrollsBusiness().execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.PayrollService#generatePayrolls()
	 */
	@Override
	public int generatePayrolls() throws BusinessException {
		return new GeneratePayrollsBusiness().execute();
	}

}
