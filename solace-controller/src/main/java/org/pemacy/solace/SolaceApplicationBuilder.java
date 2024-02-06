package org.pemacy.solace;

import org.pemacy.solace.ui.SwingUserInterfaceFactory;
import org.pemacy.solace.ui.window.Window;

public class SolaceApplicationBuilder {

    public Window build() {
        return new SwingUserInterfaceFactory().newWindow();
    }

}
