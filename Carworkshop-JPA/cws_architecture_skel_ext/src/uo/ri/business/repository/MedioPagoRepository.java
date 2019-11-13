package uo.ri.business.repository;

import java.util.List;

import uo.ri.model.MedioPago;

public interface MedioPagoRepository extends Repository<MedioPago> {

	/**
	 * @param id of the client
	 * @return a list with all the payment means owned by the client
	 */
	List<MedioPago> findPaymentMeansByClientId(Long id);
}
