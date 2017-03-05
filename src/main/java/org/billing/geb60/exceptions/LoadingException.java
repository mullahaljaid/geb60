package org.billing.geb60.exceptions;

public class LoadingException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public LoadingException() {
		super();
	}

	public LoadingException(String message) {
		super(message);
	}
	
	public LoadingException(Throwable cause) {
		super(cause);
	}
	
	public LoadingException(String message, Throwable cause) {
		super(message, cause);
	}
}
