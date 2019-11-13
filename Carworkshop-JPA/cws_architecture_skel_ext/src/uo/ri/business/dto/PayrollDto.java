package uo.ri.business.dto;

import java.util.Date;

public class PayrollDto {

	public Long id;
	public Date date;
	public double baseSalary;
	public double extraSalary;
	public double productivity;
	public double triennium;
	public double irpf;
	public double socialSecurity;
	
	public double grossTotal;
	public double discountTotal;
	public double netTotal;

}
