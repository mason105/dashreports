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
 * Module: SchedulerImpl.java
 ******************************************************************************/
package binky.reportrunner.scheduler.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.impl.StdScheduler;

import binky.reportrunner.engine.ReportGenerationJob;
import binky.reportrunner.engine.dashboard.AlertProcessor;
import binky.reportrunner.scheduler.Scheduler;
import binky.reportrunner.scheduler.SchedulerException;

public class SchedulerImpl implements Scheduler {

	private StdScheduler quartzScheduler;
	private Logger logger = Logger.getLogger(SchedulerImpl.class);
	private boolean clustered;

	public StdScheduler getQuartzScheduler() {
		return quartzScheduler;
	}

	public void setQuartzScheduler(StdScheduler quartzScheduler) {
		this.quartzScheduler = quartzScheduler;
	}

	public int getJobCount() throws org.quartz.SchedulerException {
		/*int i = 0;
		for (String groupName : this.quartzScheduler.getJobGroupNames()) {
			for (String jobName : this.quartzScheduler.getJobNames(groupName)) {
				logger.debug("found " + jobName + "/" + groupName);
				i++;
			}
		}
		return i;*/
		return this.quartzScheduler.getJobGroupNames().length;
	}

	public void addJob(String jobName, String groupName, String cronString,
			Date startDate, Date endDate) throws SchedulerException {

		// Create our job with the specification RunnerJob
		JobDetail jobDetail;
		try {
			if (this.quartzScheduler.getJobDetail(jobName, groupName) != null) {
				removeJob(jobName, groupName);
			}
			jobDetail = new JobDetail(jobName, groupName, ReportGenerationJob.class);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error with scheduler", e);
		}

		// Create the trigger
		CronTrigger jobTrigger = new CronTrigger();
		try {
			jobTrigger.setCronExpression(cronString);
		} catch (ParseException e) {
			throw new SchedulerException("Error with cron configuration", e);
		}

		jobTrigger.setStartTime(startDate);
		if (endDate != null)
			jobTrigger.setEndTime(endDate);

		jobTrigger.setName(jobName + ":" + groupName + ":trigger");
		jobTrigger.setGroup("RunnerTriggers");

		// clusterting
		if (clustered)
			jobDetail.setRequestsRecovery(true);
		// Bind the listener
		// jobDetail.addJobListener("ReportRunnerCoreJobListener");

		// schedule the job
		try {
			this.quartzScheduler.scheduleJob(jobDetail, jobTrigger);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error scheduling with quartz", e);
		}
	}

	public void startScheduler() throws SchedulerException {
		try {
			this.quartzScheduler.start();
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error starting scheduler", e);
		}
	}

	public void stopScheduler() throws SchedulerException {
		this.quartzScheduler.standby();
	}

