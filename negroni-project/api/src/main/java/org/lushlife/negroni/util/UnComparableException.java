package org.lushlife.negroni.util;

public class UnComparableException extends RuntimeException {

	public UnComparableException() {
		super();
	}

	public UnComparableException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnComparableException(String message) {
		super(message);
	}

	public UnComparableException(Throwable cause) {
		super(cause);
	}

}
