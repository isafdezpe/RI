package uo.ri.model;

import javax.persistence.*;

import uo.ri.model.types.FacturaStatus;

@Entity
@Table(name = "TCargos", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "FACTURA_ID", "MEDIOPAGO_ID" }) })
public class Cargo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Factura factura;
	@ManyToOne
	private MedioPago medioPago;
	private double importe = 0.0;

	Cargo() {
	}

	public Cargo(Factura factura, MedioPago medioPago, double importe) {
		// incrementar el importe en el acumulado del medio de pago
		medioPago.pagar(importe);
		// guardar el importe
		this.importe = importe;
		// enlazar (link) factura, este cargo y medioDePago
		Association.Cargar.link(medioPago, this, factura);
	}

	/**
	 * @return id del cargo
	 */
	public Long getId() {
		return id;
	}

	/**
	 * establece la factura del cargo
	 * 
	 * @param factura
	 */
	void _setFactura(Factura factura) {
		this.factura = factura;
	}

	/**
	 * @return factura
	 */
	public Factura getFactura() {
		return factura;
	}

	/**
	 * establece el medio de pago utilizado en el cargo
	 * 
	 * @param medioPago
	 */
	void _setMedioPago(MedioPago medioPago) {
		this.medioPago = medioPago;
	}

	/**
	 * @return medio de pago
	 */
	public MedioPago getMedioPago() {
		return medioPago;
	}

	/**
	 * @return importe del cargo
	 */
	public double getImporte() {
		return importe;
	}

	/**
	 * Anula (retrocede) este cargo de la factura y el medio de pago Solo se
	 * puede hacer si la factura no está abonada Decrementar el acumulado del
	 * medio de pago Desenlazar el cargo de la factura y el medio de pago
	 * 
	 * @throws IllegalStateException if the invoice is already settled
	 */
	public void rewind() {
		// verificar que la factura no esta ABONADA
		if (getFactura().getStatus().equals(FacturaStatus.ABONADA))
			throw new IllegalStateException("La factura ya está abonada");

		// decrementar acumulado en medio de pago
		medioPago.pagar(-importe);
		// desenlazar factura, cargo y medio de pago
		Association.Cargar.unlink(this);
	}

}
