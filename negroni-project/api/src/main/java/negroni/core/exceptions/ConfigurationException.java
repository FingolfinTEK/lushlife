package negroni.core.exceptions;


public class ConfigurationException extends NegroniException {

	private static final long serialVersionUID = -7977585123638401784L;

	public ConfigurationException() {
		super();
	}

	public ConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigurationException(String message) {
		super(message);
	}

	public ConfigurationException(Throwable cause) {
		super(cause);
	}

}
