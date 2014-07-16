package com.sakratt.gus;

import java.util.concurrent.CountDownLatch;

/**
 * A console is a {@link Window} that only allows the user to enter text when requested by the
 * program.
 * <p>
 * Also contains useful methods for getting user input, a few of which are: <blockquote>
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

	private static final String DEFAULT_TITLE = "Console";
	private static final String AWAITING_INTERACTION_TEXT = "Press any key to continue...";

	private static final int DEFAULT_SPEED = 0;

	private static final String[] YES_STRINGS = { "yes", "y", "1", "true" };
	private static final String[] NO_STRINGS = { "no", "n", "0", "false" };

	/**
	 * The last input entered, or {@code null} if no input has been entered.
	 */
	private String lastInput;

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
	 * Polls the console for a {@code boolean} using common English words for yes ({@code true}) or
	 * no ({@code false}) answers.
	 * 
	 * @param error the message to print if the input is not recognized
	 * @return {@code true} if the user entered a yes answer
	 */
	public boolean getBoolean(String error) {
		return getBoolean(YES_STRINGS, NO_STRINGS, error);
	}

	/**
	 * Polls the console for a {@code boolean} using the given strings for determining whether the
	 * input was a yes ({@code true}) or no ({@code false}) answer.
	 * 
	 * @param yesAnswers the yes answer strings
	 * @param noAnswers the no answer strings
	 * @param error the message to print if the input is not recognized
	 * @return {@code true} if the user entered a yes answer
	 */
	public boolean getBoolean(String[] yesAnswers, String[] noAnswers, String error) {
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
	 * Polls the console for a non-escaping {@code char} until a valid one has been given.
	 * 
	 * @param min the lowest acceptable {@code char}
	 * @param max the highest acceptable {@code char}
	 * @param error the message to print if the input is not a {@code char} or in range
	 * @return the {@code char}
	 */
	public final char getChar(char min, char max, String error) {
		char c = 0;
		while (true) {
			c = getChar(error);
			if (c < min || c > max) println(error);
			else break;
		}
		return c;
	}

	/**
	 * Polls the console for a non-escaping {@code char} until a valid one has been given.
	 * 
	 * @param error the message to print if the input is not a non-escaping {@code char}
	 * @return the {@code char}
	 */
	public final char getChar(String error) {
		String str = null;
		while (true) {
			str = getString();
			if (str.length() == 1) break;
			else println(error);
		}
		return str.charAt(0);
	}

	/**
	 * @return the length of the interval in milliseconds between when each character is displayed
	 *         in the output area
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
	 * @param error the message to print if the input is not a {@code double} or in range
	 * @return the {@code double}
	 */
	public final double getDouble(double min, double max, String error) {
		double n = -1;
		while (true) {
			n = getDouble(error);
			if (n < min || n > max) println(error);
			else break;
		}
		return n;
	}

	/**
	 * Polls the console for a {@code double} until a valid one has been given.
	 * 
	 * @param error the message to print if the input is not a {@code double}
	 * @return the {@code double}
	 */
	public final double getDouble(String error) {
		double n = -1;
		while (true) {
			try {
				n = getDouble();
				break;
			} catch (Exception e) {
				println(error);
			}
		}
		return n;
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
	 * @param error the message to print if the input is not an {@code int} or in range
	 * @return the {@code int}
	 */
	public final int getInt(int min, int max, String error) {
		int n = -1;
		while (true) {
			n = getInt(error);
			if (n < min || n > max) println(error);
			else break;
		}
		return n;
	}

	/**
	 * Polls the console for an {@code int} until a valid one has been given.
	 * 
	 * @param error the message to print if the input is not an {@code int}
	 * @return the {@code int}
	 */
	public final int getInt(String error) {
		int n = -1;
		while (true) {
			try {
				n = getInt();
				break;
			} catch (Exception e) {
				println(error);
			}
		}
		return n;
	}

	/**
	 * @return the last received input, or {@code null} if no input has been received
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
		latch = new CountDownLatch(1);
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return getLastInput();
	}

	/**
	 * Polls the console for a {@code String} until one that matches the given regex.
	 * 
	 * @param regex the regex
	 * @param error the error message to display if the input does not match the regex
	 * @return the {@code String}
	 */
	public final String getString(String regex, String error) {
		String str = null;
		while (true) {
			str = getString();
			if (str.matches(regex)) break;
			else println(error);
		}
		return str;
	}

	/**
	 * @return whether the console is waiting for input
	 */
	public final boolean isWaitingForInput() {
		return (latch != null);
	}

	@Override
	protected final void keyWasPressed(int code) {
		if (awatingInteraction) latch.countDown();
	}

	@Override
	public final void print(Object o) {
		if (speed == 0) super.print(o);
		else {
			String str = o.toString();
			int length = str.length();
			int index = 0;
			try {

				// Attempt to print a character and sleep
				for (; index < length; index++) {
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
			latch.countDown();
			setAcceptUserInput(false);
		}
	}

	/**
	 * Sets the length of the interval in milliseconds between when each character is displayed in
	 * the output area. A negative speed will be set to {@code 0}, which will instantly display the
	 * characters in the output area.
	 * 
	 * @param speed the speed
	 */
	public void setDisplaySpeed(int speed) {
		this.speed = (speed < 0 ? 0 : speed);
	}

	/**
	 * Polls the console until any key is pressed and notifies the user.
	 */
	public final void waitForInteraction() {
		waitForInteraction(AWAITING_INTERACTION_TEXT);
	}

	/**
	 * Prints the given string and then polls the console until any key is pressed. Does not print
	 * anything if the given text is {@code null}.
	 * 
	 * @param text the text
	 */
	public final void waitForInteraction(String text) {
		if (text != null) println(text);
		awatingInteraction = true;
		getString();
		awatingInteraction = false;
		clearInput();
	}
}