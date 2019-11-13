package uo.ri.associations;

import static org.junit.Assert.assertTrue;

import java.util.Set;

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

public class FacturarTest {
	private Mecanico mecanico;
	private Averia averia;
	private Intervencion intervencion;
	private Repuesto repuesto;
	private Vehiculo vehiculo;
	private TipoVehiculo tipoVehiculo;
	private Cliente cliente;
	private Factura factura;

	@Before
	public void setUp() {
		cliente = new Cliente("dni-cliente", "nombre", "apellidos");
		vehiculo = new Vehiculo("1234 GJI", "seat", "ibiza");
		Association.Poseer.link(cliente, vehiculo);

		tipoVehiculo = new TipoVehiculo("coche", 50.0);
		Association.Clasificar.link(tipoVehiculo, vehiculo);

		averia = new Averia(vehiculo, "falla la junta la trocla");
		mecanico = new Mecanico("dni-mecanico", "nombre", "apellidos");

		averia.assignTo(mecanico); // averia pasa a asignada

		intervencion = new Intervencion(mecanico, averia);
		intervencion.setMinutos(60);

		repuesto = new Repuesto("R1001", "junta la trocla", 100.0);
		new Sustitucion(repuesto, intervencion, 2);

		averia.markAsFinished(); // changes status & compute price

		factura = new Factura(0L, Dates.today());
		factura.addAveria(averia);
	}

	@Test
	public void testFacturarLinked() {
		assertTrue(factura.getAverias().contains(averia));
		assertTrue(factura.getImporte() > 0.0);

		assertTrue(averia.getFactura() == factura);
		assertTrue(averia.getStatus().equals(AveriaStatus.FACTURADA));
	}

	@Test
	public void testFacturarUnlink() {
		factura.removeAveria(averia);

		assertTrue(!factura.getAverias().contains(averia));
		assertTrue(factura.getAverias().size() == 0);
		assertTrue(factura.getImporte() == 0.0);

		assertTrue(averia.getFactura() == null);
		assertTrue(averia.getStatus().equals(AveriaStatus.TERMINADA));
	}

	@Test
	public void testSafeReturn() {
		Set<Averia> facturadas = factura.getAverias();
		facturadas.remove(averia);

		assertTrue(facturadas.size() == 0);
		assertTrue(
				"Se debe retornar copia de la coleccion o hacerla de solo lectura",
				factura.getAverias().size() == 1);
	}

}
