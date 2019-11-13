package uo.ri.model;

import javax.persistence.*;

@Entity
@Table(name = "TSustituciones", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "REPUESTO_ID", "INTERVENCION_ID" }) })
public class Sustitucion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Repuesto repuesto;
	@ManyToOne
	private Intervencion intervencion;
	private int cantidad;

	Sustitucion() {
	}

	public Sustitucion(Repuesto repuesto, Intervencion intervencion) {
		super();
		Association.Sustituir.link(repuesto, this, intervencion);
	}

	public Sustitucion(Repuesto repuesto, Intervencion intervencion,
			int cantidad) {
		this(repuesto, intervencion);
		if (cantidad < 1)
			throw new IllegalArgumentException(
					"La sustitución debe tener al menos un repuesto");
		this.cantidad = cantidad;
	}

	/**
	 * @return id de la sustitucion
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el repuesto utilizado en la sustitucion
	 * 
	 * @param repuesto
	 */
	void _setRepuesto(Repuesto repuesto) {
		this.repuesto = repuesto;
	}

	/**
	 * @return repuesto
	 */
	public Repuesto getRepuesto() {
		return repuesto;
	}

	/**
	 * Establece la intervencion en la que se ha realizado la sustitucion
	 * 
	 * @param intervencion
	 */
	void _setIntervencion(Intervencion intervencion) {
		this.intervencion = intervencion;
	}

	/**
	 * @return intervencion
	 */
	public Intervencion getIntervencion() {
		return intervencion;
	}

	/**
	 * @return cantidad de los repuestos utilizados
	 */
	public int getCantidad() {
		return cantidad;
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
		result = prime * result
				+ ((intervencion == null) ? 0 : intervencion.hashCode());
		result = prime * result
				+ ((repuesto == null) ? 0 : repuesto.hashCode());
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
		Sustitucion other = (Sustitucion) obj;
		if (intervencion == null) {
			if (other.intervencion != null)
				return false;
		} else if (!intervencion.equals(other.intervencion))
			return false;
		if (repuesto == null) {
			if (other.repuesto != null)
				return false;
		} else if (!repuesto.equals(other.repuesto))
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
		return "Sustitucion [repuesto=" + repuesto + ", intervencion="
				+ intervencion + ", cantidad=" + cantidad + "]";
	}

	/**
	 * @return importe de la sustitucion
	 */
	public double getImporte() {
		return getCantidad() * getRepuesto().getPrecio();
	}
}
