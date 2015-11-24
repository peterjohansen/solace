package com.actram.solace.interpreter;

public interface InputValidator<T> {
	public T convert(String input);

	public String getFirstError(String input);
}