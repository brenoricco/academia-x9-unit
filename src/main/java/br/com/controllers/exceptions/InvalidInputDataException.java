package br.com.controllers.exceptions;

public class InvalidInputDataException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public InvalidInputDataException(String msg) {
		super(msg);
	}
}