	public Date getNextRunTime(String jobName, String groupName)
			throws SchedulerException {
		try {
			return this.quartzScheduler.getTrigger(
					jobName + ":" + groupName + ":trigger", "RunnerTriggers")
					.getNextFireTime();
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error next run time for " + jobName
					+ "/" + groupName, e);
		}
	}

	public Date getPreviousRunTime(String jobName, String groupName)
			throws SchedulerException {
		try {
			return this.quartzScheduler.getTrigger(
					jobName + ":" + groupName + ":trigger", "RunnerTriggers")
					.getPreviousFireTime();
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error last run time for " + jobName
					+ "/" + groupName, e);
		}
	}

	public Boolean isJobActive(String jobName, String groupName)
			throws SchedulerException {
		try {
			return !(this.quartzScheduler.getTriggerState(jobName + ":"
					+ groupName + ":trigger", "RunnerTriggers") == Trigger.STATE_PAUSED);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error getting state for " + jobName
					+ "/" + groupName, e);
		}
	}

	public Boolean isScheduled(String jobName, String groupName)
			throws SchedulerException {
		try {
			return !(this.quartzScheduler.getTrigger(jobName + ":" + groupName
					+ ":trigger", "RunnerTriggers") == null);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error getting state for " + jobName
					+ "/" + groupName, e);
		}
	}

	public void invokeJob(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("invoke job: " + groupName + "." + jobName);
		try {

			this.quartzScheduler.triggerJob(jobName, groupName);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error triggering job " + jobName
					+ "/" + groupName, e);
		}
	}

	public Boolean isSchedulerActive() throws SchedulerException {
		return !this.quartzScheduler.isInStandbyMode();
	}

	public void pauseJob(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("pause job: " + groupName + "." + jobName);
		try {
			this.quartzScheduler.pauseJob(jobName, groupName);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error pausing job " + jobName + "/"
					+ groupName, e);
		}

	}

	public void removeJob(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("remove job: " + groupName + "." + jobName);
		try {
			this.quartzScheduler.deleteJob(jobName, groupName);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error removing job " + jobName + "/"
					+ groupName, e);
		}

	}

	public void resumeJob(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("resume job: " + groupName + "." + jobName);
		try {
			this.quartzScheduler.resumeJob(jobName, groupName);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error resuming job " + jobName + "/"
					+ groupName, e);
		}
	}

	public void interruptRunningJob(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("interrupt job: " + groupName + "." + jobName);
		try {
			this.quartzScheduler.interrupt(jobName, groupName);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error interrupting job " + jobName
					+ "/" + groupName, e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getCurrentRunningJobs() {
		List<String> currentRunningJobs = new LinkedList<String>();

		List<JobExecutionContext> cej = quartzScheduler
				.getCurrentlyExecutingJobs();

		for (JobExecutionContext je : cej) {
			currentRunningJobs.add(je.getJobDetail().getGroup() + ":|:"
					+ je.getJobDetail().getName());
		}

		return currentRunningJobs;
	}

	public void pauseGroup(String groupName) throws SchedulerException {
		logger.debug("pause group: " + groupName);
		try {
			this.quartzScheduler.pauseJobGroup(groupName);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error pausing group " + groupName, e);
		}
	}

	public void resumeGroup(String groupName) throws SchedulerException {
		logger.debug("resume group: " + groupName);
		try {
			this.quartzScheduler.resumeJobGroup(groupName);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error resuming group " + groupName, e);
		}
	}

	public void addDashboardAlert(Integer alertId, String cronTab)
			throws SchedulerException {
		// Create our job with the specification RunnerJob
		String jobName = alertId.toString();
		JobDetail jobDetail;

		logger.debug("scheduling:" + alertId + "/" + dashboardSchedulerGroup);
		try {
			if (this.quartzScheduler.getJobDetail(jobName,
					dashboardSchedulerGroup) != null) {
				removeJob(jobName, dashboardSchedulerGroup);
			}
			jobDetail = new JobDetail(jobName, dashboardSchedulerGroup,
					AlertProcessor.class);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error with scheduler", e);
		}

		// Create the trigger
		CronTrigger jobTrigger = new CronTrigger();
		try {
			jobTrigger.setCronExpression(cronTab);
		} catch (ParseException e) {
			throw new SchedulerException("Error with cron configuration", e);
		}

		jobTrigger.setStartTime(new Date());

		jobTrigger
				.setName(jobName + ":" + dashboardSchedulerGroup + ":trigger");
		jobTrigger.setGroup("RunnerTriggers");

		// clusterting
		if (clustered)
			jobDetail.setRequestsRecovery(true);
		// Bind the listener
		// jobDetail.addJobListener("ReportRunnerCoreJobListener");

		// schedule the job
		try {
			this.quartzScheduler.scheduleJob(jobDetail, jobTrigger);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException(
					"Error dashboard alert scheduling with quartz", e);
		}
	}

	public void removedDashboardAlert(Integer alertId)
			throws SchedulerException {
		logger.debug("remove alert: " + alertId);
		try {
			this.quartzScheduler.deleteJob(alertId.toString(),
					dashboardSchedulerGroup);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException(
					"Error removing dashboard alert from scheduler: RR-DASHBOARD"
							+ alertId + "/RR3DASHBOARDS", e);
		}
	}

	public boolean isClustered() {
		return clustered;
	}

	public boolean getClustered() {
		return clustered;
	}

	public void setClustered(boolean clustered) {
		this.clustered = clustered;
	}

	public void interruptRunningDashboardAlert(Integer alertId)
			throws SchedulerException {
		this.interruptRunningJob("" + alertId, dashboardSchedulerGroup);
	}

	public Date getNextRunTime(Integer alertId) throws SchedulerException {
		try {
			Trigger trigger = this.quartzScheduler.getTrigger(alertId
					.toString()
					+ ":" + dashboardSchedulerGroup + ":trigger",
					"RunnerTriggers");
			if (trigger != null) {
				return trigger.getNextFireTime();
			} else {
				return null;
			}	
		
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error next run time for alert "
					+ alertId, e);
		}
	}

	public Date getPreviousRunTime(Integer alertId) throws SchedulerException {
		try {
			Trigger t = this.quartzScheduler.getTrigger(alertId.toString()
					+ ":" + dashboardSchedulerGroup + ":trigger",
					"RunnerTriggers");
			if (t != null) {
				return t.getPreviousFireTime();
			} else {
				return null;
			}

		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error last run time for alert "
					+ alertId, e);
		}
	}

	
	public void invokeDashboardItem(Integer itemId) throws SchedulerException {
		logger.debug("invoke alert: " + itemId + "." + itemId);
		try {

			this.quartzScheduler.triggerJob("" + itemId,
					dashboardSchedulerGroup);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error triggering job " + itemId
					+ "/" + dashboardSchedulerGroup, e);
		}
	}

	
	public Date getActiveFrom()  {
		return this.quartzScheduler.getMetaData().getRunningSince();
	}

	
	public int getJobsExecuted() {
		return this.quartzScheduler.getMetaData().getNumberOfJobsExecuted();
	}


	public String getSummary() throws SchedulerException {
		try {
			return this.quartzScheduler.getMetaData().getSummary();
		} catch (org.quartz.SchedulerException e) {
			logger.error(e.getMessage(),e);
			throw new SchedulerException(e.getMessage(),e);
		}
	}

}
