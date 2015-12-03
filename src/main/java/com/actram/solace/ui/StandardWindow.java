package com.actram.solace.ui;

import com.actram.solace.InputProcessor;

/**
 * Interface that defines all the operations of a standard window.
 *
 * @author Peter André Johansen
 */
public interface StandardWindow extends InputOwner, InputProcessor, OutputOwner, TextProgram, WindowOwner {
}