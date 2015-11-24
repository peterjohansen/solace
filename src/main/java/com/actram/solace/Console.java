package com.actram.solace;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

import com.actram.solace.interpreter.InterpreterError;
import com.actram.solace.ui.WindowUI;

/**
 *
 *
 * @author Peter André Johansen
 */
public class Console extends Window {
	public static enum Error implements InterpreterError {
		NOT_A_NUMBER, NOT_AN_INT,
	}

	public static Console createNew() {
		return new Console(new WindowUI());
	}

	/**
	 * Used to pause the thread when {@link #getString()} is called.
	 */
	private CountDownLatch latch;

	/**
	 * Whether the console is waiting for user interaction.
	 */
	private boolean awatingInteraction;

	/**
	 * The speed in milliseconds to display each character in the output area.
	 */
	private int speed;

	/**
	 * Whether the console is currently printing out text. Necessary if the text
	 * is not displayed instantly.
	 */
	private boolean printingText;

	private final Queue<Object> outputQueue = new PriorityQueue<>();
	private String lastInputSubmitted;
	private char lastKeyPressed;

	public Console(WindowUI windowUI) {
		super(windowUI);

		setAcceptUserInput(false);

		windowUI.setKeyListener((code) -> {
			this.lastKeyPressed = (char) code;
			if (awatingInteraction) resumeThread();
		});
	}

	@Override
	public void close() {
		super.close();
		System.exit(0); // TODO Close this console's threads instead
	}

	/**
	 * Returns the rate in milliseconds output will be displayed.
	 * <p>
	 * More specifically, the length of the interval in milliseconds between
	 * when each character is displayed in the output area will be returned.
	 * 
	 * @return the display speed
	 */
	public int getDisplaySpeed() {
		return speed;
	}

	/**
	 * @return the last received input, or {@code null} if no input has been
	 *         received
	 */
	public final String getLastInput() {
		return lastInputSubmitted;
	}

	/**
	 * Polls the console for a {@link String}. Execution will stop on this
	 * method call until input has been received.
	 * 
	 * @return the string
	 */
	public String getString() {
		setAcceptUserInput(true);
		pauseThread();
		setAcceptUserInput(false);
		return lastInputSubmitted;
	}

	/**
	 * @return whether the console is waiting for input
	 */
	public final boolean isWaitingForInput() {
		return (latch != null);
	}

	/**
	 * Pauses the thread until {@link #resumeThread()} is called.
	 */
	private void pauseThread() {
		if (latch != null) {
			throw new AssertionError("countdown latch should be null");
		}

		latch = new CountDownLatch(1);
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public final void print(Object o) {
		if (speed == 0) {
			super.print(o);
		} else {
			if (printingText) {
				outputQueue.add(o);
			} else {
				final String str = o.toString();
				printingText = true;
				boolean previousAllowInput = isAcceptingUserInput();
				new Thread(new Runnable() {
					@Override
					public void run() {
						int index = 0;
						try {

							// Attempt to print a character and sleep
							for (; index < str.length(); index++) {
								Console.super.print(str.charAt(index));
								Thread.sleep(speed);
							}

						} catch (InterruptedException e) {
							e.printStackTrace();

							// Print out remaining characters if any
							Console.super.print(str.substring(index));

						} finally {
							printingText = false;
							if (!outputQueue.isEmpty()) {
								print(outputQueue.poll());
							}
							setAcceptUserInput(previousAllowInput);
							resumeThread();
						}
					}
				}).start();
				setAcceptUserInput(false);
				pauseThread();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Does nothing if input has not been requested.
	 */
	@Override
	public void processInput(String input) {
		if (isWaitingForInput()) {
			lastInputSubmitted = input;
			resumeThread();
		}
	}

	/**
	 * Resumes the thread.
	 */
	private void resumeThread() {
		if (latch == null) {
			throw new AssertionError("countdown latch should not be null");
		}

		latch.countDown();
		latch = null;
	}

	/**
	 * Sets the rate in milliseconds output will be displayed.
	 * <p>
	 * More specifically, the length of the interval in milliseconds between
	 * when each character is displayed in the output area will be set.
	 * 
	 * @param speed the display speed
	 */
	public void setDisplaySpeed(int speed) {
		if (speed < 0) {
			throw new IllegalArgumentException("the display speed cannot be negative (speed=" + speed + ")");
		}
		this.speed = speed;
	}
}