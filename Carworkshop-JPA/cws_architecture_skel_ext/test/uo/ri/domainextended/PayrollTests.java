package uo.ri.domainextended;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import alb.util.date.Dates;
import uo.ri.model.Contract;
import uo.ri.model.ContractCategory;
import uo.ri.model.Payroll;
import uo.ri.util.CategoryBuilder;
import uo.ri.util.ContractBuilder;
import uo.ri.util.PayrollBuilder;

public class PayrollTests {

	private static final double BASE_SALARY = 10000;
	private static final double BETTER_BASE_SALARY = BASE_SALARY * 3;
	private static final double PRODUCTIVITY_PLUS = 0.05;
	private static final double TRIENNIUM_SALARY = 30;
	private static final double TOTAL_BY_INTERVENTIONS = 10000;
	
	private ContractCategory category;

	@Before
	public void setUp() throws Exception {
		category = new CategoryBuilder()
				.withProductivityPlus( PRODUCTIVITY_PLUS )
				.withTrienniumSalary( TRIENNIUM_SALARY )
				.build();
	}

	/**
	 * The date of the payroll is the last day of the previous 
	 * month passed as date
	 */
	@Test
	public void testPayrollDate() {
		Date startDate = Dates.fromDdMmYyyy(10, 01, 2019);
		Date beginningOfFebruary = Dates.fromDdMmYyyy(02, 02, 2019);
		Contract c = new ContractBuilder()
				.withStartDate(startDate)
				.build();
		
		Date expectedDate = Dates.fromDdMmYyyy(31, 01, 2019);
		
		Payroll payrollOfJanuary = new Payroll(c, beginningOfFebruary, 0.0 );
				
		assertEquals( expectedDate, payrollOfJanuary.getDate() );
	}
	
	/**
	 * getDate() return a copy of the date
	 */
	@Test
	public void testGetDateReturnsCopy() {
		Payroll p = new PayrollBuilder().build();
		Date date = p.getDate();
		Date date2 = p.getDate();
		
		assertTrue( date != date2 );
	}	

	/**
	 * A payroll cannot be created for a month prior to the 
	 * start date of the contract
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testPayrollBeforeDate() {
		Contract c = new ContractBuilder().build();
		Date outOfRangeDate = c.getStartDate(); 
		
		// the payroll will be for the previous month, must throw exception
		new Payroll(c, outOfRangeDate, 0.0 );
	}
	
	/**
	 * The new payroll is linked with its contract
	 */
	@Test
	public void testLinkedPayrollAndContract() {
		Contract c = new ContractBuilder().build();
		Date date = Dates.addMonths( c.getStartDate(), 1);
		Payroll p = new Payroll(c, date, 0.0 );
		
		assertTrue( c.getPayrolls().contains( p ));
		assertTrue( p.getContract() == c);
	}
	
	/**
	 * A payroll for February, for a one month old contract, 
	 * without interventions has only base salary and social security
	 */
	@Test
	public void testForJanuaryPayroll() {
		Date january = Dates.fromDdMmYyyy(10, 01, 2019);
		Date beginningOfFebruary = Dates.fromDdMmYyyy(02, 02, 2019);
		Contract c = new ContractBuilder()
				.withStartDate( january )
				.withBaseSalary( BASE_SALARY )
				.build();
		Payroll payrollOfJanuary = new Payroll(c, beginningOfFebruary, 0.0 );
		
		double[] expected = new double[] {
			/*base salary    */	BASE_SALARY / 14,
			/*extra salary   */	0.0,
			/*productivity   */	0.0,
			/*trienniums     */	0.0,
			/*irpf           */	0.0, // < 12.000 €/year => 0%
			/*social security*/	BASE_SALARY / 12 * 0.05,
		};
		
		assertRightValues( expected, payrollOfJanuary );
	}
	
