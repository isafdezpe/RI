package uo.ri.business.repository;

import java.util.Date;
import java.util.List;

import uo.ri.model.Intervencion;

public interface IntervencionRepository extends Repository<Intervencion> {

	/**
	 * @param           id, refers to the mechanic id
	 * @param startDate
	 * @param endDate
	 * @return a list with all interventions done by the mechanic in between the
	 *         to dates (both included), or an empty list if there are none
	 */
	List<Intervencion> findByMechanicIdBetweenDates(Long id, Date startDate,
			Date endDate);

}
