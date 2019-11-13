package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "TVehiculos")
public class Vehiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String marca;
	@Column(unique = true)
	private String matricula;
	private String modelo;

	@Column(name = "NUM_AVERIAS")
	private int numAverias = 0;

	@ManyToOne
	private Cliente cliente;
	@ManyToOne
	@JoinColumn(name = "TIPO_ID")
	private TipoVehiculo tipoVehiculo;
	@OneToMany(mappedBy = "vehiculo")
	private Set<Averia> averias = new HashSet<>();

	Vehiculo() {
	}

	public Vehiculo(String matricula) {
		super();
		this.matricula = matricula;
	}

	public Vehiculo(String matricula, String marca, String modelo) {
		this(matricula);
		this.marca = marca;
		this.modelo = modelo;
	}

	/**
	 * @return id del vehiculo
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return marca del vehiculo
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * @return matricula
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 * @return modelo del vehiculo
	 */
	public String getModelo() {
		return modelo;
	}

	/**
	 * @return numero de averias del vehiculo
	 */
	public int getNumAverias() {
		return averias.size();
	}

	/**
	 * Establece el cliente al que pertenece el vehiculo
	 * 
	 * @param cliente
	 */
	void _setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return cliente dueño del vehiculo
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * Establece el tipo de vehiculo que es
	 * 
	 * @param tipo
	 */
	void _setTipo(TipoVehiculo tipo) {
		this.tipoVehiculo = tipo;
	}

	/**
	 * @return tipo de vehiculo
	 */
	public TipoVehiculo getTipo() {
		return tipoVehiculo;
	}

	/**
	 * @return averias del vehiculo
	 */
	Set<Averia> _getAverias() {
		return averias;
	}

	/**
	 * @return copia de las averias
	 */
	public Set<Averia> getAverias() {
		return new HashSet<>(averias);
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
				+ ((matricula == null) ? 0 : matricula.hashCode());
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
		Vehiculo other = (Vehiculo) obj;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
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
		return "Vehiculo [marca=" + marca + ", matricula=" + matricula
				+ ", modelo=" + modelo + ", numAverias=" + numAverias + "]";
	}
}
