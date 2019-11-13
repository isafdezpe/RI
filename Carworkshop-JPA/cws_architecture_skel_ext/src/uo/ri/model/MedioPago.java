package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "TMediosPago")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MedioPago {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	protected double acumulado = 0.0;

	@ManyToOne
	protected Cliente cliente;
	@OneToMany(mappedBy = "medioPago")
	private Set<Cargo> cargos = new HashSet<>();

	public void pagar(double importe) {
		comprobarValidez(importe);
		this.acumulado += importe;
	}

	/**
	 * @return id del medio de pago
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el cliente asociado al medio de pago
	 * 
	 * @param cliente
	 */
	void _setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return cliente asociado al medio de pago
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * @return cargos asociados al medio de pago
	 */
	Set<Cargo> _getCargos() {
		return cargos;
	}

	/**
	 * @return copia de los cargos
	 */
	public Set<Cargo> getCargos() {
		return new HashSet<>(cargos);
	}

	/**
	 * @return total abonado con el medio de pago
	 */
	public double getAcumulado() {
		return acumulado;
	}

	/**
	 * Comprueba que el medio de pago utilizado sea válido (que la tarjeta no
	 * esté caducada, que el importe disponible en el bono sea superior a lo que
	 * se debe abonar)
	 * 
	 * @param importe
	 */
	public abstract void comprobarValidez(double importe);
}
