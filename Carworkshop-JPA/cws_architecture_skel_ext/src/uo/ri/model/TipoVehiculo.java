package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "TTiposVehiculo")
public class TipoVehiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;
	private double precioHora;

	@OneToMany(mappedBy = "tipoVehiculo")
	private Set<Vehiculo> vehiculos = new HashSet<>();

	TipoVehiculo() {
	}

	public TipoVehiculo(String nombre) {
		super();
		this.nombre = nombre;
	}

	public TipoVehiculo(String nombre, double preciohora) {
		this(nombre);
		this.precioHora = preciohora;
	}

	/**
	 * @return id del tipo de vehiculo
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return nombre del tipo de vehiculo
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return precio por hora de una intervencion para este tipo
	 */
	public double getPrecioHora() {
		return precioHora;
	}

	/**
	 * @return vehiculos pertenecientes a este tipo
	 */
	Set<Vehiculo> _getVehiculos() {
		return vehiculos;
	}

	/**
	 * @return copia de los vehiculos
	 */
	public Set<Vehiculo> getVehiculos() {
		return new HashSet<>(vehiculos);
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
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		TipoVehiculo other = (TipoVehiculo) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
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
		return "TipoVehiculo [nombre=" + nombre + ", precioHora=" + precioHora
				+ "]";
	}
}
