package uo.ri.domainextended;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import alb.util.date.Dates;
import uo.ri.model.Contract;
import uo.ri.model.Mecanico;
import uo.ri.model.Payroll;
import uo.ri.util.ContractBuilder;
import uo.ri.util.MechanicBuilder;
import uo.ri.util.PayrollBuilder;

public class ContractTests {

	private static final Date START_DATE = Dates.subDays(Dates.today(), 100);
	private static final double BASE_SALARY = 10000;
	
	private Mecanico mechanic;
	private Contract contract;

	@Before
	public void setUp() throws Exception {
		mechanic = new MechanicBuilder().build();
		contract = new ContractBuilder()
				.forMechanic(mechanic)
				.withStartDate( START_DATE )
				.withBaseSalary( BASE_SALARY )
				.build();
	}
	
	/**
	 * The constructor links the contract with the mechanic
	 */
	@Test
	public void testContructorLinks() {
		assertTrue( mechanic.getContracts().contains( contract )  );
		assertTrue( contract.getMechanic() == mechanic );
	}

	/**
	 * The startDate is always set to the first day of the month passed
	 */
	@Test
	public void testStartDateIsFirstDayOfMonth() {
		Date startDate = Dates.fromDdMmYyyy(15, 06, 2018);
		Date expected = Dates.fromDdMmYyyy(1, 06, 2018);
		
		Contract contract = new Contract(mechanic, startDate, 10000);
		
		assertTrue( contract.getStartDate().equals( expected ));
		assertTrue( Dates.isFirstDayOfMonth( contract.getStartDate() ) );
	}
		
	/**
	 * The end date is always set to the last day of the month passed as date
	 */
	@Test
	public void testEndDateIsLastDayOfMonth() {
		Date endDate = Dates.addDays(contract.getStartDate(), 15);
		Date expected = Dates.lastDayOfMonth( endDate );
		
		contract.markAsFinished( endDate );
		
		assertTrue( contract.getEndDate().equals( expected ));
		assertTrue( Dates.isLastDayOfMonth( contract.getEndDate() ) );
	}
	
	/**
	 * The end date cannot be before the start date
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testEndDateGreaterThanStartDateLesser() {
		/*
		 *  The contract start date is always the 1st day of the month
		 *  and the end date is always the last day of the month passed
		 *  to marksAsFinished(...), then, with this endDate, 
		 *  the end date will be the last day of the previous month, 
		 *  which is an error.
		 */
		Date lastDayOfPreviousMonth = Dates.subDays( contract.getStartDate(), 1);
		
