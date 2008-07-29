package binky.reportrunner.scheduler;

import java.util.List;

import binky.reportrunner.data.RunnerJob;

public interface Scheduler {

	public void startScheduler() throws SchedulerException;

	public void stopScheduler() throws SchedulerException;

	public void addJob(RunnerJob job) throws SchedulerException;
	
	public void removeJob(RunnerJob job) throws SchedulerException;
	
	public void pauseJob(RunnerJob job) throws SchedulerException;
	
	public void resumeJob(RunnerJob job) throws SchedulerException;

	public void invokeJob(RunnerJob job) throws SchedulerException;

	public List<RunnerJob> listJobs();
}
