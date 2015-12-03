package com.actram.solace;

import java.awt.Font;
import java.awt.Image;
import java.util.Objects;

import com.actram.solace.ui.DefaultWindowUI;
import com.actram.solace.ui.StandardWindow;
import com.actram.solace.ui.WindowUI;

/**
 * 
 *
 * @author Peter André Johansen
 */
public class Window implements StandardWindow {
	public static final String DEFAULT_TITLE = "Window";
	
	protected final WindowUI windowUI;

	public Window() {
		this(new DefaultWindowUI());
	}
	
	/**
	 * Creates a new window that uses the given window user interface,
	 * which also controls whether the window is visible initially.
	 * <p>
	 * <strong>Note:</strong> Use {@link Window#createNew()} to create a new
	 * window. This constructor should only be used if you need to specify a
	 * custom user interface.
	 */
	public Window(WindowUI windowUI) {
		Objects.requireNonNull(windowUI, "the window user interface cannot be null");
		this.windowUI = windowUI;

		windowUI.setCloseListener(() -> close());
		windowUI.setInputListener((input) -> processInput(input));
	}

	@Override
	public void clearInput() {
		windowUI.clearInputText();
	}

	@Override
	public void clearOutput() {
		windowUI.setOutputText(null);
	}

	@Override
	public void close() {
		windowUI.disposeOfFrame();
	}

	@Override
	public String getCurrentInput() {
		return windowUI.getInputText();
	}

	@Override
	public int getHeight() {
		return windowUI.getFrameSize().height;
	}

	@Override
	public String getTitle() {
		return windowUI.getFrameTitle();
	}

	@Override
	public int getWidth() {
		return windowUI.getFrameSize().width;
	}

	@Override
	public int getX() {
		return windowUI.getFrameLocation().x;
	}

	@Override
	public int getY() {
		return windowUI.getFrameLocation().y;
	}

	@Override
	public boolean isAcceptingUserInput() {
		return windowUI.isInputEnabled();
	}

	@Override
	public boolean isInputHidden() {
		return windowUI.isInputHidden();
	}

	@Override
	public void print(Object obj) {
		windowUI.appendOutputText(obj.toString());
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * By default a {@link Window} will echo back any input from the user.
	 */
	@Override
	public void processInput(Object input) {
		println(input);
	}

	@Override
	public void selectInputText() {
		windowUI.selectInput();
	}

	@Override
	public void selectOuputText() {
		windowUI.selectOutput();
	}

	@Override
	public void setAcceptUserInput(boolean acceptInput) {
		windowUI.setInputEnabled(acceptInput);
	}

	@Override
	public void setCurrentInput(Object obj) {
		windowUI.setInputText(obj.toString());
	}

	@Override
	public void setIconImage(Image image) {
		windowUI.setFrameIconImage(image);
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
	public void setLocation(int x, int y) {
		windowUI.setFrameLocation(x, y);
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
		if (width < 0) {
			throw new IllegalArgumentException("the window width cannot be negative");
		}
		if (height < 0) {
			throw new IllegalArgumentException("the window height cannot be negative");
		}

		windowUI.setFrameSize(width, height);
	}
	
	@Override
	public void setTitle(String title) {
		windowUI.setFrameTitle(title);
	}
}