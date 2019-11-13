package uo.ri.persistence.jpa;

import java.util.Date;
import java.util.List;

import uo.ri.business.repository.IntervencionRepository;
import uo.ri.model.Intervencion;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

public class InterventionJpaRepository extends BaseRepository<Intervencion>
		implements IntervencionRepository {

	@Override
	public List<Intervencion> findByMechanicIdBetweenDates(Long id,
			Date startDate, Date endDate) {

		return Jpa.getManager()
				.createNamedQuery("Intervencion.findByMechanicIdBetweenDates",
						Intervencion.class)
				.setParameter(1, id)
				.setParameter(2, startDate)
				.setParameter(3, endDate)
				.getResultList();
	}

}
