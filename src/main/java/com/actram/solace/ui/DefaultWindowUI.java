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

import com.actram.solace.ui.event.CloseListener;
import com.actram.solace.ui.event.InputListener;
import com.actram.solace.ui.event.KeyListener;

/**
 * A graphical user interface that can display text and receive input.
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
			clearInputText();
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
		setOutputFocusable(false);
		setOutputFont(DEFAULT_FONT);
		setInputEnabled(true);
		setInputFont(DEFAULT_FONT);
		setFrameSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setFrameVisible(visible);

	}

	@Override
	public void appendOutputText(String text) {
		outputArea.append(text);
	}

	@Override
	public void clearInputText() {
		setInputText(null);
	}

	@Override
	public void disposeOfFrame() {
		frame.dispose();
	}

	@Override
	public Point getFrameLocation() {
		return frame.getLocation();
	}

	@Override
	public Dimension getFrameSize() {
		return frame.getSize();
	}

	@Override
	public String getFrameTitle() {
		return frame.getTitle();
	}

	@Override
	public String getInputText() {
		return inputArea.getText();
	}

	@Override
	public String getOutputText() {
		return outputArea.getText();
	}
	
	@Override
	public boolean isFrameVisible() {
		return frame.isVisible();
	}
	
	@Override
	public boolean isInputEnabled() {
		return inputArea.isEnabled();
	}

	@Override
	public boolean isInputHidden() {
		return !inputArea.isVisible();
	}

	@Override
	public void selectInput() {
		inputArea.selectAll();
	}

	@Override
	public void selectOutput() {
		outputArea.selectAll();
	}

	@Override
	public void setCloseListener(CloseListener closeListener) {
		this.closeListener = closeListener;
	}

	@Override
	public void setFrameIconImage(Image image) {
		frame.setIconImage(image);
	}

	@Override
	public void setFrameLocation(int x, int y) {
		frame.setLocation(x, y);
	}

	@Override
	public void setFrameSize(int width, int height) {
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
	public void setFrameTitle(String title) {
		frame.setTitle(title);
	}

	@Override
	public void setFrameVisible(boolean visible) {
		if (frame.isVisible() != visible) {
			frame.setVisible(visible);
		}		
	}

	@Override
	public void setInputEnabled(boolean enabled) {
		inputArea.setEnabled(enabled);
		if (enabled) {
			inputArea.requestFocusInWindow();
		} else {
			frame.requestFocus();
		}
	}

	@Override
	public void setInputFont(Font font) {
		inputArea.setFont(font);
	}

	@Override
	public void setInputHidden(boolean hidden) {
		this.inputArea.setVisible(!hidden);
	}

	@Override
	public void setInputListener(InputListener inputListener) {
		this.inputListener = inputListener;
	}

	@Override
	public void setInputText(String text) {
		inputArea.setText(text);
	}

	@Override
	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
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
	public void setOutputText(String text) {
		outputArea.setText(text);
	}
}