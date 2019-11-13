package uo.ri.business.dto;

import java.util.Date;

public class ContractDto {

	public Long mechanicId;
	public Long typeId;
	public Long categoryId;
	public Date startDate;
	public Date endDate;
	public double yearBaseSalary;

	// Filled in reading operations
	public Long id;
	public double compensation;
	public String status;
	
	// For reporting
	public String dni;
	public String categoryName;
	public String typeName;
	
}
