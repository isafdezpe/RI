package uo.ri.persistence.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import alb.util.date.Dates;
import uo.ri.business.repository.PayrollRepository;
import uo.ri.model.Payroll;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

public class PayrollJpaRepository extends BaseRepository<Payroll>
		implements PayrollRepository {

	@Override
	public List<Payroll> findByMechanicId(Long id) {
		return Jpa.getManager()
				.createNamedQuery("Payroll.findByMechanicId", Payroll.class)
				.setParameter(1, id)
				.getResultList();
	}

	@Override
	public List<Payroll> findLastGeneratedPayrolls() {
		Date d = Dates.lastDayOfMonth(Dates.subMonths(Dates.now(), 1));
		return Jpa.getManager()
				.createNamedQuery("Payroll.findLastPayrolls", Payroll.class)
				.setParameter(1, d, TemporalType.DATE)
				.getResultList();
	}

}
