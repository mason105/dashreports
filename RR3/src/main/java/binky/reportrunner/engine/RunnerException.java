package binky.reportrunner.engine;

public class RunnerException extends Exception {

	private static final long serialVersionUID = -8483333064526864264L;

	public RunnerException (String message, Exception e) {
		super(message,e);
	}
	
}
