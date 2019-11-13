package uo.ri.util;

/**
 * Base for an implementation of the Builder pattern 
 *
 * @param <T> the type of the object being constructed
 */
public interface Builder<T> {
	
	T build();

}
