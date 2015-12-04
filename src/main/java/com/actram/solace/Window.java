package com.actram.solace;

import java.awt.Font;
import java.awt.Image;
import java.util.Objects;

import com.actram.solace.ui.DefaultWindowUI;
import com.actram.solace.ui.WindowUI;
import com.actram.solace.ui.event.CloseListener;
import com.actram.solace.ui.event.InputListener;
import com.actram.solace.ui.event.KeyListener;

/**
 * A window is a text-based user interface. Input can be received from the user
 * through a text field and key presses and output can be displayed in a text
 * area.
 *
 * @author Peter André Johansen
 */
public class Window implements InputProcessor, WindowUI {
	public static final String DEFAULT_TITLE = "Window";

	protected final WindowUI windowUI;

	/**
	 * Creates a new window.
	 * <p>
	 * The window will use the {@link DefaultWindowUI}.
	 */
	public Window() {
		this(new DefaultWindowUI());
	}

	/**
	 * Creates a new window that uses the given window user interface, which
	 * also controls whether the window is visible initially.
	 */
	public Window(WindowUI windowUI) {
		Objects.requireNonNull(windowUI, "the window user interface cannot be null");
		this.windowUI = windowUI;
		windowUI.setTitle(DEFAULT_TITLE);

		windowUI.setCloseListener(() -> close());
		windowUI.setInputListener((input) -> processInput(input));
	}

	@Override
	public void clearInput() {
		windowUI.clearInput();
	}

	@Override
	public void clearOutput() {
		windowUI.clearOutput();
	}

	@Override
	public void close() {
		windowUI.close();
	}

	@Override
	public String getCurrentInput() {
		return windowUI.getCurrentInput();
	}

	@Override
	public String getCurrentOutput() {
		return windowUI.getCurrentOutput();
	}

	@Override
	public int getHeight() {
		return windowUI.getHeight();
	}

	@Override
	public String getTitle() {
		return windowUI.getTitle();
	}

	@Override
	public int getWidth() {
		return windowUI.getWidth();
	}

	@Override
	public int getX() {
		return windowUI.getX();
	}

	@Override
	public int getY() {
		return windowUI.getY();
	}

	@Override
	public boolean isAcceptingUserInput() {
		return windowUI.isAcceptingUserInput();
	}

	@Override
	public boolean isInputHidden() {
		return windowUI.isInputHidden();
	}

	@Override
	public void print(Object obj) {
		windowUI.print(obj);
	}

	@Override
	public void processInput(Object input) {
		println(input);
	}

	@Override
	public void selectInputText() {
		windowUI.selectInputText();
	}

	@Override
	public void selectOuputText() {
		windowUI.selectOuputText();
	}

	@Override
	public void setAcceptUserInput(boolean acceptInput) {
		windowUI.setAcceptUserInput(acceptInput);
	}

	@Override
	public void setCloseListener(CloseListener closeListener) {
		windowUI.setCloseListener(closeListener);
	}

	@Override
	public void setCurrentInput(Object obj) {
		windowUI.setCurrentInput(obj);
	}

	@Override
	public void setCurrentOutput(Object obj) {
		windowUI.setCurrentOutput(obj);
	}

	@Override
	public void setIconImage(Image image) {
		windowUI.setIconImage(image);
	}

	@Override
	public void setInputFont(Font font) {
		windowUI.setInputFont(font);
	}

	@Override
	public void setInputHidden(boolean hidden) {
		windowUI.setInputHidden(hidden);
	}

	@Override
	public void setInputListener(InputListener inputListener) {
		windowUI.setInputListener(inputListener);
	}

	@Override
	public void setKeyListener(KeyListener keyListener) {
		windowUI.setKeyListener(keyListener);
	}

	@Override
	public void setLocation(int x, int y) {
		windowUI.setLocation(x, y);
	}

	@Override
	public void setOutputFocusable(boolean focusable) {
		windowUI.setOutputFocusable(focusable);
	}

	@Override
	public void setOutputFont(Font font) {
		windowUI.setOutputFont(font);
	}

	@Override
	public void setSize(int width, int height) {
		windowUI.setSize(width, height);
	}

	@Override
	public void setTitle(String title) {
		windowUI.setTitle(title);
	}

	@Override
	public void setVisible(boolean visible) {
		windowUI.setVisible(visible);
	}
}