package binky.reportrunner.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.RunnerJobDao;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.scheduler.Scheduler;
import binky.reportrunner.scheduler.SchedulerException;
import binky.reportrunner.service.RunnerJobService;

public class RunnerJobServiceImpl implements RunnerJobService {
	private Scheduler scheduler;
	private RunnerJobDao runnerJobDao;
	private static final Logger logger = Logger
			.getLogger(RunnerJobServiceImpl.class);

	public RunnerJobDao getRunnerJobDao() {
		return runnerJobDao;
	}

	public void setRunnerJobDao(RunnerJobDao runnerJobDao) {
		this.runnerJobDao = runnerJobDao;
	}

	public void addUpdateJob(RunnerJob job) throws SchedulerException {
		String groupName = job.getPk().getGroup().getGroupName();
		String jobName = job.getPk().getJobName();
		logger.debug("add update job: " + groupName + "." + jobName);
		RunnerJob job_comp = runnerJobDao.getJob(jobName, groupName);
		if ((job_comp != null)
				&& ((job_comp.getCronString() != null) && !job_comp
						.getCronString().isEmpty())) {
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
		logger.debug("delete job: " + groupName + "." + jobName);
		RunnerJob job = runnerJobDao.getJob(jobName, groupName);
		runnerJobDao.deleteJob(jobName, groupName);
		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			scheduler.removeJob(jobName, groupName);
		}
	}

	public RunnerJob getJob(String jobName, String groupName) {
		logger.debug("get job: " + groupName + "." + jobName);
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

	public Boolean isJobActive(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("is job active: " + groupName + "." + jobName);
		RunnerJob job = runnerJobDao.getJob(jobName, groupName);
		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			if (scheduler.isScheduled(jobName, groupName)) {
				return this.scheduler.isJobActive(jobName, groupName);
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void pauseJob(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("pause job: " + groupName + "." + jobName);
		RunnerJob job = runnerJobDao.getJob(jobName, groupName);
		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			scheduler.pauseJob(jobName, groupName);
		}
	}

	public void resumeJob(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("resume job: " + groupName + "." + jobName);
		RunnerJob job = runnerJobDao.getJob(jobName, groupName);
		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			scheduler.resumeJob(jobName, groupName);
		}
	}

	public List<RunnerJob> getRunningJobs() throws SchedulerException {
		List<String> runningJobs = scheduler.getCurrentRunningJobs();
		List<RunnerJob> jobs = new LinkedList<RunnerJob>();
		for (String string : runningJobs) {
			String groupName = string.split(":|:")[0];
			String jobName = string.split(":|:")[1];
			RunnerJob job = runnerJobDao.getJob(jobName, groupName);
			jobs.add(job);
		}
		return jobs;
	}

	public void interruptRunningJob(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("interrupt job: " + groupName + "." + jobName);
		RunnerJob job = runnerJobDao.getJob(jobName, groupName);
		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			scheduler.interruptRunningJob(jobName, groupName);
		}
	}

	public void invokeJob(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("invoke job: " + groupName + "." + jobName);
		RunnerJob job = runnerJobDao.getJob(jobName, groupName);
		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			// if already in scheduler lets go
			scheduler.invokeJob(jobName, groupName);
		} else {
			// schedule it then remove it
			// TODO test this works as it is the mother of all hacks
			Date date = Calendar.getInstance().getTime();
			scheduler.addJob(jobName, groupName, job.getRunnerEngine(),
					"0/1 * * * * ?", date, new Date(date.getTime() + 1000));
			scheduler.invokeJob(jobName, groupName);
		}

	}

	public Date getPreviousRunTime(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("get previous run time job: " + groupName + "." + jobName);
		if (scheduler.isScheduled(jobName, groupName)) {
			return scheduler.getPreviousRunTime(jobName, groupName);
		} else {
			return null;
		}
	}

	public Date getNextRunTime(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("get next run time job: " + groupName + "." + jobName);
		if (scheduler.isScheduled(jobName, groupName)) {
			return scheduler.getNextRunTime(jobName, groupName);
		} else {
			return null;
		}
	}

	public void pauseGroup(String groupName) throws SchedulerException {
		logger.debug("pause group: " + groupName);
		this.scheduler.pauseGroup(groupName);
	}

	public void resumeGroup(String groupName) throws SchedulerException {
		logger.debug("resume group: " + groupName);
		this.scheduler.resumeGroup(groupName);
	}

}
