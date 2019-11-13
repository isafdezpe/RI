package uo.ri.business.impl.payroll.command;

import java.util.List;

import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.PayrollRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Payroll;

public class DeleteLastGeneratedPayrolls implements Command<Integer> {

	private PayrollRepository repo = Factory.repository.forPayroll();

	@Override
	public Integer execute() throws BusinessException {
		int cont = 0;
		List<Payroll> payrolls = repo.findLastGeneratedPayrolls();
		for (Payroll p : payrolls) {
			repo.remove(p);
			cont++;
		}
		return cont;
	}

}
