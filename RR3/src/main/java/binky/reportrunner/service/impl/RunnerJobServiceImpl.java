package binky.reportrunner.service.impl;

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

		if (runnerJobDao.getJob(jobName, groupName) != null) {
			deleteJob(jobName, groupName);
		}

		if ((job.getCronString() != null) || !job.getCronString().isEmpty()) {
			scheduler.addJob(jobName, groupName, job.getRunnerEngine(), job
					.getCronString(), job.getStartDate(), job.getEndDate());
		}

		runnerJobDao.saveUpdateJob(job);

	}

	public void deleteJob(String jobName, String groupName)
			throws SchedulerException {
		RunnerJob job = runnerJobDao.getJob(jobName, groupName);

		if ((job.getCronString() != null) || !job.getCronString().isEmpty()) {
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

}
