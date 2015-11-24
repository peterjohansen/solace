package com.actram.solace.interpreter;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.actram.solace.interfaces.InputConverter;
import com.actram.solace.interfaces.InterpreterAction;
import com.actram.solace.interfaces.OutputOwner;

/**
 * The most basic input interpreter. Can ask for {@link String}s and provide
 * feedback to the user.
 * <p>
 * Also defines methods to get an arbitrary type.
 *
 * @author Peter André Johansen
 */
public interface InputInterpreter extends OutputOwner {

	/**
	 * Asks the user for a {@link String}.
	 * 
	 * @return the {@code String}
	 */
	public String getString();

	/**
	 * Returns the result of
	 * {@link #getUntilValid(Supplier, InputConverter, InterpreterAction)} using
	 * {@link #getString()} for input and performs the given action on an
	 * conversion error if appropriate.
	 */
	public default <T> T getUntilValid(InputConverter<T> converter, InterpreterAction conversionErrorAction) {
		return getUntilValid(() -> getString(), converter, conversionErrorAction);
	}

	/**
	 * Returns the result of
	 * {@link #getUntilValid(Supplier, InputConverter, InterpreterAction)} using
	 * {@link #getString()} for input and displays the given conversion error
	 * message if appropriate.
	 */
	public default <T> T getUntilValid(InputConverter<T> converter, String conversionErrorMessage) {
		return getUntilValid(() -> getString(), converter, conversionErrorMessage);
	}
	
	/**
	 * Returns the result of
	 * {@link #getUntilValid(Supplier, Predicate, InputConverter, InterpreterAction)}
	 * using {@link #getString()} for input and performs the given action on an
	 * conversion error if appropriate.
	 */
	public default <T> T getUntilValid(Predicate<String> predicate, InputConverter<T> converter, InterpreterAction conversionErrorAction) {
		return getUntilValid(() -> getString(), predicate, converter, conversionErrorAction);
	}

	/**
	 * Returns the result of
	 * {@link #getUntilValid(Supplier, Predicate, InputConverter, InterpreterAction)}
	 * using {@link #getString()} for input and displays the given conversion
	 * error message if appropriate.
	 */
	public default <T> T getUntilValid(Predicate<String> predicate, InputConverter<T> converter, String conversionErrorMessage) {
		return getUntilValid(() -> getString(), predicate, converter, conversionErrorMessage);
	}

	/**
	 * Asks the user for a value. An input {@link String} is provided by the
	 * given input supplier. The input is then interpreted by the given
	 * converter and returned if and only if the input does not cause the converter to throw
	 * an exception.
	 * <p>
	 * The user will be asked as many times as it takes to get a valid response.
	 * 
	 * @param conversionErrorAction the action to perform if the input fails the
	 *            predicate, or {@code null} to do nothing
	 * @return the interpreted input
	 */
	public default <T> T getUntilValid(Supplier<String> inputSupplier, InputConverter<T> converter, InterpreterAction conversionErrorAction) {
		Objects.requireNonNull(inputSupplier, "the input supplier cannot be null");
		Objects.requireNonNull(converter, "the converter cannot be null");

		while (true) {
			try {
				final String input = inputSupplier.get();
				return converter.convert(input);
			} catch (Exception e) {
				if (conversionErrorAction != null) {
					conversionErrorAction.perform(this);
				}
			}
		}
	}

	/**
	 * Returns the result of
	 * {@link #getUntilValid(Supplier, InputConverter, InterpreterAction)} and
	 * displays the given conversion error message if appropriate.
	 */
	public default <T> T getUntilValid(Supplier<String> inputSupplier, InputConverter<T> converter, String conversionErrorMessage) {
		return getUntilValid(inputSupplier, converter, interpreter -> println(conversionErrorMessage));
	}

	/**
	 * Asks the user for a value. An input {@link String} is provided by the
	 * given input supplier. The input is then interpreted by the given
	 * converter if and only if the input passes the predicate.
	 * <p>
	 * The user will be asked as many times as it takes to get a valid response.
	 * 
	 * @param conversionErrorAction the action to perform if the input fails the
	 *            predicate, or {@code null} to do nothing
	 * @return the interpreted input
	 */
	public default <T> T getUntilValid(Supplier<String> inputSupplier, Predicate<String> predicate, InputConverter<T> converter, InterpreterAction conversionErrorAction) {
		Objects.requireNonNull(inputSupplier, "the input supplier cannot be null");
		Objects.requireNonNull(predicate, "the predicate cannot be null");
		Objects.requireNonNull(converter, "the converter cannot be null");

		while (true) {
			final String input = getString();
			if (predicate.test(input)) {
				return converter.convert(input);
			} else {
				if (conversionErrorAction != null) {
					conversionErrorAction.perform(this);
				}
			}
		}
	}

	/**
	 * Returns the result of
	 * {@link #getUntilValid(Supplier, Predicate, InputConverter, InterpreterAction)}
	 * and displays the given conversion error message if appropriate.
	 */
	public default <T> T getUntilValid(Supplier<String> inputSupplier, Predicate<String> predicate, InputConverter<T> converter, String conversionErrorMessage) {
		return getUntilValid(inputSupplier, predicate, converter, interpreter -> println(conversionErrorMessage));
	}
}