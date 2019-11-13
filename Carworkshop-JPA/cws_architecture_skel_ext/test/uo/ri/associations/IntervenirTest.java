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
import uo.ri.model.TipoVehiculo;
import uo.ri.model.Vehiculo;

public class IntervenirTest {
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
		intervencion.setMinutos(60);
	}

	@Test
	public void testArreglarAdd() {
		assertTrue(averia.getIntervenciones().contains(intervencion));
		assertTrue(intervencion.getAveria() == averia);
	}

	@Test
	public void testArreglarRemove() {
		Association.Intervenir.unlink(intervencion);

		assertTrue(!averia.getIntervenciones().contains(intervencion));
		assertTrue(averia.getIntervenciones().size() == 0);
		assertTrue(intervencion.getAveria() == null);
	}

	@Test
	public void testTrabajarAdd() {
		assertTrue(mecanico.getIntervenciones().contains(intervencion));
		assertTrue(intervencion.getMecanico() == mecanico);
	}

	@Test
	public void testTrabajarRemove() {
		Association.Intervenir.unlink(intervencion);

		assertTrue(!mecanico.getIntervenciones().contains(intervencion));
		assertTrue(mecanico.getIntervenciones().size() == 0);
		assertTrue(intervencion.getMecanico() == null);
	}

	@Test
	public void testSafeReturnMecanico() {
		Set<Intervencion> intervenciones = mecanico.getIntervenciones();
		intervenciones.remove(intervencion);

		assertTrue(intervenciones.size() == 0);
		assertTrue(
				"Se debe retornar copia de la coleccion o hacerla de solo lectura",
				mecanico.getIntervenciones().size() == 1);
	}

	@Test
	public void testSafeReturnRepuesto() {
		Set<Intervencion> intervenciones = averia.getIntervenciones();
		intervenciones.remove(intervencion);

		assertTrue(intervenciones.size() == 0);
		assertTrue(
				"Se debe retornar copia de la coleccion o hacerla de solo lectura",
				averia.getIntervenciones().size() == 1);
	}

}
