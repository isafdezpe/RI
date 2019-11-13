package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import uo.ri.model.types.ContractStatus;

@Entity
@Table(name = "TMecanicos")
public class Mecanico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String dni;
	private String apellidos;
	private String nombre;

	@OneToMany(mappedBy = "mecanico")
	private Set<Averia> asignadas = new HashSet<>();
	@OneToMany(mappedBy = "mecanico")
	private Set<Intervencion> intervenciones = new HashSet<>();
	@OneToMany(mappedBy = "mecanico")
	private Set<Contract> contratos = new HashSet<>();

	Mecanico() {
	}

	public Mecanico(String dni) {
		super();
		this.dni = dni;
	}

	public Mecanico(String dni, String nombre, String apellidos) {
		this(dni);
		this.nombre = nombre;
		this.apellidos = apellidos;
	}

	/**
	 * @return id del mecanico
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return dni del mecanico
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * Establece los apellidos del mecanico
	 * 
	 * @param apellidos
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * @return apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Establece el nombre del mecánico
	 * 
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return averias asignadas al mecanico
	 */
	Set<Averia> _getAsignadas() {
		return asignadas;
	}

	/**
	 * @return copia de las averias
	 */
	public Set<Averia> getAsignadas() {
		return new HashSet<>(asignadas);
	}

	/**
	 * @return intervenciones realizadas por el mecanico
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

	/**
	 * @return contratos del mecanico
	 */
	Set<Contract> _getContracts() {
		return contratos;
	}

	/**
	 * @return copia de los contratos
	 */
	public Set<Contract> getContracts() {
		return new HashSet<>(contratos);
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
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
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
		Mecanico other = (Mecanico) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
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
		return "Mecanico [dni=" + dni + ", apellidos=" + apellidos + ", nombre="
				+ nombre + "]";
	}

	/**
	 * @return contrato activo del mecanico
	 */
	public Contract getActiveContract() {
		for (Contract c : contratos)
			if (c.getStatus().equals(ContractStatus.ACTIVO))
				return c;
		return null;
	}
}
