package uo.ri.associations;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import uo.ri.model.Association;
import uo.ri.model.Averia;
import uo.ri.model.Cliente;
import uo.ri.model.TipoVehiculo;
import uo.ri.model.Vehiculo;

public class AveriarTest {
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
	}

	@Test
	public void testAveriarLinked() {
		// The constructor of "Averia" creates the link with "vehiculo"
		// It calls Association.Averiar.link(...)
		assertTrue(vehiculo.getAverias().contains(averia));
		assertTrue(averia.getVehiculo() == vehiculo);
		assertTrue(vehiculo.getNumAverias() == 1);
	}

	@Test
	public void testAveriarUnlink() {
		Association.Averiar.unlink(vehiculo, averia);

		assertTrue(!vehiculo.getAverias().contains(averia));
		assertTrue(averia.getVehiculo() == null);
	}

	@Test
	public void testAveriarUnlinkTwice() {
		Association.Averiar.unlink(vehiculo, averia);
		Association.Averiar.unlink(vehiculo, averia);

		assertTrue(!vehiculo.getAverias().contains(averia));
		assertTrue(averia.getVehiculo() == null);
	}

	@Test
	public void testSafeReturn() {
		Set<Averia> averias = vehiculo.getAverias();
		averias.remove(averia);

		assertTrue(averias.size() == 0);
		assertTrue("Se tiene que retornar copia de la coleccion",
				vehiculo.getAverias().size() == 1);
	}

}
