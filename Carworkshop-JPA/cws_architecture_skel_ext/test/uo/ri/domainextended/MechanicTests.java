package uo.ri.domainextended;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import alb.util.date.Dates;
import uo.ri.model.Contract;
import uo.ri.model.Mecanico;
import uo.ri.util.ContractBuilder;
import uo.ri.util.MechanicBuilder;

public class MechanicTests {

	private Mecanico mechanic;

	@Before
	public void setUp() throws Exception {
		mechanic = new MechanicBuilder().build();
	}

	/**
	 * getActiveContract() returns the active contract
	 */
	@Test
	public void testOneContractActive() {
		Contract contract = new ContractBuilder()
				.forMechanic(mechanic)
				.build();
		
		assertTrue( mechanic.getActiveContract() == contract );
	}

	/**
	 * getActiveContract() returns null if no active contract
	 */
	@Test
	public void testNoContractActive() {
		Contract contract = new ContractBuilder()
				.forMechanic(mechanic)
				.build();
		
		contract.markAsFinished( Dates.addDays(contract.getStartDate(), 1) );
		assertTrue( mechanic.getActiveContract() == null );
	}

	/**
	 * getActiveContract() returns the right active contract
	 */
	@Test
	public void testRightActiveContract() {
		Date today = Dates.today();
		Date inOneMonth = Dates.addMonths( today , 1);
		
		Contract contract = new ContractBuilder()
				.forMechanic(mechanic)
				.withStartDate( today )
				.build();
		contract.markAsFinished( Dates.addDays(contract.getStartDate(), 1) );

		Contract newContract = new ContractBuilder()
				.forMechanic(mechanic)
				.withStartDate( inOneMonth )
				.build();

		assertTrue( mechanic.getActiveContract() == newContract );
	}

}
