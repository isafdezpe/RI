package uo.ri.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import alb.util.date.Dates;
import uo.ri.model.types.AveriaStatus;
import uo.ri.model.types.FacturaStatus;

@Entity
@Table(name = "TFacturas")
public class Factura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private Long numero;
	@Temporal(TemporalType.DATE)
	private Date fecha;
	private double importe;
	private double iva;
	private FacturaStatus status = FacturaStatus.SIN_ABONAR;

	@OneToMany(mappedBy = "factura")
	private Set<Averia> averias = new HashSet<>();
	@OneToMany(mappedBy = "factura")
	private Set<Cargo> cargos = new HashSet<>();

	Factura() {
	}

	public Factura(Long numero) {
		super();
		this.numero = numero;
		this.fecha = Dates.now();
	}

	public Factura(long numero, Date fecha) {
		this(numero);
		this.fecha = (Date) fecha.clone();
	}

	public Factura(long numero, List<Averia> averias) {
		this(numero);
		for (Averia a : averias)
			addAveria(a);
	}

	public Factura(long numero, Date date, List<Averia> averias) {
		this(numero, date);
		for (Averia a : averias)
			addAveria(a);
	}

	/**
	 * @return id de factura
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return numero de factura
	 */
	public Long getNumero() {
		return numero;
	}

	/**
	 * Establece la fecha de la factura
	 * 
	 * @param now
	 */
	public void setFecha(Date now) {
		this.fecha = (Date) fecha.clone();
	}

	/**
	 * @return copia de la fecha de factura
	 */
	public Date getFecha() {
		return (Date) fecha.clone();
	}

	/**
	 * @return importe de la factura
	 */
	public double getImporte() {
		return importe;
	}

	/**
	 * @return iva de la factura
	 */
	public double getIva() {
		return iva;
	}

	/**
	 * @return estado de la factura
	 */
	public FacturaStatus getStatus() {
		return status;
	}

	/**
	 * @return averias asignadas a la factura
	 */
	Set<Averia> _getAsignadas() {
		return averias;
	}

	/**
	 * @return copia de las averias
	 */
	public Set<Averia> getAverias() {
		return new HashSet<>(averias);
	}

	/**
	 * @return cargos de la factura
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
		Factura other = (Factura) obj;
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
		return "Factura [numero=" + numero + ", fecha=" + fecha + ", importe="
				+ importe + ", iva=" + iva + ", status=" + status + "]";
	}

	/**
	 * Añade la averia a la factura y actualiza el importe e iva de la factura
	 * 
	 * @param averia
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si la factura no está en estado SIN_ABONAR
	 */
	public void addAveria(Averia averia) {
		// Verificar que la factura está en estado SIN_ABONAR
		if (!status.equals(FacturaStatus.SIN_ABONAR))
			throw new IllegalStateException("La factura está abonada");

		// Verificar que La averia está TERMINADA
		if (!averia.getStatus().equals(AveriaStatus.TERMINADA))
			throw new IllegalStateException("La avería no está terminada");

		// linkar factura y averia
		Association.Facturar.link(this, averia);
		// marcar la averia como FACTURADA
		averia.markAsInvoiced();
		// calcular el importe
		calcularImporte();
	}

	/**
	 * Calcula el importe de la avería y su IVA, teniendo en cuenta la fecha de
	 * factura
	 */
	void calcularImporte() {
		Date d = Dates.fromString("1/7/2012");
		if (getFecha().before(d))
			this.iva = 0.18;
		else
			this.iva = 0.21;

		double i = 0;
		for (Averia a : getAverias())
			i += a.getImporte();

		this.importe = Math.round(i * (1 + iva) * 100) / 100d;
	}

	/**
	 * Elimina una averia de la factura, solo si está SIN_ABONAR y recalcula el
	 * importe
	 * 
	 * @param averia
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException si la factura no está en estado SIN_ABONAR
	 */
	public void removeAveria(Averia averia) {
		// verificar que la factura está sin abonar
		if (!getStatus().equals(FacturaStatus.SIN_ABONAR))
			throw new IllegalStateException("La factura está abonada");

		// desenlazar factura y averia
		Association.Facturar.unlink(this, averia);
		// retornar la averia al estado FINALIZADA
		averia.markBackToFinished();
		// volver a calcular el importe
		calcularImporte();
	}

	/**
	 * Marks the invoice as ABONADA, but
	 * 
	 * @see Diagramas de estados en el enunciado de referencia
	 * @throws IllegalStateException if - Is already settled, or - the amounts
	 *                               paid with charges to payment means does not
	 *                               cover the total of the invoice
	 */
	public void settle() {
		if (getStatus().equals(FacturaStatus.ABONADA))
			throw new IllegalStateException("La factura ya está abonada");

		double pagado = 0;
		for (Cargo c : getCargos())
			pagado += c.getImporte();
		if (pagado < getImporte())
			throw new IllegalStateException(
					"El importe abonado no cubre el total de la factura");

		this.status = FacturaStatus.ABONADA;
	}

}
