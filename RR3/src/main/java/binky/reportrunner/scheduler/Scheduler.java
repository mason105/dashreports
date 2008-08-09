package binky.reportrunner.scheduler;

import java.util.Date;
import java.util.List;

import binky.reportrunner.data.RunnerActiveJob;
import binky.reportrunner.data.RunnerJob;

public interface Scheduler {

	public void startScheduler() throws SchedulerException;

	public void stopScheduler(Boolean waitForJobsToComplete) throws SchedulerException;
	
	public Boolean isSchedulerActive()  throws SchedulerException;

	public void addJob(RunnerJob job) throws SchedulerException;
	
	public void removeJob(String jobName, String groupName) throws SchedulerException;
	
	public void pauseJob(String jobName, String groupName) throws SchedulerException;
	
	public void resumeJob(String jobName, String groupName) throws SchedulerException;

	public void invokeJob(String jobName, String groupName) throws SchedulerException;

	public List<String> listJobs(String groupName)  throws SchedulerException;
	
	public List<String> listGroups()  throws SchedulerException;
	
	public RunnerJob getRunnerJob(String jobName, String groupName)  throws SchedulerException;
	
	public Date getNextRunTime(String jobName, String groupName)  throws SchedulerException;
	
	public Boolean isJobActive(String jobName, String groupName)  throws SchedulerException;
	
	public List<RunnerActiveJob> getRunningActiveJobs() throws SchedulerException;
	
	public void interruptRunningJob(String jobName, String groupName)  throws SchedulerException;
}
