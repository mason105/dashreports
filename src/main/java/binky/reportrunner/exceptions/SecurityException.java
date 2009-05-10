package binky.reportrunner.exceptions;

public class SecurityException extends Exception {

	private static final long serialVersionUID = -7902864083925873380L;

	public SecurityException(String message) {
		super(message);
	}
	public SecurityException(String message, Throwable cause) {
		super(message, cause);
	}

}
