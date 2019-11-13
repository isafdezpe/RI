package uo.ri.ui.util;

import java.util.List;

import alb.util.console.Console;
import uo.ri.business.dto.BreakdownDto;
import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.dto.ContractDto;
import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.dto.PayrollDto;

public class Printer {

	public static void printInvoice(InvoiceDto invoice) {

		double importeConIVa = invoice.total;
		double iva =  invoice.vat;
		double importeSinIva = importeConIVa / (1 + iva / 100);

		Console.printf("Factura nº: %d%n", 				invoice.number );
		Console.printf("\tFecha: %1$td/%1$tm/%1$tY%n", 	invoice.date);
		Console.printf("\tTotal: %.2f €%n", 			importeSinIva);
		Console.printf("\tIva: %.1f %% %n", 			invoice.vat );
		Console.printf("\tTotal con IVA: %.2f €%n", 	invoice.total );
		Console.printf("\tEstado: %s%n", 				invoice.status );
	}

	public static void printPaymentMeans(List<PaymentMeanDto> medios) {
		Console.println();
		Console.println("Medios de pago disponibles");
		
		Console.printf("\t%s \t%-8.8s \t%s %n", "ID", "Tipo", "Acumulado");
		for (PaymentMeanDto medio : medios) {
			printPaymentMean( medio );
		}
	}

	private static void printPaymentMean(PaymentMeanDto medio) {
		Console.printf("\t%s \t%-8.8s \t%s %n", 
				medio.id,
				medio.getClass().getName(),  // not the best...
				medio.accumulated
		);
	}

	public static void printRepairing(BreakdownDto rep) {
		
		Console.printf("\t%d \t%-40.40s \t%td/%<tm/%<tY \t%-12.12s \t%.2f%n",  
				rep.id
				, rep.description 
				, rep.date
				, rep.status
				, rep.total
		);
	}

	public static void printMechanic(MechanicDto m) {

		Console.printf("\t%d %-10.10s %-25.25s %-25.25s%n",  
				m.id
				, m.dni
				, m.name
				, m.surname
			);
	}

	public static void printContractCategory(ContractCategoryDto c) {

		Console.printf("\t%d %-20.20s %7.2f %3.2f%n",  
				c.id
				, c.name
				, c.trieniumSalary
				, c.productivityPlus
			);
		
	}

	public static void printContractType(ContractTypeDto t) {

		Console.printf("\t%d %-20.20s %2d%n",  
				t.id
				, t.name
				, t.compensationDays
			);
		
	}

	public static void printContract(ContractDto c) {
	
		Console.printf(
			"\t%d %-10.10s %-20.20s %-20.20s %8.8s %td/%tm/%tY %7.2f %7.2f %td/%tm/%tY %n",  
				c.id
				, c.dni
				, c.categoryName
				, c.typeName
				, c.status
				, c.startDate, c.startDate, c.startDate
				, c.yearBaseSalary
				, c.compensation
				, c.endDate, c.endDate, c.endDate
			);

	}

	public static void printPayrollSummary(PayrollDto p) {

		Console.printf("\t%d %tm/%tY %7.2f%n",  
					p.id
					, p.date, p.date
					, p.netTotal
				);

	}

	public static void printPayrollDetail(PayrollDto p) {

		Console.printf("\t%d %tm/%tY %7.2f%n %7.2f%n %7.2f%n",  
				p.id
				, p.date, p.date
				, p.grossTotal
				, p.discountTotal
				, p.netTotal
			);
		Console.println("Abonos");
		Console.printf("/t Salario base: %7.2f%n", p.baseSalary);
		Console.printf("/t        extra: %7.2f%n", p.extraSalary);
		Console.printf("/tproductividad: %7.2f%n", p.productivity);
		Console.printf("/t     trienios: %7.2f%n", p.triennium);
		Console.printf("Descuentos");
		Console.printf("/t         IRPF: %7.2f%n", p.irpf);
		Console.printf("/t    S. social: %7.2f%n", p.socialSecurity);
	}
}
