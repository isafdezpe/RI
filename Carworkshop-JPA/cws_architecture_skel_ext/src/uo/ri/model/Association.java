package uo.ri.model;

public class Association {

	public static class Poseer {

		/**
		 * Asocia un vehiculo a un cliente
		 * 
		 * @param cliente
		 * @param vehiculo
		 */
		public static void link(Cliente cliente, Vehiculo vehiculo) {
			vehiculo._setCliente(cliente);
			cliente._getVehiculos().add(vehiculo);
		}

		/**
		 * Desasocia un vehiculo del cliente
		 * 
		 * @param cliente
		 * @param vehiculo
		 */
		public static void unlink(Cliente cliente, Vehiculo vehiculo) {
			cliente._getVehiculos().remove(vehiculo);
			vehiculo._setCliente(null);
		}
	}

	public static class Clasificar {

		/**
		 * Asocia un tipo de vehiculo a un vehiculo
		 * 
		 * @param tipoVehiculo
		 * @param vehiculo
		 */
		public static void link(TipoVehiculo tipoVehiculo, Vehiculo vehiculo) {
			vehiculo._setTipo(tipoVehiculo);
			tipoVehiculo._getVehiculos().add(vehiculo);
		}

		/**
		 * Desasocia un tipo de vehiculo del vehiculo
		 * 
		 * @param tipoVehiculo
		 * @param vehiculo
		 */
		public static void unlink(TipoVehiculo tipoVehiculo,
				Vehiculo vehiculo) {
			tipoVehiculo._getVehiculos().remove(vehiculo);
			vehiculo._setTipo(null);
		}
	}

	public static class Pagar {

		/**
		 * Asocia un medio de pago a un cliente
		 * 
		 * @param medio
		 * @param cliente
		 */
		public static void link(MedioPago medio, Cliente cliente) {
			medio._setCliente(cliente);
			cliente._getMediosPago().add(medio);
		}

		/**
		 * Desasocia un medio de pago del cliente
		 * 
		 * @param medio
		 * @param cliente
		 */
		public static void unlink(Cliente cliente, MedioPago medio) {
			cliente._getMediosPago().remove(medio);
			medio._setCliente(null);
		}
	}

	public static class Averiar {

		/**
		 * Asocia una averia a un vehiculo
		 * 
		 * @param vehiculo
		 * @param averia
		 */
		public static void link(Vehiculo vehiculo, Averia averia) {
			averia._setVehiculo(vehiculo);
			vehiculo._getAverias().add(averia);
		}

		/**
		 * Desasocia una averia del vehiculo
		 * 
		 * @param vehiculo
		 * @param averia
		 */
		public static void unlink(Vehiculo vehiculo, Averia averia) {
			vehiculo._getAverias().remove(averia);
			averia._setVehiculo(null);
		}
	}

	public static class Facturar {

		/**
		 * Asocia una averia a una factura
		 * 
		 * @param factura
		 * @param averia
		 */
		public static void link(Factura factura, Averia averia) {
			averia._setFactura(factura);
			factura._getAsignadas().add(averia);
		}

		/**
		 * Desasocia una averia de la factura
		 * 
		 * @param factura
		 * @param averia
		 */
		public static void unlink(Factura factura, Averia averia) {
			factura._getAsignadas().remove(averia);
			averia._setFactura(null);
		}
	}

	public static class Cargar {

		/**
		 * Asocia una medio de pago y una factura a un cargo
		 * 
		 * @param medio
		 * @param cargo
		 * @param factura
		 */
		public static void link(MedioPago medio, Cargo cargo, Factura factura) {
			cargo._setFactura(factura);
			cargo._setMedioPago(medio);
			factura._getCargos().add(cargo);
			medio._getCargos().add(cargo);
		}

		/**
		 * Desasocia el medio de pago y la factura de un cargo
		 * 
		 * @param medio
		 * @param cargo
		 * @param factura
		 */
		public static void unlink(Cargo cargo) {
			Factura factura = cargo.getFactura();
			MedioPago medio = cargo.getMedioPago();
			factura._getCargos().remove(cargo);
			medio._getCargos().remove(cargo);
			cargo._setFactura(null);
			cargo._setMedioPago(null);
		}
	}

	public static class Asignar {

		/**
		 * Asocia una averia a un mecanico
		 * 
		 * @param mecanico
		 * @param averia
		 */
		public static void link(Mecanico mecanico, Averia averia) {
			averia._setMecanico(mecanico);
			mecanico._getAsignadas().add(averia);
		}

