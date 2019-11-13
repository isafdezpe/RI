package uo.ri.domain;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import alb.util.date.Dates;
import uo.ri.model.Bono;
import uo.ri.model.Cargo;
import uo.ri.model.Cliente;
import uo.ri.model.Factura;
import uo.ri.model.Metalico;
import uo.ri.model.TarjetaCredito;

public class CargoTests {

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * A charge to a card increases the accumulated
	 */
	@Test
	public void testCargoTarjeta() {
		Date tomorrow = Dates.tomorrow();
		TarjetaCredito t = new TarjetaCredito("123", "visa", tomorrow);
		Factura f = new Factura(123L);

		new Cargo(f, t, 100.0);

		assertTrue(t.getAcumulado() == 100.0);
	}

	/**
	 * A charge to cash increases the accumulated
	 */
	@Test
	public void testCargoMetalico() {
		Metalico m = new Metalico(new Cliente("123", "n", "a"));
		Factura f = new Factura(123L);

		new Cargo(f, m, 100.0);

		assertTrue(m.getAcumulado() == 100.0);
	}

	/**
	 * A charge to a voucher increases the accumulated and decreases the
	 * available
	 */
	@Test
	public void testCargoBono() {
		Bono b = new Bono("123", 150.0, "For testing");
		Factura f = new Factura(123L);

		new Cargo(f, b, 100.0);

		assertTrue(b.getAcumulado() == 100.0);
		assertTrue(b.getDisponible() == 50.0);
	}

	/**
	 * A credit card cannot be charged if its expiration date is over
	 * 
	 * @throws IllegalStateException
	 */
	@Test(expected = IllegalStateException.class)
	public void tesChargeExpiredCard() {
		Date expired = Dates.yesterday();
		TarjetaCredito t = new TarjetaCredito("123", "TTT", expired);
		Factura f = new Factura(123L);

		new Cargo(f, t, 100.0); // Card validity date expired exception
	}

	/**
	 * A voucher cannot be charged if it has no available money
	 * 
	 * @throws IllegalStateException
	 */
	@Test(expected = IllegalStateException.class)
	public void testEmptyVoucherCannotBeCharged() {
		Bono b = new Bono("123", 150.0, "For testing");
		Factura f = new Factura(123L);

		new Cargo(f, b, 151.0); // Not enough money exception
	}

}
