package com.actram.solace.interpreter.error;

/**
 * An error representing a failed conversion of a {@link String} to a
 * {@code boolean}.
 * <p>
 * This error is most commonly caused by the {@link String} having a value that
 * {@link Boolean#valueOf(String)} does not define.
 *
 * @author Peter André Johansen
 */
public class BooleanFormatError implements InterpreterError {
}