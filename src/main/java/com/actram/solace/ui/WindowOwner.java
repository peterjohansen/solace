package com.actram.solace.ui;

import java.awt.Image;

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
}