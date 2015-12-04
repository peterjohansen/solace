package com.actram.solace.ui;

import java.awt.Image;

import com.actram.solace.ui.event.CloseListener;
import com.actram.solace.ui.event.InputListener;
import com.actram.solace.ui.event.KeyListener;

/**
 * A window owner controls a window.
 *
 * @author Peter André Johansen
 */
public interface WindowOwner {

	/**
	 * Closes the window.
	 */
	void close();

	/**
	 * @return the window's height
	 */
	int getHeight();

	/**
	 * @return the window's title
	 */
	String getTitle();

	/**
	 * @return the windows' width
	 */
	int getWidth();

	/**
	 * @return the window's y-coordinate
	 */
	int getX();

	/**
	 * @return the window's x-coordinate
	 */
	int getY();

	/**
	 * Hides the window if it's visible.
	 */
	default void hide() {
		setVisible(false);
	}

	/**
	 * @param closeListener the window's new close listener, or {@code null} to
	 *            clear it
	 */
	void setCloseListener(CloseListener closeListener);

	/**
	 * @param height the window's new height
	 * @throws IllegalArgumentException if the height is negative
	 */
	default void setHeight(int height) {
		this.setSize(getWidth(), height);
	}

	/**
	 * @param image the window's new icon image
	 */
	void setIconImage(Image image);

	/**
	 * @param inputListener the window's new input listener, or {@code null} to
	 *            clear it
	 */
	void setInputListener(InputListener inputListener);

	/**
	 * @param keyListener the window's new key listener, or {@code null} to
	 *            clear it
	 */
	void setKeyListener(KeyListener keyListener);

	/**
	 * @param x the window's new x-coordinate
	 * @param y the window's new y-coordinate
	 */
	void setLocation(int x, int y);

	/**
	 * @param width the window's new width
	 * @param height the window's new height
	 * @throws IllegalArgumentException if the width is negative
	 * @throws IllegalArgumentException if the height is negative
	 */
	void setSize(int width, int height);

	/**
	 * @param title the window's new title
	 */
	void setTitle(String title);

	/**
	 * Sets whether the window is visible. The window will be resized around its
	 * center.
	 */
	void setVisible(boolean visible);

	/**
	 * @param width the window's new width
	 * @throws IllegalArgumentException if the width is negative
	 */
	default void setWidth(int width) {
		this.setSize(width, getHeight());
	}

	/**
	 * @param x the window's new x-coordinate
	 */
	default void setX(int x) {
		setLocation(x, getY());
	}

	/**
	 * @param y the window's new y-coordinate
	 */
	default void setY(int y) {
		setLocation(getX(), y);
	}

	/**
	 * Shows the window if it's hidden.
	 */
	default void show() {
		setVisible(true);
	}

}