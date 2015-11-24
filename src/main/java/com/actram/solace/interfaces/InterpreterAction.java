package com.actram.solace.interfaces;

import com.actram.solace.interpreter.InputInterpreter;

/**
 * An action performed by an interpreter.
 *
 *
 * @author Peter André Johansen
 */
public interface InterpreterAction {

	/**
	 * Performs the action so that the given interpreter was the performer.
	 */
	public void perform(InputInterpreter interpreter);

}