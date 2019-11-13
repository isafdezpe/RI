package uo.ri.business.repository;

import java.util.List;

import uo.ri.model.Payroll;

public interface PayrollRepository extends Repository<Payroll> {

	/**
	 * 
	 * @param id
	 * @return payrolls for the mechanic identified by id
	 */
	List<Payroll> findByMechanicId(Long id);

	/**
	 * 
	 * @return payrolls of last month
	 */
	List<Payroll> findLastGeneratedPayrolls();
}
