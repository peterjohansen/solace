package com.actram.solace;

import java.util.Objects;

/**
 * Contains static methods for launching {@link ConsoleProgram}s without having
 * to create a {@link Console}.
 *
 * @author Peter André Johansen
 */
public class SolaceApplication {
	private static Console console;

	/**
	 * Launches the {@link ConsoleProgram}-implementation of the given class.
	 */
	public static void launch(Class<? extends ConsoleProgram> cls) {
		Objects.requireNonNull(cls, "the console program class cannot be null");
		try {
			launch(cls.newInstance());
		} catch (Exception e) {
			throw new IllegalArgumentException("could not create a new instance of the class: " + e);
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