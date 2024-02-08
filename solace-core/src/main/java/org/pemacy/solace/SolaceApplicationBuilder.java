package org.pemacy.solace;

import org.pemacy.solace.ui.SwingUserInterfaceFactory;
import org.pemacy.solace.ui.UserInterfaceFactory;
import org.pemacy.solace.ui.window.Window;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class SolaceApplicationBuilder {

    private final UserInterfaceFactory uiFactory;

    private String title;

    public SolaceApplicationBuilder() {
        this(new SwingUserInterfaceFactory());
    }

    public SolaceApplicationBuilder(final UserInterfaceFactory uiFactory) {
        this.uiFactory = requireNonNull(uiFactory);
    }

    public SolaceApplicationBuilder title(final String title) {
        this.title = title;
        return this;
    }

    public Window build() {
        final var window = uiFactory.newWindow();
        window.setTitle(Optional.ofNullable(title).orElse("Solace"));
        window.centerOnScreen();
        window.setVisible(true);
        return window;
    }

}
