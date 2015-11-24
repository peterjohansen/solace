package com.actram.solace.interfaces;

import java.awt.Font;

public interface _UNASSIGNED {

	/**
	 * Selects the text in the input area.
	 */
	public void selectInput();
	
	/**
	 * @param font the new input font
	 */
	public void setInputFont(Font font);

	/**
	 * @param hidden whether to hide the input 
	 */
	public void setInputHidden(boolean hidden);

	/**
	 * @param focusable whether the output area should be focusable
	 */
	public void setOutputFocusable(boolean focusable);
	
	/**
	 * @param font the new output font
	 */
	public void setOutputFont(Font font);
}