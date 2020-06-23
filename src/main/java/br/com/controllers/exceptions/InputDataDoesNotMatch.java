package br.com.controllers.exceptions;

public class InputDataDoesNotMatch extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InputDataDoesNotMatch(String msg) {
		super(msg);
	}
}
