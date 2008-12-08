package binky.reportrunner.service.impl;

import java.util.LinkedList;
import java.util.List;

import binky.reportrunner.dao.RunnerJobDao;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.scheduler.Scheduler;
import binky.reportrunner.scheduler.SchedulerException;
import binky.reportrunner.service.RunnerJobService;

public class RunnerJobServiceImpl implements RunnerJobService {
	private Scheduler scheduler;
	private RunnerJobDao runnerJobDao;

	public RunnerJobDao getRunnerJobDao() {
		return runnerJobDao;
	}

	public void setRunnerJobDao(RunnerJobDao runnerJobDao) {
		this.runnerJobDao = runnerJobDao;
	}

	public void addUpdateJob(RunnerJob job) throws SchedulerException {
		String groupName = job.getPk().getGroup().getGroupName();
		String jobName = job.getPk().getJobName();
		RunnerJob job_comp = runnerJobDao.getJob(jobName, groupName);
		if ((job_comp != null)
				&& ((job_comp.getCronString() != null) && !job_comp.getCronString()
						.isEmpty())) {
			scheduler.removeJob(jobName, groupName);
		}

		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			scheduler.addJob(jobName, groupName, job.getRunnerEngine(), job
					.getCronString(), job.getStartDate(), job.getEndDate());
		}

		runnerJobDao.saveUpdateJob(job);

	}

	public void deleteJob(String jobName, String groupName)
			throws SchedulerException {
		RunnerJob job = runnerJobDao.getJob(jobName, groupName);

		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			scheduler.removeJob(jobName, groupName);
		}

		runnerJobDao.deleteJob(jobName, groupName);

	}

	public RunnerJob getJob(String jobName, String groupName) {
		return runnerJobDao.getJob(jobName, groupName);
	}

	public List<RunnerJob> listJobs(String groupName) {
		return runnerJobDao.listJobs(groupName);
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public Boolean isJobActive(String jobName, String groupName) throws SchedulerException {
		RunnerJob job = runnerJobDao.getJob(jobName, groupName);
		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			return this.scheduler.isJobActive(jobName, groupName);
		} else {
			return false;
		}
	}

	public void pauseJob(String jobName, String groupName) throws SchedulerException {
		RunnerJob job = runnerJobDao.getJob(jobName, groupName);
		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			scheduler.pauseJob(jobName, groupName);
		}
	}
	
	public void resumeJob(String jobName, String groupName) throws SchedulerException {
		RunnerJob job = runnerJobDao.getJob(jobName, groupName);
		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			scheduler.resumeJob(jobName, groupName);
		}
	}
	

	public List<RunnerJob> getRunningJobs() throws SchedulerException {
		List<String> runningJobs = scheduler.getCurrentRunningJobs();
		List<RunnerJob> jobs = new LinkedList<RunnerJob>();
		for (String string : runningJobs) {
			String groupName=string.split(":|:")[0];
			String jobName=string.split(":|:")[1];
			RunnerJob job = runnerJobDao.getJob(jobName, groupName);
			jobs.add(job);
		}
		return jobs;
	}

	public void interruptRunningJob(String jobName, String groupName) throws SchedulerException {
		RunnerJob job = runnerJobDao.getJob(jobName, groupName);
		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			scheduler.interruptRunningJob(jobName, groupName);
		}
	}

}
