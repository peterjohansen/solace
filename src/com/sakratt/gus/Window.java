package com.sakratt.gus;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

/**
 * A window is a graphical user interface consisting of an input and output area, allowing the user
 * to enter text and the program to display text. By default the input submitted will only be echoed
 * back, but this can be changed by overriding the <code>receivedInput(String input)</code> method.
 * <p>
 * For a {@link Window} that uses polling input, see the {@link Console} class.
 * 
 * @author Peter André Johansen
 * 
 */
public class Window extends OutputFrame {

	private static final String DEFAULT_TITLE = "Window";

	JTextField inputArea;

	/**
	 * Creates a new window.
	 */
	public Window() {
		this(DEFAULT_TITLE);
	}

	/**
	 * Creates a new window with the given width and height.
	 * 
	 * @param width the width
	 * @param height the height
	 */
	public Window(int width, int height) {
		this(width, height, DEFAULT_TITLE);
	}

	/**
	 * Creates a new window with the given width, height and title.
	 * 
	 * @param width the width
	 * @param height the height
	 * @param title the title
	 */
	public Window(int width, int height, String title) {
		super(width, height, title);

		// Input area
		inputArea = new JTextField();
		MatteBorder outsideBorder = new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY);
		EmptyBorder insideBorder = new EmptyBorder(5, 5, 5, 5);
		inputArea.setBorder(new CompoundBorder(outsideBorder, insideBorder));
		inputArea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				triedToSubmit();
			}
		});
		inputArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				keyWasPressed(evt.getKeyCode());
			}
		});

		// Frame
		frame.add(inputArea, BorderLayout.SOUTH);

		// Initialize
		setAcceptUserInput(true);
	}

	/**
	 * Creates a new window with the given title.
	 * 
	 * @param title the title
	 */
	public Window(String title) {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT, title);
	}

	/**
	 * Clears the input area.
	 */
	protected void clearInput() {
		inputArea.setText(null);
	}

	/**
	 * @return true if the window is accepting user input
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
	 * This method gives the window input, either programmatically or from the user.
	 * <p>
	 * By default prints the input to the output area.
	 * 
	 * @param input the input
	 */
	public void receiveInput(String input) {
		println(input);
	}

	/**
	 * Passes on the text in the input area to the {@link #receiveInput(String)} method and clears
	 * the input area.
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
	 * Blocking input will disable the input area.
	 * 
	 * @param accept whether to accept user input or not
	 */
	public final void setAcceptUserInput(boolean accept) {
		inputArea.setEnabled(accept);
		inputArea.requestFocusInWindow();
	}

	/**
	 * @param image the new frame icon image
	 */
	public final void setIconImage(Image image) {
		frame.setIconImage(image);
	}

	/**
	 * This method is called when the user tries to submit input.
	 */
	private void triedToSubmit() {
		sendTextFromInputArea();
	}
}