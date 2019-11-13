package uo.ri.persistence.jpa;

import java.util.List;

import uo.ri.business.repository.MedioPagoRepository;
import uo.ri.model.MedioPago;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

public class MedioPagoJpaRepository extends BaseRepository<MedioPago>
		implements MedioPagoRepository {

	@Override
	public List<MedioPago> findPaymentMeansByClientId(Long id) {
		return Jpa.getManager()
				.createNamedQuery("MedioPago.findByClientId", MedioPago.class)
				.setParameter(1, id)
				.getResultList();
	}

}
