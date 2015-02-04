package com.sakratt.gus;

import java.util.concurrent.CountDownLatch;

/**
 * A console is a {@link Window} that only allows the user to enter text when
 * requested by the program.
 * <p>
 * Also contains useful methods for getting user input, a few of which are:
 * <blockquote>
 * <ul>
 * <li>{@link #getBoolean(String)}</li>
 * <li>{@link #getChar(String)}</li>
 * <li>{@link #getDouble()}</li>
 * <li>{@link #getInt()}</li>
 * <li>{@link #getString()}</li>
 * </ul>
 * </blockquote>
 * 
 * @author Peter André Johansen
 * 
 */
public class Console extends Window {

	/** Default prompt for the {@link #waitForKey()}-methods. */
	public static final String DEFAULT_POLL_PROMPT = "Press any key to continue...";
	
	private static final String DEFAULT_TITLE = "Console";
	private static final int DEFAULT_SPEED = 0;

	private static final String[] YES_STRINGS = { "yes", "y", "true", "t", "1", };
	private static final String[] NO_STRINGS = { "no", "n", "false", "f", "0" };

	/**
	 * The last input entered, or {@code null} if no input has been entered.
	 */
	private String lastInput;

	/**
	 * The character representing the last key pressed.
	 */
	private char lastKey;

	/**
	 * Used to pause the thread when {@link #getString()} is called.
	 */
	private CountDownLatch latch;

	/**
	 * Whether the console is waiting for user interaction or not.
	 */
	private boolean awatingInteraction;

	/**
	 * The speed in milliseconds to display each character in the output area.
	 */
	private int speed;

	/**
	 * Creates a new console.
	 */
	public Console() {
		this(DEFAULT_TITLE);
	}

	/**
	 * @param width the width of the console
	 * @param height the height of the console
	 */
	public Console(int width, int height) {
		this(width, height, DEFAULT_TITLE);
	}

	/**
	 * @param width the width of the console
	 * @param height the height of the console
	 * @param title the title of the console
	 */
	public Console(int width, int height, String title) {
		super(width, height, title);

		// Initialize
		setAcceptUserInput(false);
		setDisplaySpeed(DEFAULT_SPEED);

	}

