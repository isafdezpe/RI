package uo.ri.model;

import javax.persistence.*;

@Entity
@Table(name = "TBonos")
public class Bono extends MedioPago {

	@Column(unique = true)
	private String codigo;
	private double disponible = 0.0;
	private String descripcion;

	Bono() {
	}

	public Bono(String codigo, double disponible) {
		this.codigo = codigo;
		this.disponible = disponible;
	}

	public Bono(String codigo, double disponible, String descripcion) {
		this(codigo, disponible);
		this.descripcion = descripcion;
	}

	/**
	 * @return codigo del bono
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @return cantidad disponible
	 */
	public double getDisponible() {
		return disponible - acumulado;
	}

	/**
	 * establece la descripcion del bono
	 * 
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
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
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Bono other = (Bono) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
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
		return "Bono [codigo=" + codigo + ", disponible=" + disponible
				+ ", descripcion=" + descripcion + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uo.ri.model.MedioPago#comprobarValidez(double)
	 */
	@Override
	public void comprobarValidez(double importe) {
		if (importe > getDisponible())
			throw new IllegalStateException(
					"El bono no tiene suficiente dinero disponible");
	}
}
