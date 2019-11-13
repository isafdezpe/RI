package uo.ri.associations;

import static org.junit.Assert.assertTrue;

import java.util.Set;

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

public class SustituirTest {
	private Mecanico mecanico;
	private Averia averia;
	private Intervencion intervencion;
	private Repuesto repuesto;
	private Sustitucion sustitucion;
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
		intervencion.setMinutos(60);

		repuesto = new Repuesto("R1001", "junta la trocla", 100.0);
		sustitucion = new Sustitucion(repuesto, intervencion, 2);
	}

	@Test
	public void testSustituirAdd() {
		assertTrue(sustitucion.getIntervencion().equals(intervencion));
		assertTrue(sustitucion.getRepuesto().equals(repuesto));

		assertTrue(repuesto.getSustituciones().contains(sustitucion));
		assertTrue(intervencion.getSustituciones().contains(sustitucion));
	}

	@Test
	public void testSustituirRemove() {
		Association.Sustituir.unlink(sustitucion);

		assertTrue(sustitucion.getIntervencion() == null);
		assertTrue(sustitucion.getRepuesto() == null);

		assertTrue(!repuesto.getSustituciones().contains(sustitucion));
		assertTrue(repuesto.getSustituciones().size() == 0);

		assertTrue(!intervencion.getSustituciones().contains(sustitucion));
		assertTrue(intervencion.getSustituciones().size() == 0);
	}

	@Test
	public void testSafeReturnIntervencion() {
		Set<Sustitucion> sustituciones = intervencion.getSustituciones();
		sustituciones.remove(sustitucion);

		assertTrue(sustituciones.size() == 0);
		assertTrue(
				"Se debe retornar copia de la coleccion o hacerla de solo lectura",
				intervencion.getSustituciones().size() == 1);
	}

	@Test
	public void testSafeReturnRepuesto() {
		Set<Sustitucion> sustituciones = repuesto.getSustituciones();
		sustituciones.remove(sustitucion);

		assertTrue(sustituciones.size() == 0);
		assertTrue(
				"Se debe retornar copia de la coleccion o hacerla de solo lectura",
				repuesto.getSustituciones().size() == 1);
	}

}
