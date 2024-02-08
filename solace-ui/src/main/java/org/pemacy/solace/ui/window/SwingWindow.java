package org.pemacy.solace.ui.window;

import org.pemacy.solace.ui.input.SwingInputArea;
import org.pemacy.solace.ui.output.SwingOutputArea;

import javax.swing.*;
import java.awt.*;

public class SwingWindow implements Window {

    private final JFrame frame = new JFrame();

    public SwingWindow(final SwingOutputArea outputArea, final SwingInputArea inputArea) {
        final var layout = new BorderLayout();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(layout);
        frame.setMinimumSize(new Dimension(512, 256));
        frame.setPreferredSize(new Dimension(1024, 512));
        frame.pack();

        frame.add(BorderLayout.CENTER, outputArea.getComponent());
        frame.add(BorderLayout.SOUTH, inputArea.getTextField());
    }

    @Override
    public Window centerOnScreen() {
        frame.setLocationRelativeTo(null);
        return this;
    }

    public JFrame getFrame() {
        return frame;
    }

    @Override
    public Window setTitle(final String title) {
        frame.setTitle(title);
        return this;
    }

    @Override
    public Window setVisible(final boolean visible) {
        frame.setVisible(visible);
        return this;
    }

}
