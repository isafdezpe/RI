package uo.ri.associations;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import uo.ri.model.Association;
import uo.ri.model.Averia;
import uo.ri.model.Cliente;
import uo.ri.model.Mecanico;
import uo.ri.model.TipoVehiculo;
import uo.ri.model.Vehiculo;

public class AsignarTest {
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
		Association.Asignar.link(mecanico, averia);
	}

	@Test
	public void testAsignarLinked() {
		assertTrue(mecanico.getAsignadas().contains(averia));
		assertTrue(averia.getMecanico() == mecanico);
	}

	@Test
	public void testAsignarUnlink() {
		Association.Asignar.unlink(mecanico, averia);

		assertTrue(!mecanico.getAsignadas().contains(averia));
		assertTrue(mecanico.getAsignadas().size() == 0);
		assertTrue(averia.getMecanico() == null);
	}

	@Test
	public void testSafeReturn() {
		Set<Averia> asignadas = mecanico.getAsignadas();
		asignadas.remove(averia);

		assertTrue(asignadas.size() == 0);
		assertTrue("Se tiene que retornar copia de la colección",
				mecanico.getAsignadas().size() == 1);
	}

}
