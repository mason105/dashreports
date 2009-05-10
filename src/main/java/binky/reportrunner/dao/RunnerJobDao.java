package binky.reportrunner.dao;

import java.util.List;

import binky.reportrunner.data.RunnerJob;

public interface RunnerJobDao {
	
	public void saveUpdateJob(RunnerJob job);
	public void deleteJob(String jobName, String groupName);
	public List<RunnerJob> listJobs(String groupName);
	public RunnerJob getJob(String jobName, String groupName);
	
}
