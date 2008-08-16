package binky.reportrunner.scheduler.impl;

import java.text.ParseException;
import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.impl.StdScheduler;

import binky.reportrunner.scheduler.Scheduler;
import binky.reportrunner.scheduler.SchedulerException;

public class SchedulerImpl implements Scheduler {

	private StdScheduler quartzScheduler;

	public StdScheduler getQuartzScheduler() {
		return quartzScheduler;
	}

	public void setQuartzScheduler(StdScheduler quartzScheduler) {
		this.quartzScheduler = quartzScheduler;
	}

	public void addJob(String jobName, String groupName, String className,String cronString,
			Date startDate, Date endDate) throws SchedulerException {

		// Create our job with the specification RunnerJob
		JobDetail jobDetail;
		try {
			jobDetail = new JobDetail(jobName, groupName,
					Class.forName(className));
		} catch (ClassNotFoundException e) {
			throw new SchedulerException(
					"Error with RunnerEngine specification", e);
		}

		// Create the trigger
		CronTrigger jobTrigger = new CronTrigger();
		try {
			jobTrigger.setCronExpression(cronString);
		} catch (ParseException e) {
			throw new SchedulerException("Error with cron configuration", e);
		}

		jobTrigger.setStartTime(startDate);
		jobTrigger.setEndTime(endDate);
	
		// Bind the listener
		jobDetail.addJobListener("ReportRunnerCoreJobListener");

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

	public void stopScheduler(Boolean waitForJobsToComplete)
			throws SchedulerException {
		this.quartzScheduler.shutdown(waitForJobsToComplete);
	}

	public Date getNextRunTime(String jobName, String groupName)
			throws SchedulerException {
		try {
			return this.quartzScheduler.getTrigger(jobName + "Trigger",
					groupName + "Trigger").getNextFireTime();
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error next run time for " + jobName
					+ "/" + groupName, e);
		}
	}

	public Boolean isJobActive(String jobName, String groupName)
			throws SchedulerException {
		try {
			return !(this.quartzScheduler.getTriggerState(jobName + "Trigger",
					groupName + "Trigger") == Trigger.STATE_PAUSED);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error getting state for " + jobName
					+ "/" + groupName, e);
		}		
	}


	public void invokeJob(String jobName, String groupName)
			throws SchedulerException {
		try {

			this.quartzScheduler.triggerJob(jobName, groupName);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error triggering job " + jobName
					+ "/" + groupName, e);
		}
	}

	public Boolean isSchedulerActive() throws SchedulerException {
		return !this.quartzScheduler.isShutdown();
	}

	public void pauseJob(String jobName, String groupName)
			throws SchedulerException {
		try {
			this.quartzScheduler.pauseJob(jobName, groupName);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error pausing job " + jobName + "/"
					+ groupName, e);
		}

	}

	public void removeJob(String jobName, String groupName)
			throws SchedulerException {
		try {
			this.quartzScheduler.deleteJob(jobName, groupName);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error removing job " + jobName + "/"
					+ groupName, e);
		}

	}

	public void resumeJob(String jobName, String groupName)
			throws SchedulerException {
		try {
			this.quartzScheduler.resumeJob(jobName, groupName);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error removing job " + jobName + "/"
					+ groupName, e);
		}
	}


	public void interruptRunningJob(String jobName, String groupName)
			throws SchedulerException {
		try {
			this.quartzScheduler.interrupt(jobName, groupName);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error interrupting job " + jobName
					+ "/" + groupName, e);
		}
	}

}
