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
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setLineWrap(true);
        textArea.setMargin(null);
        textArea.setWrapStyleWord(true);

        final var caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        this.scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(textArea);
    }

    public JComponent getComponent() {
        return scrollPane;
    }

}
