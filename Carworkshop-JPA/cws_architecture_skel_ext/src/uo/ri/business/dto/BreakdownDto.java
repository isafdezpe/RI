package uo.ri.business.dto;

import java.util.Date;

public class BreakdownDto {
	
	public long id;
	public long vehicleId;
	public String description;
	public Date date;
	public Long invoiceId;
	public boolean usedForVoucher;
	public double total;
	public String status;

}
