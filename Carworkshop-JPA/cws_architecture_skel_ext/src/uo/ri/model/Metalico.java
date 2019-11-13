package uo.ri.model;

import javax.persistence.*;

@Entity
@Table(name = "TMetalicos")
public class Metalico extends MedioPago {

	Metalico() {
	}

	public Metalico(Cliente cliente) {
		Association.Pagar.link(this, cliente);
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
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
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
		MedioPago other = (MedioPago) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see uo.ri.model.MedioPago#comprobarValidez(double)
	 */
	@Override
	public void comprobarValidez(double importe) {

	}
}
