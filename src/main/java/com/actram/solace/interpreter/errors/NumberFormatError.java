package com.actram.solace.interpreter.errors;

import com.actram.solace.interpreter.InterpreterError;

/**
 * An error representing a failed conversion of a {@link String} to a
 * {@link Number} (e.g. {@code int} or {@code double}).
 * <p>
 * This error is most commonly caused by the {@link String} containing one or
 * more non-numeric characters.
 *
 * @author Peter André Johansen
 */
public class NumberFormatError implements InterpreterError {
}