package com.actram.solace;

import java.util.Locale;

/**
 * An output receiver can be sent objects that will be displayed to the user,
 * including new lines.
 * 
 * @author Peter André Johansen
 */
public interface OutputReceiver {

	/**
	 * Removes everything that has been output.
	 */
	public void clearOutput();

	/**
	 * Prints the given object.
	 */
	public void print(Object obj);

	/**
	 * Prints a formatted string using the specified locale, format string and
	 * arguments.
	 */
	public default void printf(Locale l, String format, Object... args) {
		print(String.format(l, format, args));
	}

	/**
	 * Prints a formatted string using the specified format string and
	 * arguments.
	 */
	public default void printf(String format, Object... args) {
		print(String.format(format, args));
	}

	/**
	 * Prints a new line.
	 */
	public default void println() {
		print("\n");
	}

	/**
	 * Prints the given object followed by a new line.
	 */
	public default void println(Object obj) {
		print(obj + "\n");
	}
}