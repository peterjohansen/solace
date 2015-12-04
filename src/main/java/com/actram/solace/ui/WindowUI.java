package com.actram.solace.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;

import com.actram.solace.ui.event.CloseListener;
import com.actram.solace.ui.event.InputListener;
import com.actram.solace.ui.event.KeyListener;

public interface WindowUI {

	/**
	 * @param text the text to append to the output area
	 */
	void appendOutputText(String text);

	/**
	 * Clears the input text.
	 */
	void clearInputText();

	/**
	 * Disposes of the frame.
	 */
	void disposeOfFrame();

	/**
	 * @return the frame's location
	 */
	Point getFrameLocation();

	/**
	 * @return the frame's size
	 */
	Dimension getFrameSize();

	/**
	 * @return the frame's title
	 */
	String getFrameTitle();

	/**
	 * @return the text currently in the input area
	 */
	String getInputText();

	/**
	 * @return the text currently in the output area
	 */
	String getOutputText();

	/**
	 * @return whether the frame is visible
	 */
	boolean isFrameVisible();

	/**
	 * @return whether the input area is enabled
	 */
	boolean isInputEnabled();

	/**
	 * @return whether the input area is hidden
	 */
	boolean isInputHidden();

	/**
	 * Selects the text in the input area.
	 */
	void selectInput();

	/**
	 * Selects the text in the output area.
	 */
	void selectOutput();

	/**
	 * @param closeListener the window's new close listener, or {@code null} to
	 *            clear it
	 */
	void setCloseListener(CloseListener closeListener);

	/**
	 * @param image the frame's new icon image
	 */
	void setFrameIconImage(Image image);

	/**
	 * @param x the frame's new x-coordinate
	 * @param y the frame's new y-coordinate
	 */
	void setFrameLocation(int x, int y);

	/**
	 * Sets the frame's size. The frame will be resized around it's midpoint.
	 * 
	 * @param width the frame's new width
	 * @param height the frame's new height
	 * @throws IllegalArgumentException if the width is negative
	 * @throws IllegalArgumentException if the height is negative
	 */
	void setFrameSize(int width, int height);

	/**
	 * @param title the frame's new title
	 */
	void setFrameTitle(String title);

	/**
	 * @param visible whether the frame is visible
	 */
	void setFrameVisible(boolean visible);

	/**
	 * @param enabled whether to enable the input area
	 */
	void setInputEnabled(boolean enabled);

	/**
	 * @param font the input area's new font
	 */
	void setInputFont(Font font);

	/**
	 * @param hidden whether the input area should be hidden
	 */
	void setInputHidden(boolean hidden);

	/**
	 * @param inputListener the window's new input listener, or {@code null} to
	 *            clear it
	 */
	void setInputListener(InputListener inputListener);

	/**
	 * @param text the text to display in the input area
	 */
	void setInputText(String text);

	/**
	 * @param keyListener the window's new key listener, or {@code null} to
	 *            clear it
	 */
	void setKeyListener(KeyListener keyListener);

	/**
	 * @param focusable whether the output area should be focusable
	 */
	void setOutputFocusable(boolean focusable);

	/**
	 * @param font the output area's new font
	 */
	void setOutputFont(Font font);

	/**
	 * @param text the text to display in the output area
	 */
	void setOutputText(String text);

}