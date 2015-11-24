package com.actram.solace.interfaces;

import java.util.regex.Pattern;

import com.actram.solace.interpreter.InputInterpreter;

/**
 * Contains methods for simple input interpretation. Takes input as a
 * {@link String} and attempts to convert it to primitive Java types.
 * 
 * @author Peter André Johansen
 */
public interface SimpleInputInterpreter extends InputInterpreter {

	public default int getInt() {
		return getInt((InterpreterAction) null);
	}

	public default int getInt(InterpreterAction numberFormatErrorAction) {
		return getUntilValid(input -> Integer.parseInt(input), numberFormatErrorAction);
	}

	public default int getInt(String numberFormatErrorMessage) {
		return getInt(interpreter -> println(numberFormatErrorMessage));
	}

	public default int getInt(int minValue, int maxValue) {
		return getUntilValid(() -> getInt(), number -> number <= maxValue && number >= minValue, input -> input, null);
	}

	public default int getInt(int minValue, int maxValue, InterpreterAction numberFormatErrorAction, InterpreterAction numberOutOfRangeErrorAction) {
		return 0;
	}

	public default int getInt(int minValue, int maxValue, String numberFormatErrorMessage, String numberOutOfRangeErrorMessage) {
		return 0;
	}

	/**
	 * Asks the user for a {@link String} that conforms to the given regex. This
	 * method will not provide any feedback to the user if they enter an illegal
	 * value - address this by using this one instead:
	 * {@link #getString(String, String)}
	 * <p>
	 * The user will be asked as many times as it takes to get a valid response.
	 * 
	 * @param regex the regex the input must match
	 * @return the {@code String}
	 */
	public default String getString(String regex) {
		return getString(regex, null);
	}

	/**
	 * Asks the user for a {@link String} that conforms to the given regex.
	 * <p>
	 * The user will be asked as many times as it takes to get a valid response.
	 * 
	 * @param regex the regex the input must match
	 * @param matchErrorMessage the message to display if the input does not
	 *            match the regex
	 * @return the {@code String}
	 */
	public default String getString(String regex, String matchErrorMessage) {
		final Pattern pattern = Pattern.compile(regex);
		return getUntilValid(input -> pattern.matcher(input).matches(), input -> input, interpreter -> println(matchErrorMessage));
	}
}