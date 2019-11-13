package uo.ri.domain;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import alb.util.date.Dates;
import uo.ri.model.Association;
import uo.ri.model.Averia;
import uo.ri.model.Cliente;
import uo.ri.model.Factura;
import uo.ri.model.Intervencion;
import uo.ri.model.Mecanico;
import uo.ri.model.Repuesto;
import uo.ri.model.Sustitucion;
import uo.ri.model.TipoVehiculo;
import uo.ri.model.Vehiculo;
import uo.ri.model.types.AveriaStatus;
import uo.ri.model.types.FacturaStatus;

public class FacturaTest {

	private Mecanico mecanico;
	private Averia averia;
	private Intervencion intervencion;
	private Repuesto repuesto;
	private Vehiculo vehiculo;
	private TipoVehiculo tipoVehiculo;
	private Cliente cliente;

	@Before
	public void setUp() {
		cliente = new Cliente("dni-cliente", "nombre", "apellidos");
		vehiculo = new Vehiculo("1234 GJI", "ibiza", "seat");
		Association.Poseer.link(cliente, vehiculo);

		tipoVehiculo = new TipoVehiculo("coche", 50.0);
		Association.Clasificar.link(tipoVehiculo, vehiculo);

		averia = new Averia(vehiculo, "falla la junta la trocla");
		mecanico = new Mecanico("dni-mecanico", "nombre", "apellidos");
		averia.assignTo(mecanico);

		intervencion = new Intervencion(mecanico, averia);
		intervencion.setMinutos(60);

		repuesto = new Repuesto("R1001", "junta la trocla", 100.0);
		new Sustitucion(repuesto, intervencion, 2);

		averia.markAsFinished();
	}

	/**
	 * Calculo del importe de factura con una avería de 260€ + IVA 21% La averia
	 * se añade en el constructor
	 */
	@Test
	public void testImporteFactura() {
		List<Averia> averias = new ArrayList<>();
		averias.add(averia);
		Factura factura = new Factura(0L, averias);

		assertTrue(factura.getImporte() == 302.5);
	}

	/**
	 * Calculo del importe de factura con una avería de 260€ + IVA 21% La averia
	 * se añade por asociación
	 */
	@Test
	public void testImporteFacturaAddAveria() {
		Factura factura = new Factura(0L); // 0L es el numero de factura
		factura.addAveria(averia);

		assertTrue(factura.getImporte() == 302.5);
	}

	/**
	 * Dos averias añadidas a la factura en el constructor
	 */
	@Test
	public void testImporteFacturadDosAverias() {
		List<Averia> averias = new ArrayList<Averia>();
		averias.add(averia);
		averias.add(crearOtraAveria());
		Factura factura = new Factura(0L, averias);

		// importe = (137.5 nueva averia + 250.0 primera averia) * 21% iva
		assertTrue(factura.getImporte() == 468.88); // redondeo a 2 céntimos
	}

	/**
	 * Dos averias añadidas a la factura por asociación
	 */
	@Test
	public void testImporteFacturaAddDosAverias() {
		Factura factura = new Factura(0L);
		factura.addAveria(averia);
		factura.addAveria(crearOtraAveria());

		assertTrue(factura.getImporte() == 468.88); // redondeo a 2 céntimos
	}

	/**
	 * Una factura creada y con averías está SIN_ABONAR
	 */
	@Test
	public void testFacturaCreadaSinAbonar() {
		List<Averia> averias = new ArrayList<Averia>();
		averias.add(averia);
		Factura factura = new Factura(0L, averias);

		assertTrue(factura.getStatus() == FacturaStatus.SIN_ABONAR);
	}

	/**
	 * Si la factura es anterior al 1/7/2012 el IVA es el 18%, el importe es
	 * 250€ + IVA 18%
	 */
	@Test
	public void testImporteFacturaAntesDeJulio() {
		Date JUNE_6_2012 = Dates.fromString("15/6/2012");

		List<Averia> averias = new ArrayList<Averia>();
		averias.add(averia);
		Factura factura = new Factura(0L, JUNE_6_2012, averias); // iva 18%

		assertTrue(factura.getImporte() == 295.0);
	}

	/**
	 * Una avería al añadirla a una factura cambia su estado a FACTURADA al
	 * añadirla por constructor
	 */
	@Test
	public void testAveriasFacturadas() {
		List<Averia> averias = Arrays.asList(averia);
		new Factura(0L, averias);

		assertTrue(averia.getStatus() == AveriaStatus.FACTURADA);
	}

	/**
	 * Una averia al añadirla a una factura cambia su estado a FACTURADA al
	 * añadirla por asociación
	 */
	@Test
	public void testAveriasFacturadasAddAveria() {
		new Factura(0L).addAveria(averia);

		assertTrue(averia.getStatus() == AveriaStatus.FACTURADA);
	}

	/**
	 * Varias averias al añadirlas a una factura cambian su estado a FACTURADA
	 */
	@Test
	public void testDosAveriasFacturadasAddAveria() {
		Averia otraAveria = crearOtraAveria();

		Factura f = new Factura(0L);
		f.addAveria(averia);
		f.addAveria(otraAveria);

		assertTrue(averia.getStatus() == AveriaStatus.FACTURADA);
		assertTrue(otraAveria.getStatus() == AveriaStatus.FACTURADA);
	}

	/**
	 * La fecha devuelta por getFecha() es una copia de la interna
	 */
	@Test
	public void testGetFechaReturnsCopy() {
		Factura f = new Factura(0L);
		Date one = f.getFecha();
		Date another = f.getFecha();

		assertTrue(one != another);
		assertTrue(one.equals(another));
	}

	/**
	 * La fecha que se pasa en el constructor se copia para ser interna
	 */
	@Test
	public void testContructorCopiesDate() {
		Date date = new Date();
		Date copy = new Date(date.getTime());

		Factura f = new Factura(0L, date);
		date.setTime(0L); // 1/1/1970 00:00
		Date gotten = f.getFecha();

		assertTrue(gotten != date);
		assertTrue(!gotten.equals(date));
		assertTrue(gotten.equals(copy));
	}

	/**
	 * La fecha que se pasa en setDate() se copia para ser interna
	 */
	@Test
	public void testSetterCopiesDate() {
		Date now = new Date();
		Factura f = new Factura(0L);

		f.setFecha(now);
		now.setTime(0L);
		Date date = f.getFecha();

		assertTrue(!now.equals(date));
	}

	/**
	 * Genera nueva factura esperando 100 milisegundos para evitar que coincida
	 * el campo fecha si se generan dos facturas muy seguidas (en el mismo
	 * milisegundo)
	 * 
	 * Puede dar problemas si la fecha forma parte de la identidad de la avería
	 * 
	 * @return la avería creada
	 */
	private Averia crearOtraAveria() {
		sleep(100);
		Averia averia = new Averia(vehiculo,
				"falla la junta la trocla otra vez");
		averia.assignTo(mecanico);

		Intervencion i = new Intervencion(mecanico, averia);
		i.setMinutos(45);

		new Sustitucion(repuesto, i, 1);

		averia.markAsFinished();

		// importe = 100 repuesto + 37.5 mano de obra
		return averia;
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ignored) {
			// dont't care if this occurs
		}
	}

}