		contract.markAsFinished( lastDayOfPreviousMonth );
	}
	
	/**
	 * The end date passed may be the start date, but the contract's end date 
	 * is set to the last date of the month anyway  
	 */
	@Test
	public void testEndDateGreaterThanStartDateEquals() {
		contract.markAsFinished( contract.getStartDate() );
		
		assertTrue( Dates.isLastDayOfMonth( contract.getEndDate() ));
	}
	
	/**
	 * A new contract can be finished with zero compensation 
	 */
	@Test
	public void testWithNoCompensation() {
		Date endDate = Dates.addDays( contract.getStartDate(), 15 );
		
		contract.markAsFinished( endDate );
		
		assertTrue( contract.getCompensation() == 0.0 );
		assertTrue( contract.isFinished() );
	}

	/**
	 * A contract with less than one year has no compensation
	 */
	@Test
	public void testWithLessThen12MonthsHasNoCompensation() {
		Date startDate = contract.getStartDate();
		addPayrolls( 6 );
		Date endDate = Dates.addMonths( startDate, 6 );
		endDate = Dates.subDays(endDate, 10);
		
		contract.markAsFinished(endDate);
		
		assertTrue( contract.getCompensation() == 0.0 );
		assertTrue( contract.isFinished() );
	}

	/**
	 * A contract with 12 months gets the right compensation
	 */
	@Test
	public void testWith12MonthsCompensation() {
		Date startDate = contract.getStartDate();
		addPayrolls( 12 );
		Date endDate = Dates.addMonths( startDate, 12 );
		endDate = Dates.subDays(endDate, 10);
		
		/*
		 *  One year gross salary is just the year base salary because
		 *     - there are no trienniums
		 *     - there is no productivity plus for the payrolls generated
		 */
		double expected = 
			BASE_SALARY / 365
			* contract.getContractType().getCompensationDays();
		
		contract.markAsFinished(endDate);
		
		assertEquals( expected, contract.getCompensation(), 0.01 );
		assertTrue( contract.isFinished() );
	}
	
	/**
	 * A contract with more than 12 months gets the right compensation
	 */
	@Test
	public void testWithMoreThan12MonthsCompensation() {
		Date startDate = contract.getStartDate();
		addPayrolls( 18 );
		Date endDate = Dates.addMonths( startDate, 18 );
		endDate = Dates.subDays(endDate, 10);
		
		/*
		 *  One year gross salary is just the year base salary because
		 *     - there are no trienniums
		 *     - there is no productivity plus for the payrolls generated
		 */
		double expected = 
			BASE_SALARY	/ 365
			* contract.getContractType().getCompensationDays();
		
		contract.markAsFinished(endDate);
		
		assertEquals( expected, contract.getCompensation(), 0.01 );
		assertTrue( contract.isFinished() );
	}
	
	private void addPayrolls(int qty) {
		Date payrollDate = Dates.addMonths(contract.getStartDate(), 1);
		for(int i = 0; i < qty; i++) {
			new Payroll(contract, payrollDate, 0.0);
			payrollDate = Dates.addMonths( payrollDate, 1);
		}
	}
	
	/**
	 * getLastPayroll() returns null if no payroll
	 */
	@Test
	public void testLastPayrollIsNull() {
		assertTrue( contract.getLastPayroll() == null );
	}
		
	/**
	 * getLastPayroll() returns the most recent payroll 
	 */
	@Test
	public void testMostRecentPayroll() {
		Date startDate = contract.getStartDate();
		Date firstPayrollDate = Dates.addMonths( startDate, 1);
		Date secondPayrollDate = Dates.addMonths( startDate, 2);
		
		new PayrollBuilder() // automatically linked with contract (constructor)
			.forContract(contract)
			.withDate( firstPayrollDate )
			.build();
		
		Payroll lastPayroll = new PayrollBuilder()
				.forContract(contract)
				.withDate( secondPayrollDate )
				.build();
		
		assertTrue( contract.getLastPayroll() == lastPayroll );
	}

	/**
	 * An already finished contract raises exception trying 
	 * to mark it as finished again
	 */
	@Test(expected = IllegalStateException.class)
	public void testAlreadyFinished() {
		Date endDate = Dates.addDays( contract.getStartDate(), 15 );
		
		contract.markAsFinished( endDate );
		contract.markAsFinished( endDate );
	}
	
	/**
	 * getIrpfPercent() returns the right values for the year base salary
	 */
	@Test
	public void testGetIrpfPercent() {
		Contract c1 = new ContractBuilder().withBaseSalary( 10000 ).build();
		Contract c2 = new ContractBuilder().withBaseSalary( 20000 ).build();
		Contract c3 = new ContractBuilder().withBaseSalary( 35000 ).build();
		Contract c4 = new ContractBuilder().withBaseSalary( 45000 ).build();
		Contract c5 = new ContractBuilder().withBaseSalary( 55000 ).build();
		Contract c6 = new ContractBuilder().withBaseSalary( 65000 ).build();
		
		assertEquals( 0.0, 	c1.getIrpfPercent(), 0.01 ); 
		assertEquals( 0.1, 	c2.getIrpfPercent(), 0.01 );	
		assertEquals( 0.15, c3.getIrpfPercent(), 0.01 );
		assertEquals( 0.2, 	c4.getIrpfPercent(), 0.01 );
		assertEquals( 0.3, 	c5.getIrpfPercent(), 0.01 );
		assertEquals( 0.4, 	c6.getIrpfPercent(), 0.01 );
	}

	/**
	 * getStartDate() returns a copy of the date
	 */
	@Test
	public void testGetStartDateReturnCopies() {
		Date d1 = contract.getStartDate();
		Date d2 = contract.getStartDate();
		
		assertTrue( d1 != d2 ); 
	}
	
	/**
	 * getEndDate() returns a copy of the date
	 */
	@Test
	public void testGetEndDateReturnCopies() {
		contract.markAsFinished( contract.getStartDate() );
		Date d1 = contract.getEndDate();
		Date d2 = contract.getEndDate();
		
		assertTrue( d1 != d2 ); 
	}
	
	/**
	 * yearBaseSalary passed must be positive
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testPositiveSalary() {
		double INVALID = -1.0;
		new Contract(mechanic, Dates.today(), INVALID);
	}
	
	/**
	 * startDate is copied in the constructor even if it already 
	 * is the first day of month
	 */
	@SuppressWarnings("deprecation") // for setDate
	public void testContructorCopiesStartDate() {
		Date fdom = Dates.firstDayOfMonth( Dates.today() );
		Contract c = new Contract(mechanic, fdom, 10000);
		
		fdom.setDate(15);
		
		assertTrue( ! fdom.equals( c.getStartDate() ) );
	}
	
	/**
	 * endDate is copied in the constructor even if it already 
	 * is the last day of month
	 */
	@SuppressWarnings("deprecation") // for setDate
	public void testContractorCopiesEndDate() {
		Date ldom = Dates.lastDayOfMonth( Dates.today() );
		Contract c = new Contract(mechanic, Dates.today(), ldom, 10000);
		
		ldom.setDate(15);
		
		assertTrue( ! ldom.equals( c.getEndDate() ) );
	}
	
	/**
	 * endDate is copied in the setter even if it already 
	 * is the last day of month
	 */
	@SuppressWarnings("deprecation") // for setDate
	public void testSetterCopiesEndDate() {
		Date ldom = Dates.lastDayOfMonth( Dates.today() );
		Contract c = new Contract(mechanic, Dates.today(), 10000);
		c.setEndDate( ldom );
		
		ldom.setDate(15);
		
		assertTrue( ! ldom.equals( c.getEndDate() ) );
	}
	
}
