package com.actram.solace.interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * More advanced input interpretation that attempts to parse basic
 * {@link String}-input into different types.
 * <p>
 * A type can be defined by specifying how to convert a {@link String} into the
 * desired type.
 *
 * @author Peter André Johansen
 */
public class InputInterpreterB {
	private final Map<InterpreterType, Supplier<Object>> converterMap = new HashMap<>();
	private final Map<InterpreterError, Runnable> errorMap = new HashMap<>();

	public void clearErrorHandlers() {
		this.errorMap.clear();
	}
	
	public void handle(InterpreterError error, Runnable action) {
		Objects.requireNonNull(error, "the interpreter error cannot be null");
		Objects.requireNonNull(action, "the error feedback action cannot be null");

		this.errorMap.put(error, action);
	}

	public void define(InterpreterType type, Supplier<Object> converter) {
		Objects.requireNonNull(type, "the interpreter type cannot be null");
		Objects.requireNonNull(converter, "the converter cannot be null");

	}
}