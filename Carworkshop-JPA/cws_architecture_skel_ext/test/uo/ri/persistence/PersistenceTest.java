package uo.ri.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import alb.util.date.Dates;
import uo.ri.model.Association;
import uo.ri.model.Averia;
import uo.ri.model.Bono;
import uo.ri.model.Cargo;
import uo.ri.model.Cliente;
import uo.ri.model.Factura;
import uo.ri.model.Intervencion;
import uo.ri.model.Mecanico;
import uo.ri.model.MedioPago;
import uo.ri.model.Metalico;
import uo.ri.model.Repuesto;
import uo.ri.model.Sustitucion;
import uo.ri.model.TarjetaCredito;
import uo.ri.model.TipoVehiculo;
import uo.ri.model.Vehiculo;
import uo.ri.model.types.Address;

public class PersistenceTest {

	private EntityManagerFactory factory;
	private Cliente cliente;
	private Sustitucion sustitucion;
	private Cargo cargo;

	@Before
	public void setUp() {
		factory = Persistence.createEntityManagerFactory("carworkshop");
		List<Object> graph = createGraph();
		persistGraph(graph);
	}

	@After
	public void tearDown() {
		removeGraph();
		factory.close();
	}

	@Test
	public void testCliente() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		Cliente cl = mapper.merge(cliente);

		assertNotNull(cl.getId());
		assertEquals(cl.getApellidos(), "apellidos");
		assertEquals(cl.getNombre(), "nombre");
		assertEquals(cl.getDni(), "dni");

