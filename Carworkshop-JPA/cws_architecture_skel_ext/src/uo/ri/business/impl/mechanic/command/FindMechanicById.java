package uo.ri.business.impl.mechanic.command;

import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;

public class FindMechanicById implements Command<MechanicDto> {

	private Long id;
	private MecanicoRepository repo = Factory.repository.forMechanic();

	public FindMechanicById(Long id) {
		this.id = id;
	}

	public MechanicDto execute() throws BusinessException {
		Mecanico m = repo.findById(id);
		return m == null ? null : DtoAssembler.toDto(m);
	}

}
