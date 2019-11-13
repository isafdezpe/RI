package uo.ri.persistence.jpa;

import java.util.List;

import uo.ri.business.repository.AveriaRepository;
import uo.ri.model.Averia;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

public class AveriaJpaRepository extends BaseRepository<Averia>
		implements AveriaRepository {

	@Override
	public List<Averia> findByIds(List<Long> idsAveria) {
		return Jpa.getManager()
				.createNamedQuery("Averia.findByIds", Averia.class)
				.setParameter(1, idsAveria)
				.getResultList();
	}

	@Override
	public List<Averia> findNoFacturadasByDni(String dni) {
		return Jpa.getManager()
				.createNamedQuery("Averia.findNoFacturadasByDni", Averia.class)
				.setParameter(1, dni)
				.getResultList();
	}

}
