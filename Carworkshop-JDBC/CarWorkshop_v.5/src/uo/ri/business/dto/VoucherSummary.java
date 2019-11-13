package uo.ri.business.dto;

/**
 * An aggregated result of all vouchers of a client
 */
public class VoucherSummary {
	
	public String dni;			// of the client 
	public String fullName;		// of the client 
	public int emitted;			// how many vouchers has been emitted
	public double totalAmount;	// the total amount "voucherized" (money)
	public double available;	// how much remains available for the client
	public double consumed;		// how much has been 
	
}
