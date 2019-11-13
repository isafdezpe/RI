package uo.ri.business.impl.payroll.command;

import java.util.List;

import uo.ri.business.dto.PayrollDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.PayrollRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Payroll;

public class FindAllPayrolls implements Command<List<PayrollDto>> {

	private PayrollRepository repo = Factory.repository.forPayroll();

	@Override
	public List<PayrollDto> execute() throws BusinessException {
		List<Payroll> payrolls = repo.findAll();
		return DtoAssembler.toPayrollDtoList(payrolls);
	}

}
