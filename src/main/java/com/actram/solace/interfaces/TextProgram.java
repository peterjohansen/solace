package com.actram.solace.interfaces;

import java.awt.Font;

/**
 * A text program is a program that conveys information to the user by
 * displaying text.
 *
 * @author Peter André Johansen
 */
public interface TextProgram {

	/**
	 * Selects the text in the input area.
	 */
	public void selectInputText();

	/**
	 * Selects the text in the output area.
	 */
	public void selectOuputText();

	/**
	 * @param font the new input font
	 */
	public void setInputFont(Font font);

	/**
	 * @param focusable whether the output area should be focusable
	 */
	public void setOutputFocusable(boolean focusable);

	/**
	 * @param font the new output font
	 */
	public void setOutputFont(Font font);

}