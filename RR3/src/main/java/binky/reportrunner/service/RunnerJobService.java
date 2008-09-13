package binky.reportrunner.service;

import java.util.List;

import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.scheduler.SchedulerException;

public interface RunnerJobService {
	
	public void addUpdateJob(RunnerJob job) throws SchedulerException;
	public void deleteJob(String jobName, String groupName) throws SchedulerException;
	public List<RunnerJob> listJobs(String groupName);
	public RunnerJob getJob(String jobName, String groupName);
}
