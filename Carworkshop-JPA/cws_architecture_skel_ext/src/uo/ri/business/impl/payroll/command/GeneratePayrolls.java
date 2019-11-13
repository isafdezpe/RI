package uo.ri.business.impl.payroll.command;

import java.util.List;

import alb.util.date.Dates;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.business.repository.PayrollRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;
import uo.ri.model.Payroll;

public class GeneratePayrolls implements Command<Integer> {

	private MecanicoRepository repoMecanicos 
						= Factory.repository.forMechanic();
	private PayrollRepository repoNominas = Factory.repository.forPayroll();

	@Override
	public Integer execute() throws BusinessException {
		int cont = 0;
		List<Mecanico> mecanicos = repoMecanicos.findActive();
		for (Mecanico m : mecanicos) {
			Payroll p = new Payroll(m.getActiveContract(), Dates.now());
			repoNominas.add(p);
			cont++;
		}
		return cont;
	}

}
