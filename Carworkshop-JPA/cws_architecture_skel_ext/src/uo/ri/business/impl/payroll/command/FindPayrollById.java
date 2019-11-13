package uo.ri.business.impl.payroll.command;

import uo.ri.business.dto.PayrollDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.PayrollRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Payroll;

public class FindPayrollById implements Command<PayrollDto> {

	private Long id;
	private PayrollRepository repo = Factory.repository.forPayroll();

	public FindPayrollById(Long id) {
		this.id = id;
	}

	@Override
	public PayrollDto execute() throws BusinessException {
		Payroll p = repo.findById(id);
		return p == null ? null : DtoAssembler.toDto(p);
	}

}
