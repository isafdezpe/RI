package alb.util.singleton;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SingletonTests {

	@Test
	public void testNull() {
		Singleton<String> value = Singleton.empty();
		
		assertTrue( value.orElse( () -> null ) == null );
	}

	@Test
	public void testWithString() {
		Singleton<String> value = Singleton.empty();
		
		assertTrue( value.orElse( () -> "Hello World!" ).equals("Hello World!") );
	}

	/**
	 * One assigned it does not change
	 */
	@Test
	public void testStringDoesNotChange() {
		Singleton<String> value = Singleton.empty();
		
		value.orElse( () -> "Hello World!" );
		assertTrue( value.orElse( () -> "Bye bye World!" ).equals("Hello World!") );
	}
	
	/**
	 * The builder code is just called the first time (lazy evaluation)
	 */
	@Test
	public void testBuilderCodeNotExecuted() {
		Singleton<Mock> value = Singleton.empty();
		final Counter counter1 = new Counter();
		final Counter counter2 = new Counter();
		
		value.orElse( () -> new Mock() {
			{ counter1.inc(); }
		});
		value.orElse( () -> new Mock() {
			{ counter2.inc(); }
		});
		assertTrue( counter1.get() == 1 );
		assertTrue( counter2.get() == 0 );
	}
	
	interface Mock { /* nothing here */ }
	class Counter {
		int value = 0;
		void inc() { value++; }
		int get() { return value; }
	}
}
