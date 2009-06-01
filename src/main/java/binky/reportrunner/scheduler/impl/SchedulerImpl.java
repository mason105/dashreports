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

import binky.reportrunner.engine.RunnerEngine;
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

	public void addJob(String jobName, String groupName, 
			String cronString, Date startDate, Date endDate)
			throws SchedulerException {
	
		// Create our job with the specification RunnerJob
		JobDetail jobDetail;
		try {
			if (this.quartzScheduler.getJobDetail(jobName, groupName) != null) {
				removeJob(jobName,groupName);
			}			
			jobDetail = new JobDetail(jobName, groupName,RunnerEngine.class);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException(
					"Error with scheduler", e);
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
		
		//clusterting
		if (clustered) jobDetail.setRequestsRecovery(true);
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
			throw new SchedulerException("Error pausing group " 
					+ groupName, e);
		}	
	}

	public void resumeGroup(String groupName) throws SchedulerException {
		logger.debug("resume group: " + groupName);
		try {
			this.quartzScheduler.resumeJobGroup(groupName);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error resuming group " 
					+ groupName, e);
		}			
	}

	public void addDashboardAlert(Integer alertId, String cronTab) throws SchedulerException {
		// Create our job with the specification RunnerJob
		String jobName=alertId.toString(); 
		String groupName="RR3DASHBOARDS";
		JobDetail jobDetail;
		
		logger.debug("scheduling:" + alertId + "/"+groupName);
		try {
			if (this.quartzScheduler.getJobDetail(jobName, groupName) != null) {
				removeJob(jobName,groupName);
			}			
			jobDetail = new JobDetail(jobName, groupName,AlertProcessor.class);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException(
					"Error with scheduler", e);
		}

		// Create the trigger
		CronTrigger jobTrigger = new CronTrigger();
		try {
			jobTrigger.setCronExpression(cronTab);
		} catch (ParseException e) {
			throw new SchedulerException("Error with cron configuration", e);
		}

		jobTrigger.setStartTime(new Date());
		
		jobTrigger.setName(jobName + ":" + groupName + ":trigger");
		jobTrigger.setGroup("RunnerTriggers");
		
		//clusterting
		if (clustered) jobDetail.setRequestsRecovery(true);
		// Bind the listener
		// jobDetail.addJobListener("ReportRunnerCoreJobListener");

		// schedule the job
		try {
			this.quartzScheduler.scheduleJob(jobDetail, jobTrigger);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error dashboard alert scheduling with quartz", e);
		}
	}

	public void removedDashboardAlert(Integer alertId) throws SchedulerException {
		logger.debug("remove alert: " + alertId);
		try {
			this.quartzScheduler.deleteJob(alertId.toString(), "RR3DASHBOARDS");
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error removing dashboard alert from scheduler: RR-DASHBOARD"+alertId+"/RR3DASHBOARDS", e);
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
	
}
