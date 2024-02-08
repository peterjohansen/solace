package org.pemacy.solace.ui;

import org.pemacy.solace.ui.input.SwingInputArea;
import org.pemacy.solace.ui.output.SwingOutputArea;
import org.pemacy.solace.ui.window.SwingWindow;
import org.pemacy.solace.ui.window.Window;

public class SwingUserInterfaceFactory implements UserInterfaceFactory {

    @Override
    public Window newWindow() {
        final var outputArea = new SwingOutputArea();
        final var inputArea = new SwingInputArea();
        return new SwingWindow(outputArea, inputArea);
    }

}
