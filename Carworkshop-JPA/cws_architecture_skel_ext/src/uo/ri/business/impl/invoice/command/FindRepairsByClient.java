package uo.ri.business.impl.invoice.command;

import java.util.List;

import uo.ri.business.dto.BreakdownDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.AveriaRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Averia;

public class FindRepairsByClient implements Command<List<BreakdownDto>> {

	private String dni;
	private AveriaRepository repoAverias = Factory.repository.forAveria();

	public FindRepairsByClient(String dni) {
		this.dni = dni;
	}

	@Override
	public List<BreakdownDto> execute() throws BusinessException {
		List<Averia> averias = repoAverias.findNoFacturadasByDni(dni);
		return DtoAssembler.toBreakdownDtoList(averias);
	}

}
