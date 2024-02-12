package org.pemacy.solace.ui.input;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static java.util.Collections.emptySet;

public class SwingInputArea implements InputArea {

    private final JTextField textField;

    public SwingInputArea() {
        this.textField = new JTextField();
        textField.setBorder(new CompoundBorder(
                new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
                new EmptyBorder(8, 8, 8, 8)));
        textField.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, emptySet());
        textField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textField.requestFocus();
    }

    public JTextField getTextField() {
        return textField;
    }

}
