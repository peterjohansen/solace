package com.actram.solace;

/**
 * An input receiver can be sent input that will later be processed.
 * 
 * @author Peter André Johansen
 */
public interface InputReceiver {

	/**
	 * Removes what has been input, but not processed. An example would be
	 * clearing the text in a text field.
	 */
	public void clearInput();

	/**
	 * @return what has been input, but not processed
	 */
	public Object getCurrentInput();

	/**
	 * Sets the current input. The input will only be updated, not processed.
	 * 
	 * @param obj the input
	 */
	public void setCurrentInput(Object obj);

}