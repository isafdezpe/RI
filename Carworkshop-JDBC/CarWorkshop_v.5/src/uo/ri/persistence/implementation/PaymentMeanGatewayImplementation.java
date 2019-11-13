package uo.ri.persistence.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CardDto;
import uo.ri.business.dto.CashDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.dto.VoucherDto;
import uo.ri.conf.Conf;
import uo.ri.persistence.PaymentMeanGateway;

public class PaymentMeanGatewayImplementation implements PaymentMeanGateway {
	
	private Connection c;

	/* (non-Javadoc)
	 * @see uo.ri.persistence.SQLGateway#setConnection(java.sql.Connection)
	 */
	@Override
	public void setConnection(Connection c) {
		this.c = c;
	}

	/* (non-Javadoc)
	 * @see uo.ri.persistence.PaymentMeanGateway#findPaymentMethodsForInvoice(java.lang.Long)
	 */
	@Override
	public List<PaymentMeanDto> findPaymentMethodsForInvoice(Long idFactura) 
			throws SQLException {
		List<PaymentMeanDto> paymentMeans = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getConnection();
			
			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("SQL_PAYMENT_MEANS_FOR_INVOICE"));
			pst.setLong(1, idFactura);
			
			rs = pst.executeQuery();
			
			while (rs.next()) {
				PaymentMeanDto p;
				if (rs.getString(4).equals("TTarjetasCredito")) {
					p = new CardDto();
				} else if (rs.getString(4).equals("TMetalico")) {
					p = new CashDto();
				} else {
					p = new VoucherDto();
				}
				p.id = rs.getLong(1);
				p.clientId = rs.getLong(2);
				p.accumulated = rs.getDouble(3);
				paymentMeans.add(p);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			Jdbc.close(rs, pst, c);
		}
		
		return paymentMeans;
	}

}