	/**
	 * A payroll for June, for a six months old contract, 
	 * without interventions has right values for:
	 * 	- base salary
	 *  - extra salary (June)
	 *  - social security
	 */
	@Test
	public void testForJunePayroll() {
		Date january = Dates.fromDdMmYyyy(10, 01, 2019);
		Date beginningOfJuly = Dates.fromDdMmYyyy(02, 07, 2019);
		Contract c = new ContractBuilder()
				.withStartDate( january )
				.withBaseSalary( BASE_SALARY )
				.build();
		
		double[] expected = new double[] {
			/*base salary    */	BASE_SALARY / 14,
			/*extra salary   */	BASE_SALARY / 14,
			/*productivity   */	0.0,
			/*trienniums     */	0.0,
			/*irpf           */	0.0,
			/*social security*/	BASE_SALARY / 12 * 0.05,
		};
			
		Payroll payrollOfJuly = new Payroll(c, beginningOfJuly, 0.0 );
		
		assertRightValues( expected, payrollOfJuly );
	}
	
	/**
	 * A payroll for March, for a three months old contract, 
	 * with interventions, has right values for:
	 * 	- base salary
	 *  - productivity plus
	 *  - social security
	 */
	@Test
	public void testPayrollWithProductivity() {
		Date january = Dates.fromDdMmYyyy(10, 01, 2019);
		Date beginningOfMarch = Dates.fromDdMmYyyy(02, 03, 2019);
		Contract c = new ContractBuilder()
				.withStartDate( january )
				.withBaseSalary( BASE_SALARY )
				.withCategory( category )
				.build();

		double[] expected = new double[] {
				/*base salary    */	BASE_SALARY / 14,
				/*extra salary   */	0.0,
				/*productivity   */	TOTAL_BY_INTERVENTIONS * PRODUCTIVITY_PLUS,
				/*trienniums     */	0.0,
				/*irpf           */	0.0,
				/*social security*/	BASE_SALARY / 12 * 0.05,
			};
				
		Payroll payrollOfJuly = new Payroll(
					c, 
					beginningOfMarch, 
					TOTAL_BY_INTERVENTIONS 
			);
		
		assertRightValues( expected, payrollOfJuly );
	}
	
	/**
	 * A payroll of November, for a 2 year, 11 month old contract, has:
	 * 	- base salary
	 *  - social security
	 * 	- BUT no triennium
	 */
	@Test
	public void testPayrollNoTriennium() {
		Date january = Dates.fromDdMmYyyy(10, 01, 2015);
		Date beginningOfDecember = Dates.fromDdMmYyyy(01, 12, 2017);
		Contract c = new ContractBuilder()
				.withStartDate( january )
				.withBaseSalary( BASE_SALARY )
				.withCategory( category )
				.build();

		double[] expected = new double[] {
				/*base salary    */	BASE_SALARY / 14,
				/*extra salary   */	0.0,
				/*productivity   */	0.0,
				/*trienniums     */	0.0,
				/*irpf           */	0.0,
				/*social security*/	BASE_SALARY / 12 * 0.05,
			};
				
		Payroll payrollOfNovember2017 = new Payroll(c, beginningOfDecember, 0.0);
		
		assertRightValues( expected, payrollOfNovember2017 );
	}
		
	/**
	 * Payroll for a 3 year old contract has:
	 * 	- base salary
	 *  - social security
	 * 	- AND triennium
	 */
	@Test
	public void testPayrollWithTriennium() {
		Date february = Dates.fromDdMmYyyy(10, 02, 2015);
		Date march2018 = Dates.fromDdMmYyyy(01, 03, 2018);
		Contract c = new ContractBuilder()
				.withStartDate( february )
				.withBaseSalary( BASE_SALARY )
				.withCategory( category )
				.build();

		double[] expected = new double[] {
				/*base salary    */	BASE_SALARY / 14,
				/*extra salary   */	0.0,
				/*productivity   */	0.0,
				/*trienniums     */	1 * TRIENNIUM_SALARY,
				/*irpf           */	0.0,
				/*social security*/	BASE_SALARY / 12 * 0.05,
			};
				
		Payroll payrollOfFebruary2018 = new Payroll(c, march2018, 0.0);
		
		assertRightValues( expected, payrollOfFebruary2018 );
	}		
	
