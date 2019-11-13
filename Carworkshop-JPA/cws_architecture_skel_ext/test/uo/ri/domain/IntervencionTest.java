package uo.ri.domain;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import uo.ri.model.Association;
import uo.ri.model.Averia;
import uo.ri.model.Cliente;
import uo.ri.model.Intervencion;
import uo.ri.model.Mecanico;
import uo.ri.model.Repuesto;
import uo.ri.model.Sustitucion;
import uo.ri.model.TipoVehiculo;
import uo.ri.model.Vehiculo;

public class IntervencionTest {

	private Mecanico mecanico;
	private Averia averia;
	private Vehiculo vehiculo;
	private TipoVehiculo tipoVehiculo;
	private Cliente cliente;

	@Before
	public void setUp() {
		cliente = new Cliente("dni-cliente", "nombre", "apellidos");
		vehiculo = new Vehiculo("1234 GJI", "seat", "ibiza");
		Association.Poseer.link(cliente, vehiculo);

		tipoVehiculo = new TipoVehiculo("coche", 50.0);
		Association.Clasificar.link(tipoVehiculo, vehiculo);

		averia = new Averia(vehiculo, "falla la junta la trocla");
		mecanico = new Mecanico("dni-mecanico", "nombre", "apellidos");
	}

	/**
	 * Una intervencion sin tiempo asignado ni repuestos da como precio 0 €
	 */
	@Test
	public void testAmountsZero() {
		Intervencion i = new Intervencion(mecanico, averia);

		assertTrue(i.getImporte() == 0.0);
	}

	/**
	 * Intervención con 60 minutos da importe del precio hora
	 */
	@Test
	public void testImporteHora() {
		Intervencion i = new Intervencion(mecanico, averia);
		i.setMinutos(60);

		assertTrue(i.getImporte() == tipoVehiculo.getPrecioHora());
	}

	/**
	 * Intervención con un solo repuesto da importe del respuesto
	 */
	@Test
	public void testImporteRepuesto() {
		Intervencion i = new Intervencion(mecanico, averia);
		Repuesto r = new Repuesto("R1001", "junta la trocla", 100.0);
		new Sustitucion(r, i, 1);

		assertTrue(i.getImporte() == r.getPrecio());
	}

	/**
	 * Intervención con tiempo y repuestos da el importe debido
	 */
	@Test
	public void testImporteIntervencionCompleta() {
		Intervencion i = new Intervencion(mecanico, averia);
		i.setMinutos(60);

		Repuesto r = new Repuesto("R1001", "junta la trocla", 100.0);
		new Sustitucion(r, i, 2);

		final double TOTAL = 50.0 // 60 mins * 50 €/hora tipo vehiculo
				+ 2 * 100.0; // 2 repuestos a 100.0

		assertTrue(i.getImporte() == TOTAL);
	}

}
