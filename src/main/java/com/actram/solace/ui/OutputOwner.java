package com.actram.solace.ui;

import java.util.Locale;

/**
 * An output owner controls the output of a program. It can be sent objects that
 * will be displayed to the user, including new lines.
 * 
 * @author Peter André Johansen
 */
public interface OutputOwner {

	/**
	 * Removes everything that has been output.
	 */
	void clearOutput();

	/**
	 * @return what has been output
	 */
	String getCurrentOutput();

	/**
	 * Prints the given object.
	 */
	void print(Object obj);

	/**
	 * Prints a formatted string using the specified locale, format string and
	 * arguments.
	 */
	default void printf(Locale l, String format, Object... args) {
		print(String.format(l, format, args));
	}

	/**
	 * Prints a formatted string using the specified format string and
	 * arguments.
	 */
	default void printf(String format, Object... args) {
		print(String.format(format, args));
	}

	/**
	 * Prints a new line.
	 */
	default void println() {
		print("\n");
	}

	/**
	 * Prints the given object followed by a new line.
	 */
	default void println(Object obj) {
		print(obj + "\n");
	}

	/**
	 * Sets the current output. Any existing output will be overwritten.
	 * <p>
	 * If the given object is {@code null}, the output is cleared.
	 * 
	 * @param obj the output
	 */
	void setCurrentOutput(Object obj);
}