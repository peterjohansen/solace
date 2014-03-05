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
	protected static final int DEFAULT_WIDTH = 600;
	protected static final int DEFAULT_HEIGHT = 300;
	private static final Font OUTPUT_AREA_FONT = new Font("Arial", Font.PLAIN, 14);

	JFrame frame;
	private JTextArea outputArea;

	/**
	 * Creates a new output frame.
	 */
	public OutputFrame() {
		this(DEFAULT_TITLE);
	}

	/**
	 * Creates a new output frame with the given width and height.
	 * 
	 * @param width the width
	 * @param height the height
	 */
	public OutputFrame(int width, int height) {
		this(width, height, DEFAULT_TITLE);
	}

	/**
	 * Creates a new output frame with the given width, height and title.
	 * 
	 * @param width the width
	 * @param height the height
	 * @param title the title
	 */
	public OutputFrame(int width, int height, String title) {

		// Output area
		outputArea = new JTextArea();
		outputArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		outputArea.setMargin(null);
		outputArea.setEditable(false);
		outputArea.setLineWrap(true);
		outputArea.setWrapStyleWord(true);
		DefaultCaret caret = (DefaultCaret) outputArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		// Output area scroll pane
		int vsbPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED;
		int hsbPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER;
		JScrollPane outputScrollPane = new JScrollPane(outputArea, vsbPolicy, hsbPolicy);
		outputScrollPane.setBorder(null);

		// Frame
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				close();
			}
		});
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(outputScrollPane, BorderLayout.CENTER);

		// Initialize
		setOutputFont(OUTPUT_AREA_FONT);
		setSize(width, height);
		setTitle(title);
		frame.setVisible(true);
	}

	/**
	 * Creates a new output frame with the given title.
	 * 
	 * @param title the title
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
	 * Prints the given object to the output area.
	 * 
	 * @param o the object
	 */
	public void print(Object o) {
		outputArea.append(o.toString());
	}

	/**
	 * Prints a formatted string using the given locale, format string and arguments.
	 * 
	 * @param locale the locale
	 * @param format the format
	 * @param args the arguments
	 */
	public void printf(Locale locale, String format, Object... args) {
		printf(locale, String.format(format, args));
	}

	/**
	 * Prints a formatted string using the given format string and arguments.
	 * 
	 * @param format the format
	 * @param args the arguments
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
	 * 
	 * @param o the object
	 */
	public void println(Object o) {
		print(o + "\n");
	}

	/**
	 * @param x the new frame x coordinate
	 * @param y the new frame y coordinate
	 */
	public final void setLocation(int x, int y) {
		frame.setLocation(x, y);
	}

	/**
	 * @param font the new output area font
	 */
	public final void setOutputFont(Font font) {
		outputArea.setFont(font);
	}

	/**
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
	 * @param image the new frame icon image
	 */
	public final void setIconImage(Image image) {
		frame.setIconImage(image);
	}

	/**
	 * @param title the new frame title
	 */
	public final void setTitle(String title) {
		frame.setTitle(title);
	}
}