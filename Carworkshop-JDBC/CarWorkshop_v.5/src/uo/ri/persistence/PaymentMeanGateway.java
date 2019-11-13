package uo.ri.persistence;

import java.sql.SQLException;
import java.util.List;

import uo.ri.business.dto.PaymentMeanDto;

public interface PaymentMeanGateway extends SQLGateway {

	/**
	 * @param idFactura id de la factura a buscar
	 * @return lista de los métodos de pago de una factura,
	 * lista vacía si no hay ninguno
	 * @throws SQLException
	 */
	List<PaymentMeanDto> findPaymentMethodsForInvoice(Long idFactura) throws SQLException;

}
