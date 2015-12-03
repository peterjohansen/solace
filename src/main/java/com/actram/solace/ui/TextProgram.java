package com.actram.solace.ui;

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
	void selectInputText();

	/**
	 * Selects the text in the output area.
	 */
	void selectOuputText();

	/**
	 * @param font the new input font
	 */
	void setInputFont(Font font);

	/**
	 * @param focusable whether the output area should be focusable
	 */
	void setOutputFocusable(boolean focusable);

	/**
	 * @param font the new output font
	 */
	void setOutputFont(Font font);

}