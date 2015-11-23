package com.sakratt.gus;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

/**
 * An output frame is a graphical user interface that displays text.
 * 
 * @author Peter André Johansen
 * 
 */
public class OutputFrame {

	private static final String DEFAULT_TITLE = "Output Frame";
	private static final Font OUTPUT_AREA_FONT = new Font("Arial", Font.PLAIN, 14);

	protected static final int DEFAULT_WIDTH = 600;
	protected static final int DEFAULT_HEIGHT = 300;

	final JFrame frame;
	private final JTextArea outputArea;

	/**
	 * Creates a new output frame.
	 */
	public OutputFrame() {
		this(DEFAULT_TITLE);
	}

	/**
	 * @param width the width of the frame
	 * @param height the height of the frame
	 */
	public OutputFrame(int width, int height) {
		this(width, height, DEFAULT_TITLE);
	}

	/**
	 * @param width the width of the frame
	 * @param height the height of the frame
	 * @param title the title of the frame
	 */
	public OutputFrame(int width, int height, String title) {
		if (width < 0) {
			throw new IllegalArgumentException("the width must be greater than zero");
		}
		if (height < 0) {
			throw new IllegalArgumentException("the height must be greater than zero");
		}

		// Output area
		outputArea = new JTextArea();
		outputArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		outputArea.setEditable(false);
		outputArea.setLineWrap(true);
		outputArea.setMargin(null);
		outputArea.setWrapStyleWord(true);
		DefaultCaret caret = (DefaultCaret) outputArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		// Output area scroll pane
		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setViewportView(outputArea);

		// Create the frame
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				close();
			}
		});
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(scrollPane, BorderLayout.CENTER);

		// Initialize
		setOutputFont(OUTPUT_AREA_FONT);
		setSize(width, height);
		setTitle(title);
		frame.setVisible(true);

	}

	/**
	 * @param title the title of the frame
	 */
	public OutputFrame(String title) {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT, title);
	}

	/**
	 * Clears the output area.
	 */
	public final void clearOutput() {
		outputArea.setText(null);
	}

	/**
	 * Closes the window.
	 */
	public void close() {
		frame.dispose();
	}

	/**
	 * @return the text currently in the output area
	 */
	public String getOutputText() {
		return outputArea.getText();
	}

	/**
	 * Prints the given object to the output area.
	 */
	public void print(Object o) {
		outputArea.append(o.toString());
	}

	/**
	 * Prints a formatted string using the given locale, format string and
	 * arguments to the output area.
	 */
	public void printf(Locale locale, String format, Object... args) {
		print(String.format(locale, format, args));
	}

	/**
	 * Prints a formatted string using the given format string and arguments to
	 * the output area.
	 */
	public void printf(String format, Object... args) {
		print(String.format(format, args));
	}

	/**
	 * Prints a new line to the output area.
	 */
	public void println() {
		print('\n');
	}

	/**
	 * Prints the given object and a new line to the output area.
	 */
	public void println(Object o) {
		print(o + "\n");
	}

	/**
	 * Prints the given amount of new lines.
	 */
	public void printNewLines(int count) {
		if (count < 0) {
			throw new IllegalArgumentException("amount of new lines cannot be negative");
		}
		print(new String(new char[count]).replace('\0', '\n'));
	}

	/**
	 * @param image the new frame icon image
	 */
	public final void setIconImage(Image image) {
		frame.setIconImage(image);
	}

	/**
	 * @param x the new frame x-coordinate
	 * @param y the new frame y-coordinate
	 */
	public final void setLocation(int x, int y) {
		frame.setLocation(x, y);
	}

	/**
	 * @param focusable whether the output area should be focusable
	 */
	public void setOutputFocusable(boolean focusable) {
		outputArea.setFocusable(focusable);
	}

	/**
	 * @param font the new output area font
	 */
	public final void setOutputFont(Font font) {
		outputArea.setFont(font);
	}

	/**
	 * Sets the size of the frame and centers it on the screen.
	 * 
	 * @param width the new frame width
	 * @param height the new frame height
	 */
	public final void setSize(int width, int height) {
		Dimension size = new Dimension(width, height);
		frame.setPreferredSize(size);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	/**
	 * @param title the new frame title
	 */
	public final void setTitle(String title) {
		frame.setTitle(title);
	}
}