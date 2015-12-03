package com.actram.solace.interfaces;

/**
 * An input processor receives input, processes it and updates the program.
 *
 * @author Peter André Johansen
 */
public interface InputProcessor {

	/**
	 * This method feeds the program input, either programmatically or from the
	 * user, so that it can be processed.
	 * 
	 * @param input the input
	 */
	public void processInput(Object input);
}