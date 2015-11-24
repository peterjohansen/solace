package com.actram.solace.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;

import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import com.actram.solace.InputReceiver;

/**
 * A window is a graphical user interface consisting of an input and output
 * area, allowing the user to enter text and the program to display text.
 * <p>
 * By default the input submitted will only be echoed back, but this can be
 * changed by overriding the {@link #receiveInput(String)}-method.
 * <p>
 * For a {@link WindowUI} that uses polling input, see the {@link ConsoleUI} class.
 * 
 * @author Peter André Johansen
 * 
 */
public class WindowUI extends OutputFrameUI implements InputReceiver {

	private static final String DEFAULT_TITLE = "Window";

	final JTextField inputArea;

	/**
	 * Creates a new window.
	 */
	public WindowUI() {
		this(DEFAULT_TITLE);
	}

	/**
	 * @param width the width of the window
	 * @param height the height of the window
	 */
	public WindowUI(int width, int height) {
		this(width, height, DEFAULT_TITLE);
	}

	/**
	 * @param width the width of the window
	 * @param height the height of the window
	 * @param title the title of the window
	 */
	public WindowUI(int width, int height, String title) {
		super(width, height, title);

		// Input area
		inputArea = new JTextField();
		MatteBorder outsideBorder = new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY);
		EmptyBorder insideBorder = new EmptyBorder(5, 5, 5, 5);
		inputArea.setBorder(new CompoundBorder(outsideBorder, insideBorder));
		inputArea.addActionListener(evt -> sendTextFromInputArea());
		inputArea.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.emptySet());

		// Add key listener to the window
		KeyListener keyListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				internalKeyWasPressed(evt.getKeyCode());
			}
		};
		inputArea.addKeyListener(keyListener);
		frame.addKeyListener(keyListener); // Necessary for when the
											// input area is disabled

		// Frame
		frame.add(inputArea, BorderLayout.SOUTH);
		frame.revalidate();

		// Initialize
		setAcceptUserInput(true);
		setOutputFocusable(false);
	}

	/**
	 * @param title the title of the window
	 */
	public WindowUI(String title) {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT, title);
	}

	@Override
	public void clearInput() {
		inputArea.setText(null);
	}

	@Override
	public String getCurrentInput() {
		return inputArea.getText();
	}

	/**
	 * Internally used for key presses.
	 * 
	 * @param code the key code
	 */
	void internalKeyWasPressed(int code) {
		keyWasPressed(code);
	}

	/**
	 * @return whether the window is accepting user input
	 */
	public final boolean isAcceptingUserInput() {
		return inputArea.isEnabled();
	}

	/**
	 * This method is called when the user presses a key in the input area.
	 * 
	 * @param code the key code
	 */
	protected void keyWasPressed(int code) {}

	/**
	 * This method feeds the window input, either programmatically or from the
	 * user.
	 * 
	 * @param input the input
	 */
	public void receiveInput(String input) {
		println(input);
	}

	/**
	 * Selects the text in the input area.
	 */
	public void selectInput() {
		inputArea.selectAll();
	}

	/**
	 * Passes on the text in the input area to the {@link #receiveInput(String)}
	 * method and clears the input area.
	 */
	private void sendTextFromInputArea() {
		String text = inputArea.getText();
		int len = text.length();
		if (len != 0) {
			receiveInput(text);
			clearInput();
		}
	}

	/**
	 * The input area will be disabled if input is not accepted.
	 * 
	 * @param accept whether to accept user input
	 */
	public final void setAcceptUserInput(boolean accept) {
		inputArea.setEnabled(accept);
		if (accept) inputArea.requestFocusInWindow();
		else frame.requestFocus();
	}

	@Override
	public void setCurrentInput(Object obj) {
		inputArea.setText(obj.toString());
	}
}