	/**
	 * A payroll of December, for a 10 year old contract, with 30.000 €/year
	 * of base year salary, and a total by interventions of 10.000 €, has: 
	 *  - base salary
	 *  - extra pay (December)
	 *  - productivity plus
	 * 	- three trienniums
	 *  - irpf discount (>= 30.000, < 40.000 €/year => 15%)
	 *  - social security discount
	 */
	@Test
	public void testForCompletePayroll() {
		Date january = Dates.fromDdMmYyyy(10, 01, 2010);
		Date january2020 = Dates.fromDdMmYyyy(02, 01, 2020);
		Contract c = new ContractBuilder()
				.withStartDate( january )
				.withBaseSalary( BETTER_BASE_SALARY )
				.withCategory( category )
				.build();
		
		double[] expected = new double[] {
			/*base salary    */	BETTER_BASE_SALARY / 14,
			/*extra salary   */	BETTER_BASE_SALARY / 14,
			/*productivity   */	TOTAL_BY_INTERVENTIONS * PRODUCTIVITY_PLUS,
			/*trienniums     */	3 * TRIENNIUM_SALARY,
			/*irpf           */	0.0, // assigned below (15%)
			/*social security*/	BETTER_BASE_SALARY / 12 * 0.05,
		};
		/*irpf */ expected[4] = 0.15 * (expected[0] + expected[1] + expected[2] + expected[3]);
		
		Payroll payrollOfDecember2019 = new Payroll(c, january2020, TOTAL_BY_INTERVENTIONS );
		
		assertRightValues( expected, payrollOfDecember2019 );
	}

	/**
	 * getGrossTotal, getDiscountTotal, getNetTotal return valid values
	 */
	@Test
	public void testPayrollTotals() {
		Date january = Dates.fromDdMmYyyy(10, 01, 2010);
		Date january2020 = Dates.fromDdMmYyyy(02, 01, 2020);
		Contract c = new ContractBuilder()
				.withStartDate( january )
				.withBaseSalary( BETTER_BASE_SALARY )
				.withCategory( category )
				.build();
		
		double[] expected = new double[] {
			/*base salary    */	BETTER_BASE_SALARY / 14,
			/*extra salary   */	BETTER_BASE_SALARY / 14,
			/*productivity   */	TOTAL_BY_INTERVENTIONS * PRODUCTIVITY_PLUS,
			/*trienniums     */	3 * TRIENNIUM_SALARY,
			/*irpf           */	0.0, // assigned below (15%)
			/*social security*/	BETTER_BASE_SALARY / 12 * 0.05,
		};
		/*irpf */ expected[4] = 0.15 * (expected[0] + expected[1] + expected[2] + expected[3]);
		
		Payroll payrollOfDecember2019 = new Payroll(c, january2020, TOTAL_BY_INTERVENTIONS );
		
		assertRightTotals( expected, payrollOfDecember2019 );
	}
	
	private void assertRightTotals(double[] expected, Payroll p) {
		double grossTotal = expected[0] + expected[1] + expected[2] + expected[3];
		double discountTotal = expected[4] + expected[5];
		double netTotal = grossTotal - discountTotal;
		
		assertEquals(grossTotal, p.getGrossTotal(), 0.01);
		assertEquals(discountTotal, p.getDiscountTotal(), 0.01);
		assertEquals(netTotal, p.getNetTotal(), 0.01);
	}

	private void assertRightValues(double[] expected, Payroll p) {
		int i = 0;
		assertEquals( expected[i++], p.getBaseSalary(), 0.01 );
		assertEquals( expected[i++], p.getExtraSalary(), 0.01 );
		assertEquals( expected[i++], p.getProductivity(), 0.01 );
		assertEquals( expected[i++], p.getTriennium(), 0.01 );
		
		assertEquals( expected[i++], p.getIrpf(), 0.01 );
		assertEquals( expected[i++], p.getSocialSecurity(), 0.01 );
	}

}
