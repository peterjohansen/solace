package com.actram.solace.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.DefaultCaret;

import com.actram.solace.ui.listeners.CloseListener;
import com.actram.solace.ui.listeners.InputListener;
import com.actram.solace.ui.listeners.KeyListener;

/**
 * A graphical user interface that can display text and receive input.
 * 
 * @author Peter André Johansen
 */
public class WindowUI {

	public static final String DEFAULT_TITLE = "Window";

	public static final int DEFAULT_WIDTH = 600;
	public static final int DEFAULT_HEIGHT = 300;

	public static final Font DEFAULT_FONT = Font.decode("monospace");

	private final JFrame frame;
	private final JTextArea outputArea;
	final JTextField inputArea;

	private CloseListener closeListener;
	private KeyListener keyListener;
	private InputListener inputListener;

	public WindowUI() {

		// Create output area
		outputArea = new JTextArea();
		outputArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		outputArea.setEditable(false);
		outputArea.setFont(DEFAULT_FONT);
		outputArea.setLineWrap(true);
		outputArea.setMargin(null);
		outputArea.setWrapStyleWord(true);
		DefaultCaret caret = (DefaultCaret) outputArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		// Create output area scroll pane
		final JScrollPane outputAreaScrollPane = new JScrollPane();
		outputAreaScrollPane.setBorder(null);
		outputAreaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		outputAreaScrollPane.setViewportView(outputArea);

		// Input area
		inputArea = new JTextField();
		MatteBorder outsideBorder = new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY);
		EmptyBorder insideBorder = new EmptyBorder(5, 5, 5, 5);
		inputArea.setBorder(new CompoundBorder(outsideBorder, insideBorder));
		inputArea.addActionListener(evt -> {
			String input = inputArea.getText();
			if (inputListener != null && !input.isEmpty()) {
				inputListener.receiveInput(input);
			}
			clearInputText();
		});
		inputArea.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.emptySet());
		inputArea.setFont(DEFAULT_FONT);

		// Create the frame
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				if (closeListener != null) {
					closeListener.close();
				}
			}
		});
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(outputAreaScrollPane, BorderLayout.CENTER);
		frame.add(inputArea, BorderLayout.SOUTH);

		// Add key listener to the window
		java.awt.event.KeyListener uiKeyListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if (keyListener != null) {
					keyListener.keyPressed(evt.getKeyCode());
				}
			}
		};
		inputArea.addKeyListener(uiKeyListener);
		frame.addKeyListener(uiKeyListener); // Necessary for when the input
												// area is disabled

		// Initialize the window
		setFrameSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setFrameTitle(DEFAULT_TITLE);
		setInputEnabled(true);
		setOutputFocusable(false);
		frame.setVisible(true);

	}

	/**
	 * @param text the text to append to the output area
	 */
	public void appendOutputText(String text) {
		outputArea.append(text);
	}

	/**
	 * Clears the input text.
	 */
	public void clearInputText() {
		setInputText(null);
	}

	/**
	 * Disposes of the frame.
	 */
	public void disposeOfFrame() {
		frame.dispose();
	}

	/**
	 * @return the frame's location
	 */
	public Point getFrameLocation() {
		return frame.getLocation();
	}

	/**
	 * @return the frame's size
	 */
	public Dimension getFrameSize() {
		return frame.getSize();
	}

	/**
	 * @return the frame's title
	 */
	public String getFrameTitle() {
		return frame.getTitle();
	}

	/**
	 * @return the text currently in the input area
	 */
	public String getInputText() {
		return inputArea.getText();
	}

	/**
	 * @return the text currently in the output area
	 */
	public String getOutputText() {
		return outputArea.getText();
	}

	/**
	 * @return whether the input area is enabled
	 */
	public boolean isInputEnabled() {
		return inputArea.isEnabled();
	}

	/**
	 * @param closeListener the window's new close listener
	 */
	public void setCloseListener(CloseListener closeListener) {
		this.closeListener = closeListener;
	}

	/**
	 * @param image the frame's new icon image
	 */
	public void setFrameIconImage(Image image) {
		frame.setIconImage(image);
	}

	/**
	 * @param x the frame's new x-coordinate
	 * @param y the frame's new y-coordinate
	 */
	public void setFrameLocation(int x, int y) {
		frame.setLocation(x, y);
	}

	/**
	 * @param width the frame's new width
	 * @param height the frame's new height
	 */
	public void setFrameSize(int width, int height) {
		frame.setPreferredSize(new Dimension(width, height));
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	/**
	 * @param title the frame's new title
	 */
	public void setFrameTitle(String title) {
		frame.setTitle(title);
	}

	/**
	 * @param enabled whether to enable the input area
	 */
	public void setInputEnabled(boolean enabled) {
		inputArea.setEnabled(enabled);
		if (enabled) {
			inputArea.requestFocusInWindow();
		} else {
			frame.requestFocus();
		}
	}

	/**
	 * @param inputListener the window's new input listener
	 */
	public void setInputListener(InputListener inputListener) {
		this.inputListener = inputListener;
	}

	/**
	 * @param text the text to display in the input area
	 */
	public void setInputText(String text) {
		inputArea.setText(text);
	}

	/**
	 * @param keyListener the window's new key listener
	 */
	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}

	/**
	 * @param focusable whether the output area should be focusable
	 */
	public void setOutputFocusable(boolean focusable) {
		outputArea.setFocusable(focusable);
	}

	/**
	 * @param text the text to display in the output area
	 */
	public void setOutputText(String text) {
		outputArea.setText(text);
	}
}