package com.actram.solace.ui.event;

/**
 * Listener for key presses.
 *
 * @author Peter André Johansen
 */
public interface KeyListener {

	/**
	 * Called with the given key code when a key is pressed.
	 */
	public void keyPressed(int keyCode);

}