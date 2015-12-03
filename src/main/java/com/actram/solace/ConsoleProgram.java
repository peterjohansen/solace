package com.actram.solace;

/**
 * Interface for encapsulating a program that uses a {@link Console} in an
 * object.
 * <p>
 * Use the static methods in {@link SolaceLauncher} to run implementations of
 * this interface.
 *
 * @author Peter André Johansen
 */
public interface ConsoleProgram {

	/**
	 * Executes this program using the given console.
	 * 
	 * @throws NullPointerException if the specified console is {@code null}
	 */
	public void run(Console console);

}