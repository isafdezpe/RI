package uo.ri.business.impl.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import uo.ri.business.dto.BreakdownDto;
import uo.ri.business.dto.CardDto;
import uo.ri.business.dto.CashDto;
import uo.ri.business.dto.ClientDto;
import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.dto.PayrollDto;
import uo.ri.business.dto.VoucherDto;
import uo.ri.model.Averia;
import uo.ri.model.Bono;
import uo.ri.model.Cliente;
import uo.ri.model.ContractCategory;
import uo.ri.model.ContractType;
import uo.ri.model.Factura;
import uo.ri.model.Mecanico;
import uo.ri.model.MedioPago;
import uo.ri.model.Metalico;
import uo.ri.model.Payroll;
import uo.ri.model.TarjetaCredito;

public class DtoAssembler {

	public static ClientDto toDto(Cliente c) {
		ClientDto dto = new ClientDto();

		dto.id = c.getId();
		dto.dni = c.getDni();
		dto.name = c.getNombre();
		dto.surname = c.getApellidos();

		return dto;
	}

	public static List<ClientDto> toClientDtoList(List<Cliente> clientes) {
		List<ClientDto> res = new ArrayList<>();
		for (Cliente c : clientes) {
			res.add(DtoAssembler.toDto(c));
		}
		return res;
	}

	public static MechanicDto toDto(Mecanico m) {
		MechanicDto dto = new MechanicDto();
		dto.id = m.getId();
		dto.dni = m.getDni();
		dto.name = m.getNombre();
		dto.surname = m.getApellidos();
		return dto;
	}

	public static List<MechanicDto> toMechanicDtoList(List<Mecanico> list) {
		List<MechanicDto> res = new ArrayList<>();
		for (Mecanico m : list) {
			res.add(toDto(m));
		}
		return res;
	}

	public static List<VoucherDto> toVoucherDtoList(List<Bono> list) {
		List<VoucherDto> res = new ArrayList<>();
		for (Bono b : list) {
			res.add(toDto(b));
		}
		return res;
	}

	public static VoucherDto toDto(Bono b) {
		VoucherDto dto = new VoucherDto();
		dto.id = b.getId();
		dto.clientId = b.getCliente().getId();
		dto.accumulated = b.getAcumulado();
		dto.code = b.getCodigo();
		dto.description = b.getDescripcion();
		dto.available = b.getDisponible();
		return dto;
	}

	public static CardDto toDto(TarjetaCredito tc) {
		CardDto dto = new CardDto();
		dto.id = tc.getId();
		dto.clientId = tc.getCliente().getId();
		dto.accumulated = tc.getAcumulado();
		dto.cardNumber = tc.getNumero();
		dto.cardExpiration = tc.getValidez();
		dto.cardType = tc.getTipo();
		return dto;
	}

	public static CashDto toDto(Metalico m) {
		CashDto dto = new CashDto();
		dto.id = m.getId();
		dto.clientId = m.getCliente().getId();
		dto.accumulated = m.getAcumulado();
		return dto;
	}

	public static InvoiceDto toDto(Factura factura) {
		InvoiceDto dto = new InvoiceDto();
		dto.id = factura.getId();
		dto.number = factura.getNumero();
		dto.date = factura.getFecha();
		dto.total = factura.getImporte();
		dto.vat = factura.getIva();
		dto.status = factura.getStatus().toString();
		return dto;
	}

	public static List<PaymentMeanDto> toPaymentMeanDtoList(
			List<MedioPago> list) {
		return list.stream().map(mp -> toDto(mp)).collect(Collectors.toList());
	}

	private static PaymentMeanDto toDto(MedioPago mp) {
		if (mp instanceof Bono) {
			return toDto((Bono) mp);
		} else if (mp instanceof TarjetaCredito) {
			return toDto((TarjetaCredito) mp);
		} else if (mp instanceof Metalico) {
			return toDto((Metalico) mp);
		} else {
			throw new RuntimeException("Unexpected type of payment mean");
		}
	}

	public static List<BreakdownDto> toBreakdownDtoList(List<Averia> list) {
		return list.stream().map(a -> toDto(a)).collect(Collectors.toList());
	}

	public static BreakdownDto toDto(Averia a) {
		BreakdownDto dto = new BreakdownDto();
		dto.id = a.getId();
		dto.invoiceId = a.getFactura().getId();
		dto.vehicleId = a.getVehiculo().getId();
		dto.description = a.getDescripcion();
		dto.date = a.getFecha();
		dto.total = a.getImporte();
		dto.status = a.getStatus().toString();
		return dto;
	}

	public static ContractTypeDto toDto(ContractType ct) {
		ContractTypeDto dto = new ContractTypeDto();
		dto.id = ct.getId();
		dto.name = ct.getName();
		dto.compensationDays = ct.getCompensationDays();
		return dto;
	}

	public static List<ContractTypeDto> toContractTypeDtoList(
			List<ContractType> list) {
		return list.stream().map(ct -> toDto(ct)).collect(Collectors.toList());
	}

	public static PayrollDto toDto(Payroll p) {
		PayrollDto dto = new PayrollDto();
		dto.id = p.getId();
		dto.date = p.getDate();
		dto.baseSalary = p.getBaseSalary();
		dto.extraSalary = p.getExtraSalary();
		dto.productivity = p.getProductivity();
		dto.triennium = p.getTriennium();
		dto.irpf = p.getIrpf();
		dto.socialSecurity = p.getSocialSecurity();
		dto.discountTotal = p.getDiscountTotal();
		dto.grossTotal = p.getGrossTotal();
		dto.netTotal = p.getNetTotal();
		return dto;
	}

	public static List<PayrollDto> toPayrollDtoList(List<Payroll> list) {
		return list.stream().map(p -> toDto(p)).collect(Collectors.toList());
	}

	public static ContractCategoryDto toDto(ContractCategory cc) {
		ContractCategoryDto dto = new ContractCategoryDto();
		dto.id = cc.getId();
		dto.name = cc.getName();
		dto.productivityPlus = cc.getProductivityPlus();
		dto.trieniumSalary = cc.getTrieniumSalary();
		return dto;
	}

	public static List<ContractCategoryDto> toContractCategoryDtoList(
			List<ContractCategory> list) {
		return list.stream().map(cc -> toDto(cc)).collect(Collectors.toList());
	}

}
