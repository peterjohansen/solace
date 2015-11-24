package com.actram.solace.interfaces;

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
	public void close();

	/**
	 * @return the window's height
	 */
	public int getHeight();

	/**
	 * @return the window's title
	 */
	public String getTitle();
	
	/**
	 * @return the windows' width
	 */
	public int getWidth();

	/**
	 * @return the window's y-coordinate
	 */
	public int getX();

	/**
	 * @return the window's x-coordinate
	 */
	public int getY();

	/**
	 * @param height the window's new height
	 */
	public default void setHeight(int height) {
		this.setSize(getWidth(), height);
	}

	/**
	 * @param image the window's new icon image
	 */
	public void setIconImage(Image image);

	/**
	 * @param x the window's new x-coordinate
	 * @param y the window's new y-coordinate
	 */
	public void setLocation(int x, int y);

	/**
	 * @param width the window's new width
	 * @param height the window's new height
	 * @throws IllegalArgumentException if the width is negative
	 * @throws IllegalArgumentException if the height is negative
	 */
	public void setSize(int width, int height);

	/**
	 * @param title the window's new title
	 */
	public void setTitle(String title);

	/**
	 * @param width the window's new width
	 */
	public default void setWidth(int width) {
		this.setSize(width, getHeight());
	}

	/**
	 * @param x the window's new x-coordinate
	 */
	public default void setX(int x) {
		setLocation(x, getY());
	}

	/**
	 * @param y the window's new y-coordinate
	 */
	public default void setY(int y) {
		setLocation(getX(), y);
	}
}