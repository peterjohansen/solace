package com.actram.solace.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.DefaultCaret;

import com.actram.solace.ui.event.CloseListener;
import com.actram.solace.ui.event.InputListener;
import com.actram.solace.ui.event.KeyListener;

/**
 * A text-based user interface that can display and receive text input.
 * 
 * @author Peter André Johansen
 */
public class DefaultWindowUI implements WindowUI {

	public static final int DEFAULT_WIDTH = 600;
	public static final int DEFAULT_HEIGHT = 300;
	public static final Font DEFAULT_FONT = Font.decode("monospace");

	private final JFrame frame;
	private final JTextArea outputArea;
	private final JTextField inputArea;

	private CloseListener closeListener;
	private KeyListener keyListener;
	private InputListener inputListener;

	/**
	 * Creates a new visible window user interface.
	 */
	public DefaultWindowUI() {
		this(true);
	}

	/**
	 * Creates a new window user interface with the given initial visibility.
	 */
	public DefaultWindowUI(boolean visible) {
		
		// Attempt to set the look and feel to the system's
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			// e.printStackTrace();
		}

		// Create output area
		this.outputArea = new JTextArea();
		outputArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		outputArea.setEditable(false);
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

		// Create input area
		this.inputArea = new JTextField();
		MatteBorder outsideBorder = new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY);
		EmptyBorder insideBorder = new EmptyBorder(5, 5, 5, 5);
		inputArea.setBorder(new CompoundBorder(outsideBorder, insideBorder));
		inputArea.addActionListener(evt -> {
			String input = inputArea.getText();
			if (inputListener != null && !input.isEmpty()) {
				inputListener.receiveInput(input);
			}
			clearInput();
		});
		inputArea.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.emptySet());

		// Create the frame
		this.frame = new JFrame();
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

		// Add the key listener to the window
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
		setOutputFocusable(false);
		setOutputFont(DEFAULT_FONT);
		setAcceptUserInput(true);
		setInputFont(DEFAULT_FONT);
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setVisible(visible);

	}

	@Override
	public void clearInput() {
		setCurrentInput(null);
	}

	@Override
	public void clearOutput() {
		setCurrentOutput(null);
	}

	@Override
	public void close() {
		frame.dispose();
	}

	@Override
	public String getCurrentInput() {
		return inputArea.getText();
	}

	@Override
	public String getCurrentOutput() {
		return outputArea.getText();
	}

	@Override
	public int getHeight() {
		return frame.getHeight();
	}

	@Override
	public String getTitle() {
		return frame.getTitle();
	}

	@Override
	public int getWidth() {
		return frame.getWidth();
	}

	@Override
	public int getX() {
		return frame.getX();
	}

	@Override
	public int getY() {
		return frame.getY();
	}

	@Override
	public boolean isAcceptingUserInput() {
		return inputArea.isEnabled();
	}

	@Override
	public boolean isInputHidden() {
		return !inputArea.isVisible();
	}

	@Override
	public void print(Object obj) {
		outputArea.append(obj.toString());
	}

	@Override
	public void selectInputText() {
		inputArea.selectAll();
	}

	@Override
	public void selectOuputText() {
		outputArea.selectAll();
	}

	@Override
	public void setAcceptUserInput(boolean acceptInput) {
		inputArea.setEnabled(acceptInput);
		if (acceptInput) {
			inputArea.requestFocusInWindow();
		} else {
			frame.requestFocus();
		}
	}

	@Override
	public void setCloseListener(CloseListener closeListener) {
		this.closeListener = closeListener;
	}

	@Override
	public void setCurrentInput(Object obj) {
		inputArea.setText(obj != null ? obj.toString() : "");
	}

	@Override
	public void setCurrentOutput(Object obj) {
		outputArea.setText(obj != null ? obj.toString() : "");
	}

	@Override
	public void setIconImage(Image image) {
		frame.setIconImage(image);
	}

	@Override
	public void setInputFont(Font font) {
		inputArea.setFont(font);
	}

	@Override
	public void setInputHidden(boolean hidden) {
		inputArea.setVisible(hidden);
	}

	@Override
	public void setInputListener(InputListener inputListener) {
		this.inputListener = inputListener;
	}

	@Override
	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}

	@Override
	public void setLocation(int x, int y) {
		frame.setLocation(x, y);
	}

	@Override
	public void setOutputFocusable(boolean focusable) {
		outputArea.setFocusable(focusable);
	}

	@Override
	public void setOutputFont(Font font) {
		outputArea.setFont(font);
	}

	@Override
	public void setSize(int width, int height) {
		if (width < 0) {
			throw new IllegalArgumentException("the width cannot be negative");
		}
		if (height < 0) {
			throw new IllegalArgumentException("the height cannot be negative");
		}

		frame.setPreferredSize(new Dimension(width, height));
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	@Override
	public void setTitle(String title) {
		frame.setTitle(title);
	}

	@Override
	public void setVisible(boolean visible) {
		if (frame.isVisible() != visible) {
			frame.setVisible(visible);
		}
	}
}