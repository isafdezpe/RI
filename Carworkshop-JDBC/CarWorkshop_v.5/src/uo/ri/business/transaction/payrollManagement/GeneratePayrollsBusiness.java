package uo.ri.business.transaction.payrollManagement;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Month;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import alb.util.date.Dates;
import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.dto.ContractDto;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.dto.PayrollDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.BreakdownGateway;
import uo.ri.persistence.ContractCategoryGateway;
import uo.ri.persistence.ContractGateway;
import uo.ri.persistence.MechanicGateway;
import uo.ri.persistence.PayrollGateway;

public class GeneratePayrollsBusiness {
	
	private Connection c;
	private PayrollGateway payrollGateway;
	private MechanicGateway mechanicGateway;
	private ContractGateway contractGateway;
	private ContractCategoryGateway contractCategoryGateway;
	private BreakdownGateway breakdownGateway;
	
	/**
	 * Genera las nóminas para todos los contratos activos o los que se han 
	 * extinguido en el mes para el que se generan
	 * @return número de nóminas generadas
	 * @throws BusinessException
	 */
	public int execute() throws BusinessException{
		int count;
		
		try {
			c = Jdbc.getConnection();
			c.setAutoCommit(false);
			
			payrollGateway = PersistenceFactory.getPayrollGateway();
			payrollGateway.setConnection(c);
			
			mechanicGateway = PersistenceFactory.getMechanicGateway();
			mechanicGateway.setConnection(c);
			
			contractGateway = PersistenceFactory.getContractGateway();
			contractGateway.setConnection(c);
			
			contractCategoryGateway = 
					PersistenceFactory.getContractCategoryGateway();
			contractCategoryGateway.setConnection(c);
			
			breakdownGateway = PersistenceFactory.getBreakdownGateway();
			breakdownGateway.setConnection(c);
			
			Date payrollDate = Dates.subMonths(Dates.now(), 1);
			payrollDate = Dates.lastDayOfMonth(payrollDate);
			payrollDate = Dates.trunc(payrollDate);
			List<MechanicDto> mechanics = mechanicGateway.listActive();
			Map<Long, PayrollDto> payrolls = new HashMap<>();
			
			for (MechanicDto m : mechanics) {
				ContractDto contract = getActive(m, payrollDate);
				ContractCategoryDto contractCategory = 
						contractCategoryGateway.findById(contract.categoryId);
				
				PayrollDto payroll = generatePayroll(payrollDate, contract,
								contractCategory, m);
				
				if (!existe(payrollDate, contract.mechanicId))
					payrolls.put(contract.id, payroll);
			}
			
			count = payrollGateway.generatePayrolls(payrolls);
			
			c.commit();
		} catch (SQLException e) {
			try {
				if (c != null) {
					c.rollback();
				} 
			}catch (SQLException e2) {
			};
			throw new BusinessException(e.getMessage());
		} finally {
			Jdbc.close(c);
		}
		
		return count;
	}

	/**
	 * @param payrollDate fecha de la nómina
	 * @param mechanicId mecánico para el que se genera
	 * @return true si la nómina ya se ha generado, false si no
	 * @throws SQLException
	 */
	private boolean existe(Date payrollDate, Long mechanicId) 
			throws SQLException {
		List<PayrollDto> payrolls = payrollGateway.findByMechanic(mechanicId);
		
		for (PayrollDto p : payrolls) {
			if (Dates.trunc(p.date).equals(payrollDate))
				return true;
		}
		
		return false;
	}

	/**
	 * @param m mecánico
	 * @param payrollDate fecha de la nómina
	 * @return el contrato activo para el mecánico
	 * @throws SQLException
	 * @throws BusinessException
	 */
	private ContractDto getActive(MechanicDto m, Date payrollDate) 
			throws SQLException, BusinessException {
		List<ContractDto> contracts = contractGateway
				.findContractsByMechanicId(m.id);
		for (ContractDto co : contracts) {
			if (co.status.equals("ACTIVO") 
					|| co.endDate != null && Dates.isAfter(payrollDate
							, co.endDate) 
						&& Dates.isBefore(Dates.firstDayOfMonth(payrollDate)
								, co.endDate)
					|| co.endDate != null && co.endDate.equals(payrollDate))
				return co;
		}
		throw new BusinessException(
				"No hay un contrato activo para el mecánico con id " + m.id);
	}
	
	/**
	 * @param payrollDate fecha de la nómina
	 * @param contract contrato para el que se genera
	 * @param contractCategory categoria del contrato
	 * @param mechanic mecánico asociado al contrato
	 * @return la nueva nómina con los diferentes importes y datos del contrato
	 * @throws SQLException
	 */
	private PayrollDto generatePayroll(Date payrollDate, ContractDto contract
			, ContractCategoryDto contractCategory, MechanicDto mechanic) 
					throws SQLException {
		PayrollDto payroll = new PayrollDto();
		
		payroll.date = payrollDate;
		payroll.baseSalary = contract.yearBaseSalary / 14;
		
		if (Dates.month(payrollDate) == Month.JUNE.getValue() 
				|| Dates.month(payrollDate) == Month.DECEMBER.getValue())
			payroll.extraSalary = contract.yearBaseSalary / 14;
		else
			payroll.extraSalary = 0;
		
		long diff = payrollDate.getTime() - contract.startDate.getTime();
		int years = (int) ((TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS))
				/ 365);
		payroll.triennium = years / 3 * contractCategory.trieniumSalary;
		
		payroll.productivity = contractCategory.productivityPlus * 
				breakdownGateway.calcularImporteIntervenciones(mechanic
						, payrollDate);
		
		double irpfPerc;
		if (contract.yearBaseSalary < 12000)
			irpfPerc = 0;
		else if (contract.yearBaseSalary < 30000)
			irpfPerc = 0.1;
		else if (contract.yearBaseSalary < 40000)
			irpfPerc = 0.15;
		else if (contract.yearBaseSalary < 50000)
			irpfPerc = 0.2;
		else if (contract.yearBaseSalary < 60000)
			irpfPerc = 0.3;
		else
			irpfPerc = 0.4;
		payroll.irpf = (payroll.baseSalary + payroll.extraSalary + payroll.triennium 
				+ payroll.productivity) * irpfPerc;
		
		payroll.socialSecurity = contract.yearBaseSalary / 12 * 0.05;
				
		return payroll;
	}

}
