package uo.ri.business.impl.payroll.command;

import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.business.repository.PayrollRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;
import uo.ri.model.Payroll;

public class DeleteLastPayrollForMechanic implements Command<Void> {

	private Long id;
	private MecanicoRepository repoMecanicos = Factory.repository.forMechanic();
	private PayrollRepository repoNominas = Factory.repository.forPayroll();

	public DeleteLastPayrollForMechanic(Long id) {
		this.id = id;
	}

	@Override
	public Void execute() throws BusinessException {
		Mecanico m = repoMecanicos.findById(id);
		Payroll p = m.getActiveContract().getLastPayroll();
		BusinessCheck.isNotNull(p, "La nómina no existe");
		repoNominas.remove(p);
		return null;
	}

}
