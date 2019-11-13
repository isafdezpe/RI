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

public class SustitucionTest {

	private Mecanico mecanico;
	private Averia averia;
	private Intervencion intervencion;
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

		intervencion = new Intervencion(mecanico, averia);

	}

	/**
	 * Sustitución con menos de 1 repuesto lanza IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSustitucionThrowsExceptionIfZero() {
		Repuesto r = new Repuesto("R1001", "junta la trocla", 100.0);
		new Sustitucion(r, intervencion, 0);
	}

	/**
	 * Sustitución con menos de 1 repuesto lanza IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSustitucionThrowsExceptionIfNegative() {
		Repuesto r = new Repuesto("R1001", "junta la trocla", 100.0);
		new Sustitucion(r, intervencion, -1);
	}

	/**
	 * Importe correcto de la sustitución con varios repuestos
	 */
	@Test
	public void testImporteSustitucion() {
		Repuesto r = new Repuesto("R1001", "junta la trocla", 100.0);
		Sustitucion s = new Sustitucion(r, intervencion, 2);

		assertTrue(s.getImporte() == 200.0);
	}

}
