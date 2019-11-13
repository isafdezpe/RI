package uo.ri;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	uo.ri.associations.AllTests.class,
	uo.ri.domain.AllTests.class,
	uo.ri.persistence.PersistenceTest.class,
	uo.ri.domainextended.AllTests.class
})
public class AllTests { }
