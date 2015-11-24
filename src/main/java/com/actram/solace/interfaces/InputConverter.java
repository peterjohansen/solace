package com.actram.solace.interfaces;

public interface InputConverter<T> {
	public T convert(String input);
}