package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "TIntervenciones", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "AVERIA_ID", "MECANICO_ID" }) })
public class Intervencion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Averia averia;
	@ManyToOne
	private Mecanico mecanico;
	private int minutos;

	@OneToMany(mappedBy = "intervencion")
	private Set<Sustitucion> sustituciones = new HashSet<>();

	Intervencion() {
	}

	public Intervencion(Mecanico mecanico, Averia averia) {
		super();
		Association.Intervenir.link(averia, this, mecanico);
	}

	/**
	 * @return id de la intervencion
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece la averia de la intervencion
	 * 
	 * @param averia
	 */
	void _setAveria(Averia averia) {
		this.averia = averia;
	}

	/**
	 * @return averia
	 */
	public Averia getAveria() {
		return averia;
	}

	/**
	 * Establece el mecanico que realiza la intervencion
	 * 
	 * @param mecanico
	 */
	void _setMecanico(Mecanico mecanico) {
		this.mecanico = mecanico;
	}

	/**
	 * @return mecanico
	 */
	public Mecanico getMecanico() {
		return mecanico;
	}

	/**
	 * Establece la duracion de la intervencion en minutos
	 * 
	 * @param minutos
	 */
	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}

	/**
	 * @return minutos de duracion
	 */
	public int getMinutos() {
		return minutos;
	}

	/**
	 * @return sustituciones realizadas en la intervencion
	 */
	Set<Sustitucion> _getSustituciones() {
		return sustituciones;
	}

	/**
	 * @return copia de las sustituciones
	 */
	public Set<Sustitucion> getSustituciones() {
		return new HashSet<>(sustituciones);
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
		result = prime * result + ((averia == null) ? 0 : averia.hashCode());
		result = prime * result
				+ ((mecanico == null) ? 0 : mecanico.hashCode());
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
		Intervencion other = (Intervencion) obj;
		if (averia == null) {
			if (other.averia != null)
				return false;
		} else if (!averia.equals(other.averia))
			return false;
		if (mecanico == null) {
			if (other.mecanico != null)
				return false;
		} else if (!mecanico.equals(other.mecanico))
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
		return "Intervencion [averia=" + averia + ", mecanico=" + mecanico
				+ ", minutos=" + minutos + "]";
	}

	/**
	 * @return importe de la intervencion
	 */
	public double getImporte() {
		double precioHora = getAveria().getVehiculo().getTipo().getPrecioHora();
		double manoObra = precioHora * getMinutos() / 60;

		double precioRepuestos = 0;
		for (Sustitucion s : getSustituciones())
			precioRepuestos += s.getImporte();

		return manoObra + precioRepuestos;
	}

}
