package uo.ri.business.impl.mechanic.command;

import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;

public class DeleteMechanic implements Command<Void> {

	private Long idMecanico;
	private MecanicoRepository repo = Factory.repository.forMechanic();

	public DeleteMechanic(Long idMecanico) {
		this.idMecanico = idMecanico;
	}

	public Void execute() throws BusinessException {
		Mecanico m = repo.findById(idMecanico);
		BusinessCheck.isNotNull(m, "El mecánico no existe");
		checkCanBeDeleted(m);
		repo.remove(m);
		return null;
	}

	/**
	 * Comprueba que un mecanico pueda ser borrado
	 * @param m
	 * @throws BusinessException si:
	 *  - tiene averias asignadas
	 *  - tiene intervenciones
	 *  - tiene contratos
	 */
	private void checkCanBeDeleted(Mecanico m) throws BusinessException {
		BusinessCheck.isTrue(m.getIntervenciones().size() <= 0,
				"No puede ser borrado");
		BusinessCheck.isTrue(m.getAsignadas().size() <= 0,
				"No puede ser borrado");
		BusinessCheck.isTrue(m.getContracts().size() <= 0, 
				"No puede ser borrado");
	}

}
