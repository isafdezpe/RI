package uo.ri.model;

import java.util.Date;

import javax.persistence.*;

import alb.util.date.Dates;

@Entity
@Table(name = "TTarjetasCredito")
public class TarjetaCredito extends MedioPago {

	@Column(unique = true)
	protected String numero;
	protected String tipo;
	@Temporal(TemporalType.DATE)
	protected Date validez;

	TarjetaCredito() {
	}

	public TarjetaCredito(String numero, String tipo, Date validez) {
		this.numero = numero;
		this.tipo = tipo;
		this.validez = validez;
	}

	/**
	 * @return numero de la tarjeta
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @return tipo de tarjeta
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @return validez de la tarjeta
	 */
	public Date getValidez() {
		return validez;
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
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		TarjetaCredito other = (TarjetaCredito) obj;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
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
		return "TarjetaCredito [numero=" + numero + ", tipo=" + tipo
				+ ", validez=" + validez + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uo.ri.model.MedioPago#comprobarValidez(double)
	 */
	@Override
	public void comprobarValidez(double importe) {
		if (getValidez().before(Dates.now()))
			throw new IllegalStateException(
					"La validez de la tarjeta ha expirado.");
	}

}