	/**
	 * @param title the title of the console
	 */
	public Console(String title) {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT, title);
	}

	@Override
	public void close() {
		super.close();
		System.exit(0);
	}

	/**
	 * Polls the console for a {@code boolean} using common English words for
	 * yes ({@code true}) or no ({@code false}) answers.
	 * 
	 * @param error the message to print if the input is not recognized
	 * @return {@code true} if the user entered a yes answer
	 */
	public boolean getBoolean(String error) {
		return getBoolean(YES_STRINGS, NO_STRINGS, error);
	}

	/**
	 * Polls the console for a {@code boolean} using the given strings for
	 * determining whether the input was a yes ({@code true}) or no (
	 * {@code false}) answer. The case in the given strings will be ignored.
	 * 
	 * @param yesAnswers the yes answer strings
	 * @param noAnswers the no answer strings
	 * @param error the message to print if the input is not recognized
	 * @return {@code true} if the user entered a yes answer
	 */
	public boolean getBoolean(String[] yesAnswers, String[] noAnswers, String error) {
		if (yesAnswers == null) {
			throw new NullPointerException("array of yes-answers cannot be null");
		}
		if (noAnswers == null) {
			throw new NullPointerException("array of no-answers cannot be null");
		}
		while (true) {
			String answer = getString();

			// Check if answer is yes
			for (String yes : yesAnswers)
				if (yes.equalsIgnoreCase(answer)) return true;

			// Check if answer is no
			for (String no : noAnswers)
				if (no.equalsIgnoreCase(answer)) return false;

			// Notify user of invalid answer
			println(error);

		}
	}

	/**
	 * Polls the console for a non-escaping {@code char} until a valid one has
	 * been given.
	 * 
	 * @param min the lowest acceptable {@code char}
	 * @param max the highest acceptable {@code char}
	 * @param error the message to print if the input is not a {@code char} or
	 *            in range
	 * @return the {@code char}
	 */
	public final char getChar(char min, char max, String error) {
		if (min < 0) {
			throw new IllegalArgumentException("the lowest acceptable char cannot be less than zero");
		}
		if (max < min) {
			throw new IllegalArgumentException("the highest accetable char cannot be less than the lowest one");
		}
		while (true) {
			char c = getChar(error);
			if (c < min || c > max) println(error);
			else return c;
		}
	}

	/**
	 * Polls the console for a non-escaping {@code char} until a valid one has
	 * been given.
	 * 
	 * @param error the message to print if the input is not a non-escaping
	 *            {@code char}
	 * @return the {@code char}
	 */
	public final char getChar(String error) {
		while (true) {
			String str = getString();
			if (str.length() == 1) return str.charAt(0);
			else println(error);
		}
	}

	/**
	 * @return the length of the interval in milliseconds between when each
	 *         character is displayed in the output area
	 */
	public int getDisplaySpeed() {
		return speed;
	}

	/**
	 * Polls the console for a {@code double}.
	 * 
	 * @return the {@code double}
	 */
	public final double getDouble() {
		return Double.parseDouble(getString());
	}

	/**
	 * Polls the console for a {@code double} until a valid one has been given.
	 * 
	 * @param min the minimum value
	 * @param max the maximum value
	 * @param error the message to print if the input is not a {@code double} or
	 *            in range
	 * @return the {@code double}
	 */
	public final double getDouble(double min, double max, String error) {
		if (min < 0) {
			throw new IllegalArgumentException("the minimum value cannot be less than zero");
		}
		if (max < min) {
			throw new IllegalArgumentException("the maximum value cannot be less than the minimum one");
		}
		while (true) {
			double n = getDouble(error);
			if (n < min || n > max) println(error);
			else return n;
		}
	}

	/**
	 * Polls the console for a {@code double} until a valid one has been given.
	 * 
	 * @param error the message to print if the input is not a {@code double}
	 * @return the {@code double}
	 */
	public final double getDouble(String error) {
		while (true) {
			try {
				return getDouble();
			} catch (Exception e) {
				println(error);
			}
		}
	}

	/**
	 * Polls the console for an {@code int}.
	 * 
	 * @return the {@code int}
	 */
	public final int getInt() {
		return Integer.parseInt(getString());
	}

	/**
	 * Polls the console for an {@code int} until a valid one has been given.
	 * 
	 * @param min the minimum value
	 * @param max the maximum value
	 * @param error the message to print if the input is not an {@code int} or
	 *            in range
	 * @return the {@code int}
	 */
	public final int getInt(int min, int max, String error) {
		if (min < 0) {
			throw new IllegalArgumentException("the minimum value cannot be less than zero");
		}
		if (max < min) {
			throw new IllegalArgumentException("the maximum value cannot be less than the minimum one");
		}
		while (true) {
			int n = getInt(error);
			if (n < min || n > max) println(error);
			else return n;
		}
	}

	/**
	 * Polls the console for an {@code int} until a valid one has been given.
	 * 
	 * @param error the message to print if the input is not an {@code int}
	 * @return the {@code int}
	 */
	public final int getInt(String error) {
		while (true) {
			try {
				return getInt();
			} catch (Exception e) {
				println(error);
			}
		}
	}

	/**
	 * @return the last received input, or {@code null} if no input has been
	 *         received
	 */
	public final String getLastInput() {
		return lastInput;
	}

	/**
	 * Polls the console for a {@code String}.
	 * 
	 * @return the {@code String}
	 */
	public final String getString() {
		setAcceptUserInput(true);
		pause();
		setAcceptUserInput(false);
		return getLastInput();
	}

	/**
	 * Polls the console for a {@code String} until one that matches the given
	 * regex has been given.
	 * 
	 * @param regex the regex
	 * @param error the error message to display if the input does not match the
	 *            regex
	 * @return the {@code String}
	 */
	public final String getString(String regex, String error) {
		if (regex == null) {
			throw new NullPointerException("the regex cannot be null");
		}
		while (true) {
			String str = getString();
			if (str.matches(regex)) return str;
			else println(error);
		}
	}

	/**
	 * @return whether the console is waiting for input
	 */
	public final boolean isWaitingForInput() {
		return (latch != null);
	}

	@Override
	protected final void keyWasPressed(int code) {
		this.lastKey = (char) code;
		if (awatingInteraction) resume();
	}

	/**
	 * Pauses the thread until {@link #resume()} is called.
	 */
	private void pause() {
		latch = new CountDownLatch(1);
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public final void print(Object o) {
		if (speed == 0) super.print(o);
		else {
			String str = o.toString();
			int index = 0;
			try {

				// Attempt to print a character and sleep
				for (; index < str.length(); index++) {
					super.print(str.charAt(index));
					Thread.sleep(speed);
				}

			} catch (InterruptedException e) {
				e.printStackTrace();

				// Print out remaining characters
				super.print(str.substring(index));

			}
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Does nothing if input has not been requested.
	 */
	@Override
	public final void receiveInput(String input) {
		if (isWaitingForInput()) {
			lastInput = input;
			resume();
		}
	}

	/**
	 * Resumes the thread.
	 */
	private void resume() {
		latch.countDown();
	}

	/**
	 * Sets the length of the interval in milliseconds between when each
	 * character is displayed in the output area. A negative speed will be set
	 * to {@code 0}, which will instantly display the characters in the output
	 * area.
	 * 
	 * @param speed the speed
	 */
	public void setDisplaySpeed(int speed) {
		this.speed = (speed < 0 ? 0 : speed);
	}

	/**
	 * Uses {@link #waitForKey(String)} with no prompt.
	 * 
	 * @return the character representing the key that was pressed
	 */
	public final char waitForKey() {
		return waitForKey(null);
	}

	/**
	 * Prints the given string and then polls the console until any key is
	 * pressed. Does not print anything if the given prompt is {@code null}.
	 * 
	 * @param prompt the text to display first
	 * @return the character representing the key that was pressed
	 */
	public final char waitForKey(String prompt) {
		if (prompt != null) println(prompt);
		awatingInteraction = true;
		pause();
		awatingInteraction = false;
		return lastKey;
	}

	/**
	 * Uses {@link #waitForKey(String, char...)} to wait for a single character.
	 * 
	 * @param key the character representing the key that can be pressed
	 * @param error the error message to display if a wrong key was pressed
	 */
	public final void waitForKey(char key, String error) {
		waitForKey(error, key);
	}

	/**
	 * Polls the console until one of the given keys is pressed. If a key not in
	 * the list is pressed, the given error message will be displayed and the
	 * user is able to press a new key.
	 * <p>
	 * Not all characters can be pressed as a key, so use this method with
	 * caution.
	 * 
	 * @param error the error message to display if a wrong key was pressed
	 * @param keys the characters representing the keys that can be pressed
	 * @return the key that was pressed
	 */
	public final char waitForKey(String error, char... keys) {
		boolean done = false;
		while (!done) {
			waitForKey(null);
			for (char key : keys) {
				if (lastKey == key) {
					done = true;
					break;
				}
			}
			if (!done) println(error);
		}
		return lastKey;
	}
}