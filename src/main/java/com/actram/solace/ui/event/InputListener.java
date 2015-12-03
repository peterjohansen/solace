package com.actram.solace.ui.event;

/**
 * Listener for input submission.
 *
 * @author Peter André Johansen
 */
public interface InputListener {

	/**
	 * Called when the user attempts to submit input.
	 * 
	 * @param input the input the user wants to submit
	 */
	public void receiveInput(String input);

}