package binky.reportrunner.exceptions;

public class InvalidParameterException extends Exception {

	private static final long serialVersionUID = 957633892477690815L;

	public InvalidParameterException(String message) {
		super(message);
	}

	public InvalidParameterException(Throwable cause) {
		super(cause);
	}

	public InvalidParameterException(String message, Throwable cause) {
		super(message, cause);
	}

}
