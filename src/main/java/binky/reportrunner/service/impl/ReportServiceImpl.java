/*******************************************************************************
 * Copyright (c) 2009 Daniel Grout.
 * 
 * GNU GENERAL PUBLIC LICENSE - Version 3
 * 
 * This file is part of Report Runner (http://code.google.com/p/reportrunner).
 * 
 * Report Runner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Runner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Runner. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Module: RunnerJobServiceImpl.java
 ******************************************************************************/
package binky.reportrunner.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJob_pk;
import binky.reportrunner.scheduler.Scheduler;
import binky.reportrunner.scheduler.SchedulerException;
import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.service.ReportService;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;

public class ReportServiceImpl implements ReportService {
	private Scheduler scheduler;

	private ReportRunnerDao<RunnerJob, RunnerJob_pk> runnerJobDao;

	private DatasourceService dataSourceService;

	private static final Logger logger = Logger
			.getLogger(ReportServiceImpl.class);

	public void setReportRunnerDao(
			ReportRunnerDao<RunnerJob, RunnerJob_pk> runnerJobDao) {
		this.runnerJobDao = runnerJobDao;
	}
	
	
	 @TriggersRemove(cacheName = "jobCache")
	public void addUpdateJob(RunnerJob job) throws SchedulerException {
		String groupName = job.getPk().getGroup().getGroupName();
		String jobName = job.getPk().getJobName();
		logger.debug("add update job: " + groupName + "." + jobName);
		RunnerJob job_comp = runnerJobDao.get(new RunnerJob_pk(jobName,
				new RunnerGroup(groupName)));
		if ((job_comp != null)
				&& ((job_comp.getCronString() != null) && !job_comp
						.getCronString().isEmpty())
				&& !job_comp.getCronString().equals(job.getCronString())) {
			scheduler.removeJob(jobName, groupName);
		}

		if (job.isScheduled() && (job.getCronString() != null)
				&& !job.getCronString().isEmpty()
				&& !scheduler.isScheduled(jobName, groupName)) {
			scheduler.addJob(jobName, groupName, job.getCronString(),
					job.getStartDate(), job.getEndDate());
		}
		runnerJobDao.saveOrUpdate(job);

	}
	 @TriggersRemove(cacheName = "jobCache")
	public void deleteJob(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("delete job: " + groupName + "." + jobName);
		RunnerJob job = runnerJobDao.get(new RunnerJob_pk(jobName,
				new RunnerGroup(groupName)));
		if (job != null && (job.getCronString() != null)
				&& !job.getCronString().isEmpty()) {
			scheduler.removeJob(jobName, groupName);

			runnerJobDao.delete(new RunnerJob_pk(jobName, new RunnerGroup(
					groupName)));
		}
	}

	@Cacheable(cacheName = "jobCache")
	public RunnerJob getJob(String jobName, String groupName) {
		logger.debug("get job: " + groupName + "." + jobName);
		return runnerJobDao.get(new RunnerJob_pk(jobName, new RunnerGroup(
				groupName)));
	}

	@Cacheable(cacheName = "jobCache")
	public List<RunnerJob> listJobs(String groupName) {
		return runnerJobDao.findByNamedQuery("getJobsByGroup",
				new String[] { groupName });
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

		RunnerJob job = runnerJobDao.get(new RunnerJob_pk(jobName,
				new RunnerGroup(groupName)));

		if (job != null && (job.getCronString() != null)
				&& !job.getCronString().isEmpty()) {
			if (scheduler.isScheduled(jobName, groupName)) {
				if (job.getEndDate() != null
						&& job.getEndDate().getTime() < Calendar.getInstance()
								.getTimeInMillis()) {
					// issue 84 - jobs showing as scheduled when the schedule
					// has ended.
					return false;
				} else {
					return this.scheduler.isJobActive(jobName, groupName);
				}
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
		RunnerJob job = runnerJobDao.get(new RunnerJob_pk(jobName,
				new RunnerGroup(groupName)));
		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			scheduler.pauseJob(jobName, groupName);
		}
	}

	public void resumeJob(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("resume job: " + groupName + "." + jobName);
		RunnerJob job = runnerJobDao.get(new RunnerJob_pk(jobName,
				new RunnerGroup(groupName)));
		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			scheduler.resumeJob(jobName, groupName);
		}
	}

	public List<RunnerJob> getRunningJobs() throws SchedulerException {
		logger.debug("executed getRunningJobs()");
		List<String> runningJobs = scheduler.getCurrentRunningJobs();
		List<RunnerJob> jobs = new LinkedList<RunnerJob>();
		for (String string : runningJobs) {
			logger.debug("found entry:" + string);
			String groupName = string.split(":|:")[0];
			String jobName = string.split(":|:")[2];
			if (!groupName.equals(Scheduler.dashboardSchedulerGroup)) {
				logger.debug("found a job with details of: " + jobName + "/"
						+ groupName);
				RunnerJob job = getJobHelper(jobName, groupName);
				jobs.add(job);
			}
		}
		return jobs;
	}

	private RunnerJob getJobHelper(String jobName, String groupName) {
		return runnerJobDao.get(new RunnerJob_pk(jobName, new RunnerGroup(
				groupName)));
	}

	public void interruptRunningJob(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("interrupt job: " + groupName + "." + jobName);
		RunnerJob job = getJobHelper(jobName, groupName);
		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			scheduler.interruptRunningJob(jobName, groupName);
		}
	}

	public void invokeJob(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("invoke job: " + groupName + "." + jobName);
		RunnerJob job = getJobHelper(jobName, groupName);
		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			// if already in scheduler lets go
			scheduler.invokeJob(jobName, groupName);
		} else {
			// schedule it then remove it
			// TODO test this works as it is the mother of all hacks
			Date date = Calendar.getInstance().getTime();
			scheduler.addJob(jobName, groupName, "0/1 * * * * ?", date,
					new Date(date.getTime() + 1000));
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

	public DatasourceService getDataSourceService() {
		return dataSourceService;
	}

	public void setDataSourceService(DatasourceService dataSourceService) {
		this.dataSourceService = dataSourceService;
	}

	public ReportRunnerDao<RunnerJob, RunnerJob_pk> getRunnerJobDao() {
		return runnerJobDao;
	}

	public void setRunnerJobDao(
			ReportRunnerDao<RunnerJob, RunnerJob_pk> runnerJobDao) {
		this.runnerJobDao = runnerJobDao;
	}

}
