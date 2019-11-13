package uo.ri.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import uo.ri.model.types.AveriaStatus;

@Entity
@Table(name = "TAverias", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "FECHA", "VEHICULO_ID" }) })
public class Averia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String descripcion;
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	private double importe = 0.0;
	@Enumerated(EnumType.STRING)
	private AveriaStatus status = AveriaStatus.ABIERTA;

	@ManyToOne
	private Vehiculo vehiculo;
	@ManyToOne
	private Mecanico mecanico;
	@ManyToOne
	private Factura factura;
	@OneToMany(mappedBy = "averia")
	private Set<Intervencion> intervenciones = new HashSet<>();

	Averia() {
	}

	public Averia(Vehiculo vehiculo) {
		super();
		this.fecha = new Date();
		Association.Averiar.link(vehiculo, this);
	}

	public Averia(Vehiculo vehiculo, String descripcion) {
		this(vehiculo);
		this.descripcion = descripcion;
	}

	/**
	 * @return id de averia
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece la descripcion de la averia
	 * 
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return descripcion de la averia
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @return copia de la fecha de averia
	 */
	public Date getFecha() {
		return (Date) fecha.clone();
	}

	/**
	 * Establece el importe de la averia
	 * 
	 * @param importe
	 */
	public void setImporte(double importe) {
		this.importe = importe;
	}

	/**
	 * @return importe de la averia
	 */
	public double getImporte() {
		return importe;
	}

	/**
	 * Establece el estado de la averia
	 * 
	 * @param status
	 */
	private void setStatus(AveriaStatus status) {
		this.status = status;
	}

	/**
	 * @return estado de la averia
	 */
	public AveriaStatus getStatus() {
		return status;
	}

	/**
	 * Establece el vehiculo de la averia
	 * 
	 * @param vehiculo
	 */
	void _setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	/**
	 * @return vehiculo de la averia
	 */
	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	/**
	 * Establece el mecánico de la averia
	 * 
	 * @param mecanico
	 */
	void _setMecanico(Mecanico mecanico) {
		this.mecanico = mecanico;
	}

	/**
	 * @return mecanico de la averia
	 */
	public Mecanico getMecanico() {
		return mecanico;
	}

	/**
	 * Establece la factura de la averia
	 * 
	 * @param factura
	 */
	void _setFactura(Factura factura) {
		this.factura = factura;
	}

	/**
	 * @return factura
	 */
	public Factura getFactura() {
		return factura;
	}

	/**
	 * @return las intervenciones de la averia
	 */
	Set<Intervencion> _getIntervenciones() {
		return intervenciones;
	}

	/**
	 * @return copia de las intervenciones
	 */
	public Set<Intervencion> getIntervenciones() {
		return new HashSet<>(intervenciones);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result
				+ ((vehiculo == null) ? 0 : vehiculo.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Averia other = (Averia) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (vehiculo == null) {
			if (other.vehiculo != null)
				return false;
		} else if (!vehiculo.equals(other.vehiculo))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Averia [descripcion=" + descripcion + ", fecha=" + fecha
				+ ", importe=" + importe + ", status=" + status + ", vehiculo="
				+ vehiculo + "]";
	}

	/**
	 * Asigna la averia al mecanico y esta queda marcada como ASIGNADA
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @param mecanico
	 * @throws IllegalStateException si - La avería no está en estado ABIERTA, o
	 *                               - La avería ya está enlazada con otro
	 *                               mecánico
	 */
	public void assignTo(Mecanico mecanico) {
		// Solo se puede asignar una averia que está ABIERTA
		if (!getStatus().equals(AveriaStatus.ABIERTA))
			throw new IllegalStateException("La avería no está abierta");
		if (getMecanico() != null)
			throw new IllegalStateException(
					"La avería ya tiene un mecánico asignado");

		// linkado de averia y mecanico
		Association.Asignar.link(mecanico, this);
		// la averia pasa a ASIGNADA
		setStatus(AveriaStatus.ASIGNADA);
	}

	/**
	 * El mecánico da por finalizada esta avería, entonces se calcula el importe
	 * y pasa a TERMINADA
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si - La avería no está en estado ASIGNADA,
	 *                               o - La avería no está enlazada con un
	 *                               mecánico
	 */
	public void markAsFinished() {
		if (!getStatus().equals(AveriaStatus.ASIGNADA))
			throw new IllegalStateException("La avería no está asignada");
		if (getMecanico() == null)
			throw new IllegalStateException(
					"La avería no tiene un mecánico asignado");

		this.importe = 0;
		for (Intervencion i : getIntervenciones())
			this.importe += i.getImporte();

		setStatus(AveriaStatus.TERMINADA);
	}

	/**
	 * Una averia en estado TERMINADA se puede asignar a otro mecánico (p.e., el
	 * primero no ha hecho bien la reparación), pero debe ser pasada a ABIERTA
	 * primero
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si - La avería no está en estado TERMINADA
	 */
	public void reopen() {
		// Se verifica que está en estado TERMINADA
		if (!getStatus().equals(AveriaStatus.TERMINADA))
			throw new IllegalStateException("La avería no está terminada");

		// Se pasa la averia a ABIERTA
		setStatus(AveriaStatus.ABIERTA);
		Association.Asignar.unlink(getMecanico(), this);
	}

	/**
	 * Edte método se llama desde la factura al ejecutar factura.removeAveria()
	 * Retrocede la averia a TERMINADA
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si - La averia no está en estado FACTURADA,
	 *                               o - La avería aún está enlazada con la
	 *                               factura
	 */
	public void markBackToFinished() {
		if (!getStatus().equals(AveriaStatus.FACTURADA))
			throw new IllegalStateException("La avería no está facturada");
		if (getFactura() != null)
			throw new IllegalStateException(
					"La avería tiene una factura asignada");

		setStatus(AveriaStatus.TERMINADA);
	}

	/**
	 * Edte método se llama desde la factura al ejecutar factura.addAveria()
	 * Marca la averia como FACTURADA
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si - La averia no está en estado TERMINADA,
	 *                               o - La avería no está enlazada con una
	 *                               factura
	 */
	public void markAsInvoiced() {
		if (!getStatus().equals(AveriaStatus.TERMINADA))
			throw new IllegalStateException("La avería no está terminada");
		if (getFactura() == null)
			throw new IllegalStateException(
					"La avería tiene una factura asignada");

		setStatus(AveriaStatus.FACTURADA);
	}

	/**
	 * Desvincula la avería en estado ASIGNADA del mecánico y la pasa a ABIERTA
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si - La averia no está en estado ASIGNADA,
	 *                               o
	 */
	public void desassign() {
		if (!getStatus().equals(AveriaStatus.ASIGNADA))
			throw new IllegalStateException("La avería no está asignada");

		Association.Asignar.unlink(getMecanico(), this);
		setStatus(AveriaStatus.ABIERTA);
	}

}
