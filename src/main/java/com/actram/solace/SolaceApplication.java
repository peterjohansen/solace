package com.actram.solace;

import java.util.Objects;

/**
 * Contains static methods for launching {@link ConsoleProgram}s without having
 * to create a {@link Console}.
 *
 * @author Peter André Johansen
 */
public class SolaceApplication {

	/**
	 * Runtime exception for failed runtime instantiation of a console program.
	 *
	 * @author Peter André Johansen
	 */
	private static class SolaceApplicationException extends RuntimeException {
		private static final long serialVersionUID = -5173269888034861502L;

		public SolaceApplicationException(String message) {
			super(message);
		}
	}

	private static Console console;

	/**
	 * Launches the {@link ConsoleProgram}-implementation of the given class.
	 */
	public static void launch(Class<? extends ConsoleProgram> cls) {
		Objects.requireNonNull(cls, "the console program class cannot be null");
		try {
			launch(cls.newInstance());
		} catch (IllegalAccessException | InstantiationException e) {
			throw new SolaceApplicationException("the " + cls.getSimpleName() + ".class console program could not be launched");
		}
	}

	/**
	 * Launches the given program.
	 */
	public static void launch(ConsoleProgram program) {
		Objects.requireNonNull(program, "the program cannot be null");

		if (console == null) {
			console = Console.createNew();
		}
		program.run(console);
	}

	/**
	 * Sets the console that the launch methods will use. If the specified
	 * console is {@code null}, a new one will be created.
	 */
	public static void setConsole(Console console) {
		SolaceApplication.console = console;
	}

	/** Prevent instantiation. */
	private SolaceApplication() {}
}