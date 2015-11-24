package com.actram.solace.ui.listeners;

/**
 * Listener for close requests.
 *
 * @author Peter André Johansen
 */
public interface CloseListener {

	/**
	 * Called when the user attempts to close the window.
	 */
	public void close();

}