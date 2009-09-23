package org.lushlife.negroni.core.exceptions;


public class RuntimeInvocationTargetException extends NegroniException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7498487896042040811L;

	public RuntimeInvocationTargetException() {
		super();
	}

	public RuntimeInvocationTargetException(String message, Throwable cause) {
		super(message, cause);
	}

	public RuntimeInvocationTargetException(String message) {
		super(message);
	}

	public RuntimeInvocationTargetException(Throwable cause) {
		super(cause);
	}

}