		/**
		 * Desasocia una averia de un mecanico
		 * 
		 * @param mecanico
		 * @param averia
		 */
		public static void unlink(Mecanico mecanico, Averia averia) {
			mecanico._getAsignadas().remove(averia);
			averia._setMecanico(null);
		}
	}

	public static class Intervenir {

		/**
		 * Asocia un mecanico y una averia a una intervencion
		 * 
		 * @param averia
		 * @param intervencion
		 * @param mecanico
		 */
		public static void link(Averia averia, Intervencion intervencion,
				Mecanico mecanico) {
			intervencion._setMecanico(mecanico);
			intervencion._setAveria(averia);
			averia._getIntervenciones().add(intervencion);
			mecanico._getIntervenciones().add(intervencion);
		}

		/**
		 * Desasocia el mecanico y la averia de una intervencion
		 * 
		 * @param averia
		 * @param intervencion
		 * @param mecanico
		 */
		public static void unlink(Intervencion intervencion) {
			Averia averia = intervencion.getAveria();
			Mecanico mecanico = intervencion.getMecanico();
			averia._getIntervenciones().remove(intervencion);
			mecanico._getIntervenciones().remove(intervencion);
			intervencion._setMecanico(null);
			intervencion._setAveria(null);
		}
	}

	public static class Sustituir {

		/**
		 * Asocia un repuesto y una intervencion a una sustitucion
		 * 
		 * @param repuesto
		 * @param sustitucion
		 * @param intervencion
		 */
		public static void link(Repuesto repuesto, Sustitucion sustitucion,
				Intervencion intervencion) {
			sustitucion._setRepuesto(repuesto);
			sustitucion._setIntervencion(intervencion);
			repuesto._getSustituciones().add(sustitucion);
			intervencion._getSustituciones().add(sustitucion);
		}

		/**
		 * Desasocia el repuesto y la intervencion de una sustitucion
		 * 
		 * @param repuesto
		 * @param sustitucion
		 * @param intervencion
		 */
		public static void unlink(Sustitucion sustitucion) {
			Repuesto repuesto = sustitucion.getRepuesto();
			Intervencion intervencion = sustitucion.getIntervencion();
			repuesto._getSustituciones().remove(sustitucion);
			intervencion._getSustituciones().remove(sustitucion);
			sustitucion._setRepuesto(null);
			sustitucion._setIntervencion(null);
		}
	}

	public static class Vincular {

		/**
		 * Asocia un contrato a un mecanico
		 * 
		 * @param mecanico
		 * @param contrato
		 */
		public static void link(Mecanico mecanico, Contract contrato) {
			contrato._setMechanic(mecanico);
			mecanico._getContracts().add(contrato);
		}

		/**
		 * Desasocia un contrato del mecanico
		 * 
		 * @param mecanico
		 * @param contrato
		 */
		public static void unlink(Mecanico mecanico, Contract contrato) {
			mecanico._getContracts().remove(contrato);
			contrato._setMechanic(null);
		}
	}

	public static class Categorize {

		/**
		 * Asocia una categoria de contrato a un contrato
		 * 
		 * @param contrato
		 * @param categoria
		 */
		public static void link(Contract contrato, ContractCategory categoria) {
			contrato._setContractCategory(categoria);
			categoria._getContracts().add(contrato);
		}

		/**
		 * Desasocia la categoria de contrato de un contrato
		 * 
		 * @param contrato
		 * @param categoria
		 */
		public static void unlink(Contract contrato,
				ContractCategory categoria) {
			categoria._getContracts().remove(contrato);
			contrato._setContractCategory(null);
		}
	}

	public static class Typefy {

		/**
		 * Asocia un tipo de contrato a un contrato
		 * 
		 * @param contrato
		 * @param tipo
		 */
		public static void link(Contract contrato, ContractType tipo) {
			contrato._setContractType(tipo);
			tipo._getContracts().add(contrato);
		}

		/**
		 * Desasocia el tipo de contrato de un contrato
		 * 
		 * @param contrato
		 * @param tipo
		 */
		public static void unlink(Contract contrato, ContractType tipo) {
			tipo._getContracts().remove(contrato);
			contrato._setContractCategory(null);
		}
	}

	public static class Percibir {

		/**
		 * Asocia una nómina a un contrato
		 * 
		 * @param contrato
		 * @param nomina
		 */
		public static void link(Contract contrato, Payroll nomina) {
			nomina._setContract(contrato);
			contrato._getPayrolls().add(nomina);
		}

		/**
		 * Desasocia una nómina de un contrato
		 * 
		 * @param contrato
		 * @param nomina
		 */
		public static void unlink(Contract contrato, Payroll nomina) {
			contrato._getPayrolls().remove(nomina);
			nomina._setContract(null);
		}
	}
}
