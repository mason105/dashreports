package binky.reportrunner.data;

import java.util.Date;

public class RunnerActiveJob {

	private Date startTime;
	private RunnerJob runnerJob;
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public RunnerJob getRunnerJob() {
		return runnerJob;
	}
	public void setRunnerJob(RunnerJob runnerJob) {
		this.runnerJob = runnerJob;
	}

	
}
