package uo.ri.domain;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uo.ri.business.exception.BusinessException;
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
import uo.ri.model.types.FacturaStatus;

/**
 * Para entender mejor estos test repasa el diagrama de estados de una avería en
 * la documentación del problema de referencia
 */
public class AveriaTest {

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

		tipoVehiculo = new TipoVehiculo("coche", 50.0 /* €/hora */);
		Association.Clasificar.link(tipoVehiculo, vehiculo);

		averia = new Averia(vehiculo, "falla la junta la trocla");
		mecanico = new Mecanico("dni-mecanico", "nombre", "apellidos");
		averia.assignTo(mecanico);

		intervencion = new Intervencion(mecanico, averia);
		intervencion.setMinutos(60);

		repuesto = new Repuesto("R1001", "junta la trocla", 100.0 /* € */);
		new Sustitucion(repuesto, intervencion, 2);

		averia.markAsFinished(); // changes status & computes price
	}

	/**
	 * El importe de la averia de referencia es 250.0
	 */
	@Test
	public void testImporteAveria() {
		assertTrue(averia.getImporte() == 250.0);
	}

	/**
	 * Calculo del importe de averia con intervenciones de varios mecanicos
	 */
	@Test
	public void testImporteAveriaConDosIntervenciones() {
		averia.reopen(); // changes from TERMINADA to ABIERTA again
		Mecanico otro = new Mecanico("1", "a", "n");
		averia.assignTo(otro);
		Intervencion i = new Intervencion(otro, averia);
		i.setMinutos(30);

		averia.markAsFinished();

		assertTrue(averia.getImporte() == 275.0);
	}

	/**
	 * Cálculo correcto de importe de avería al quitar intervenciones El
	 * (re)cálculo se hace al pasar la avería a TERMINADA
	 */
	@Test
	public void testImporteAveriaQuitandoIntervencione() {
		averia.reopen();
		Mecanico otro = new Mecanico("1", "a", "n");
		averia.assignTo(otro);
		Intervencion i = new Intervencion(otro, averia);
		i.setMinutos(30);

		Association.Intervenir.unlink(intervencion);
		averia.markAsFinished();

		assertTrue(averia.getImporte() == 25.0);
	}

	/**
	 * No se puede añadir a una factura una averia no terminada y lo indica
	 * lanzando una IllegalStateException
	 * 
	 * @throws IllegalStateException
	 */
	@Test(expected = IllegalStateException.class)
	public void testAveriaNoTerminadaException() {
		averia.reopen();
		List<Averia> averias = new ArrayList<Averia>();
		averias.add(averia);
		new Factura(0L, averias); // debe saltar IllegalStateExcepcion: averia
									// no terminada
	}

	/**
	 * Una factura creada y con averias asignadas está en estado SIN_ABONAR
	 * 
	 * @throws BusinessException
	 */
	@Test
	public void testFacturaCreadaSinAbonar() {
		List<Averia> averias = new ArrayList<Averia>();
		averias.add(averia);
		Factura factura = new Factura(0L, averias);

		assertTrue(factura.getStatus() == FacturaStatus.SIN_ABONAR);
	}

	/**
	 * Una averia no puede ser marcada como facturada si no tiene factura
	 * asignada
	 * 
	 * @throws IllegalStateException
	 */
	@Test(expected = IllegalStateException.class)
	public void testSinFacturaNoMarcarFacturada() {
		averia.markAsInvoiced(); // Lanza excepción "No factura asignada"
	}

	/**
	 * La fecha devuelta por una avería debe ser una copia de la interna
	 */
	@Test
	public void testGetDateReturnsCopy() {
		Date one = averia.getFecha();
		Date other = averia.getFecha();

		assertTrue(one != other);
		assertTrue(one.equals(other));
	}

}
