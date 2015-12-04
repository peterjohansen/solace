package com.actram.solace;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

import com.actram.solace.interpreter.InputInterpreter;
import com.actram.solace.ui.DefaultWindowUI;
import com.actram.solace.ui.WindowUI;

/**
 * A console is a text-based user interface. Input can be received from the user
 * through a text field and key presses and output can be displayed in a text
 * area.
 * <p>
 * The console receives text input by polling. This means that the user cannot
 * enter input until the program requests it and execution will halt until
 * acceptable input has been submitted.
 * <p>
 * An example of this behavior:
 * 
 * <pre>
 * Console console = new Console();
 * console.println("Enter your name:");
 * String name = console.getString();
 * console.println("Hello, " + name + ".");
 * </pre>
 *
 * @author Peter André Johansen
 */
public class Console extends Window implements InputInterpreter {
	public static final String DEFAULT_TITLE = "Console";

	/**
	 * Used to pause the thread when {@link #nextString()} is called.
	 */
	private CountDownLatch latch;

	private final Queue<String> outputQueue = new PriorityQueue<>();
	private int printSpeed;
	private boolean printingText;

	private String lastInputSubmitted;
	private boolean awatingInteraction;
	private char lastKeyPressed;

	/**
	 * Creates a new console window.
	 * <p>
	 * The window will use the {@link DefaultWindowUI}.
	 */
	public Console() {
		this(DEFAULT_TITLE);
	}

	/**
	 * Creates a new console window.
	 * <p>
	 * The window will use the {@link DefaultWindowUI}.
	 * 
	 * @param title the console's title
	 */
	public Console(String title) {
		this(new DefaultWindowUI(), title);
	}

	/**
	 * Creates a new console window.
	 * <p>
	 * Unless you need to provide your own user interface, use this constructor
	 * instead: {@link #Console()}
	 * 
	 * @param windowUI the window user interface, which also controls whether
	 *            the console is visible initially
	 */
	public Console(WindowUI windowUI) {
		this(windowUI, DEFAULT_TITLE);
	}

	/**
	 * Creates a new console window.
	 * <p>
	 * Unless you need to provide your own user interface, use one of these
	 * constructors instead: {@link #Console()} or {@link #Console(String)}
	 * 
	 * @param windowUI the window user interface, which also controls whether
	 *            the console is visible initially
	 * @param title the console's title
	 */
	public Console(WindowUI windowUI, String title) {
		super(windowUI);

		setAcceptUserInput(false);
		setTitle(title);

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
	 * @return the last received input, or {@code null} if no input has been
	 *         received
	 */
	public final String getLastInput() {
		return lastInputSubmitted;
	}

	/**
	 * Returns the rate in milliseconds output will be displayed.
	 * <p>
	 * More specifically, the length of the interval in milliseconds between
	 * when each character is displayed in the output area will be returned.
	 * 
	 * @return the display speed
	 */
	public int getPrintSpeed() {
		return printSpeed;
	}

	/**
	 * @return whether the console is waiting for input
	 */
	public final boolean isWaitingForInput() {
		return (latch != null);
	}

	/**
	 * Polls the console for a {@link String}. Execution will stop on this
	 * method call until input has been received.
	 * 
	 * @return the string
	 */
	@Override
	public String nextString() {
		setAcceptUserInput(true);
		pauseThread();
		setAcceptUserInput(false);
		return lastInputSubmitted;
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
		final String str = o.toString();
		if (printSpeed == 0) {
			super.print(str);
		} else {
			if (printingText) {
				outputQueue.add(str);
			} else {
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
								Thread.sleep(printSpeed);
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
	public void processInput(Object input) {
		if (isWaitingForInput()) {
			lastInputSubmitted = input.toString();
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
	public void setPrintSpeed(int speed) {
		if (speed < 0) {
			throw new IllegalArgumentException("the print speed cannot be negative (speed=" + speed + ")");
		}
		this.printSpeed = speed;
	}

	/**
	 * Halts execution for the given period of time (in milliseconds).
	 * <p>
	 * This method uses {@link Thread#sleep(long)} and might therefore not be
	 * exactly accurate.
	 */
	public void waitFor(long ms) {
		if (ms < 0) {
			throw new IllegalArgumentException("the wait time cannot be negative (ms=" + ms + ")");
		}

		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void waitForInteraction() {
		awatingInteraction = true;
		pauseThread();
		awatingInteraction = false;
	}
}