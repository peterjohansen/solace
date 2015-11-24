package com.actram.solace;

import java.awt.Image;
import java.util.Objects;

import com.actram.solace.interfaces.StandardWindow;
import com.actram.solace.ui.WindowUI;

/**
 * 
 *
 * @author Peter André Johansen
 */
public class Window implements StandardWindow {
	public static Window createNew() {
		return new Window(new WindowUI());
	}

	protected final WindowUI windowUI;

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
	public void print(Object obj) {
		windowUI.appendOutputText(obj.toString());
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * By default a {@link Window} will print the input.
	 */
	@Override
	public void processInput(String input) {
		println(input);
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
	public void setLocation(int x, int y) {
		windowUI.setFrameLocation(x, y);
	}

	@Override
	public void setSize(int width, int height) {
		if (width < 0) {
			throw new IllegalArgumentException("the window width cannot be null");
		}
		if (height < 0) {
			throw new IllegalArgumentException("the window height cannot be null");
		}

		windowUI.setFrameSize(width, height);
	}

	@Override
	public void setTitle(String title) {
		windowUI.setFrameTitle(title);
	}
}