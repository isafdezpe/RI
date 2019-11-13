package uo.ri.util;

import alb.util.random.Random;

public class Values {

	private static String newPrefixedString(String prefix) {
		return prefix + "-" + Random.string(5);
	}

	public static String newName() {
		return newPrefixedString( "name" );
	}

	public static String newSurname() {
		return newPrefixedString( "surname" );
	}

	public static String newDni() {
		return "dni-" + Random.integer(1, 1000000);
	}

	public static String newString(String prefix, int len) {
		return prefix + "-" + Random.string( len );
	}

}
