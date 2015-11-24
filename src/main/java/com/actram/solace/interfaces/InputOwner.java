package com.actram.solace.interfaces;

/**
 * An input owner controls input interfacing with the user in the program.
 * 
 * @author Peter André Johansen
 */
public interface InputOwner {

	/**
	 * Removes what has been input, but not processed. An example would be
	 * clearing the text in a text field.
	 */
	public void clearInput();

	/**
	 * @return what has been input, but not processed
	 */
	public String getCurrentInput();

	/**
	 * @return whether the window is accepting user input
	 */
	public boolean isAcceptingUserInput();
	
	/**
	 * @param acceptInput whether to accept user input
	 */
	public void setAcceptUserInput(boolean acceptInput);

	/**
	 * Sets the current input. The input will only be up dated, not processed.
	 * 
	 * @param obj the input
	 */
	public void setCurrentInput(Object obj);
}