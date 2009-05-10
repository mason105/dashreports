package binky.reportrunner.scheduler;

public class SchedulerException extends Exception {

	private static final long serialVersionUID = 715254575368864013L;

	public SchedulerException (String message, Exception e) {
		super(message,e);
	}
}