		trx.commit();
		mapper.close();
	}

	@Test
	public void testVehiculos() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		Cliente cl = mapper.merge(cliente);
		Set<Vehiculo> vehiculos = cl.getVehiculos();
		Vehiculo v = vehiculos.iterator().next();

		assertTrue(vehiculos.size() == 1);
		assertSame(v.getCliente(), cl);
		assertNotNull(v.getId());
		assertEquals(v.getMarca(), "seat");
		assertEquals(v.getModelo(), "ibiza");
		assertEquals(v.getMatricula(), "1234 GJI");

		trx.commit();
		mapper.close();
	}

	@Test
	public void testSustituir() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		Sustitucion s = mapper.merge(sustitucion);
		Repuesto r = s.getRepuesto();
		Intervencion i = s.getIntervencion();

		assertTrue(r.getSustituciones().contains(s));
		assertTrue(i.getSustituciones().contains(s));

		trx.commit();
		mapper.close();
	}

	@Test
	public void testTrabajarArreglar() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		Sustitucion s = mapper.merge(sustitucion);
		Intervencion i = s.getIntervencion();
		Mecanico m = i.getMecanico();
		Averia a = i.getAveria();

		assertTrue(m.getIntervenciones().contains(i));
		assertTrue(a.getIntervenciones().contains(i));

		trx.commit();
		mapper.close();
	}

	@Test
	public void testTener() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		Sustitucion s = mapper.merge(sustitucion);
		Averia a = s.getIntervencion().getAveria();
		Vehiculo v = a.getVehiculo();

		assertTrue(v.getAverias().contains(a));

		trx.commit();
		mapper.close();
	}

	@Test
	public void testSerPoseer() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		Sustitucion s = mapper.merge(sustitucion);
		Vehiculo v = s.getIntervencion().getAveria().getVehiculo();
		TipoVehiculo tv = v.getTipo();
		Cliente c = v.getCliente();

		assertTrue(tv.getVehiculos().contains(v));
		assertTrue(c.getVehiculos().contains(v));

		trx.commit();
		mapper.close();
	}

	@Test
	public void testCargar() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		Cargo c = mapper.merge(cargo);
		Factura f = c.getFactura();
		MedioPago mp = c.getMedioPago();

		assertTrue(mp.getCargos().contains(c));
		assertTrue(f.getCargos().contains(c));

		trx.commit();
		mapper.close();
	}

	@Test
	public void testFacturar() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		Sustitucion s = mapper.merge(sustitucion);
		Averia a = s.getIntervencion().getAveria();
		Factura f = a.getFactura();

		assertTrue(f.getAverias().contains(a));

		trx.commit();
		mapper.close();
	}

	@Test
	public void testPagar() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		Sustitucion s = mapper.merge(sustitucion);
		Cliente c = s.getIntervencion().getAveria().getVehiculo().getCliente();
		Set<MedioPago> medios = c.getMediosPago();

		for (MedioPago mp : medios) {
			assertSame(mp.getCliente(), c);
		}

		trx.commit();
		mapper.close();
	}

	protected List<Object> createGraph() {

		cliente = new Cliente("dni", "nombre", "apellidos");
		Address address = new Address("street", "city", "zipcode");
		cliente.setAddress(address);
		Vehiculo vehiculo = new Vehiculo("1234 GJI", "seat", "ibiza");
		Association.Poseer.link(cliente, vehiculo);

		TipoVehiculo tipoVehiculo = new TipoVehiculo("coche", 50.0);
		Association.Clasificar.link(tipoVehiculo, vehiculo);

		Averia averia = new Averia(vehiculo, "falla la junta la trocla");
		Mecanico mecanico = new Mecanico("dni-mecanico", "nombre", "apellidos");
		averia.assignTo(mecanico);

		Intervencion intervencion = new Intervencion(mecanico, averia);
		intervencion.setMinutos(60);
		averia.markAsFinished();

		Repuesto repuesto = new Repuesto("R1001", "junta la trocla", 100.0);
		sustitucion = new Sustitucion(repuesto, intervencion, 2);

		Bono bono = new Bono("B-100", 100.0);
		bono.setDescripcion("Voucher just for testing");
		Association.Pagar.link(bono, cliente);

		TarjetaCredito tarjetaCredito = new TarjetaCredito("1234567", "visa",
				Dates.inYearsTime(1));
		Association.Pagar.link(tarjetaCredito, cliente);

		Metalico metalico = new Metalico(cliente);

		Factura factura = new Factura(1L);
		factura.setFecha(Dates.today());
		factura.addAveria(averia);

		cargo = new Cargo(factura, tarjetaCredito, factura.getImporte());

		List<Object> res = new LinkedList<Object>();

		res.add(tipoVehiculo);
		res.add(repuesto);
		res.add(mecanico);
		res.add(cliente);
		res.add(bono);
		res.add(tarjetaCredito);
		res.add(metalico);
		res.add(vehiculo);
		res.add(factura);
		res.add(averia);
		res.add(intervencion);
		res.add(sustitucion);
		res.add(cargo);

		return res;
	}

	private void persistGraph(List<Object> graph) {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		for (Object o : graph) {
			mapper.persist(o);
		}

		trx.commit();
		mapper.close();
	}

	private void removeGraph() {
		EntityManager mapper = factory.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		List<Object> merged = mergeGraph(mapper);

		for (Object o : merged) {
			mapper.remove(o);
		}

		trx.commit();
		mapper.close();
	}

	private List<Object> mergeGraph(EntityManager mapper) {
		List<Object> res = new LinkedList<Object>();

		res.add(mapper.merge(cargo));

		Sustitucion s = mapper.merge(sustitucion);
		res.add(s);
		res.add(s.getRepuesto());
		res.add(s.getIntervencion());
		res.add(s.getIntervencion().getMecanico());
		res.add(s.getIntervencion().getAveria());
		res.add(s.getIntervencion().getAveria().getVehiculo());
		res.add(s.getIntervencion().getAveria().getVehiculo().getTipo());
		res.add(s.getIntervencion().getAveria().getFactura());

		Cliente cl = mapper.merge(cliente);
		for (MedioPago mp : cl.getMediosPago()) {
			res.add(mp);
		}
		res.add(cl);

		return res;
	}

}
