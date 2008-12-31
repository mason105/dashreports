package binky.reportrunner.service;

import java.util.Date;
import java.util.List;

import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.scheduler.SchedulerException;

public interface RunnerJobService {
	
	public void addUpdateJob(RunnerJob job) throws SchedulerException;
	public void deleteJob(String jobName, String groupName) throws SchedulerException;
	public List<RunnerJob> listJobs(String groupName);
	public RunnerJob getJob(String jobName, String groupName);
	public void pauseJob(String jobName, String groupName) throws SchedulerException;
	public void resumeJob(String jobName, String groupName) throws SchedulerException;	
	public Boolean isJobActive(String jobName, String groupName) throws SchedulerException;
	public List<RunnerJob> getRunningJobs() throws SchedulerException;
	public void interruptRunningJob(String jobName, String groupName)
			throws SchedulerException;

	public void invokeJob(String jobName, String groupName) throws SchedulerException;
	public Date getNextRunTime(String jobName, String groupName) throws SchedulerException;
	public Date getPreviousRunTime(String jobName, String groupName) throws SchedulerException;
	
}
