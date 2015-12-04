package com.actram.solace.interpreter;

/**
 * 
 *
 * @author Peter André Johansen
 */
public interface InputInterpreter {
	default boolean nextBoolean() {
		return Boolean.valueOf(nextString());
	}

	default char nextChar() {
		return nextString().charAt(0);
	}

	default double nextFloat() {
		return Float.parseFloat(nextString());
	}

	default int nextInt() {
		return Integer.parseInt(nextString());
	}

	String nextString();
}