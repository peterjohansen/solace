package org.pemacy.solace.ui.output;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class SwingOutputArea implements OutputArea {

    private final JScrollPane scrollPane;

    public SwingOutputArea() {
        final var textArea = new JTextArea();
        textArea.setBorder(new EmptyBorder(8, 8, 8, 8));
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setFont(Font.decode("monospace"));
        textArea.setLineWrap(true);
        textArea.setMargin(null);
        textArea.setWrapStyleWord(true);

        final var caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        this.scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(textArea);

        textArea.setText("hello world ".repeat(16).trim()); // TODO
    }

    public JComponent getComponent() {
        return scrollPane;
    }

}
