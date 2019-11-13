package uo.ri.business.dto;

import java.util.Date;

public class InvoiceDto {

	public Long id;			// the internal (db) id
	public double total;	// total amount (money) vat included
	public double vat;		// amount of vat (money)
	public long number;		// the invoice identity, a sequential number
	public Date date;		// of the invoice
	public String status;	// the status as in FacturaStatus

}
