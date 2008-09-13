package binky.reportrunner.scheduler.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
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
		if (endDate!=null) jobTrigger.setEndTime(endDate);
	
		jobTrigger.setName(jobName+":"+groupName+":trigger");
		jobTrigger.setGroup("RunnerTriggers");
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

	public void stopScheduler()
			throws SchedulerException {
		this.quartzScheduler.standby();
	}

	public Date getNextRunTime(String jobName, String groupName)
			throws SchedulerException {
		try {			
			return this.quartzScheduler.getTrigger(jobName+":"+groupName+":trigger",
					"RunnerTriggers").getNextFireTime();
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error next run time for " + jobName
					+ "/" + groupName, e);
		}
	}

	public Boolean isJobActive(String jobName, String groupName)
			throws SchedulerException {
		try {
			return !(this.quartzScheduler.getTriggerState(jobName+":"+groupName+":trigger",
					"RunnerTriggers") == Trigger.STATE_PAUSED);
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
		return !this.quartzScheduler.isInStandbyMode();
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

	@SuppressWarnings("unchecked")
	public Map<String, String> getCurrentRunningJobs() {
		Map<String,String> currentRunningJobs = new HashMap<String, String>();
		
		List<JobExecutionContext> cej = quartzScheduler.getCurrentlyExecutingJobs();
		
		for(JobExecutionContext je:cej) {
			currentRunningJobs.put(je.getJobDetail().getGroup(),je.getJobDetail().getName());
		}
						
		return currentRunningJobs;
	}
	
}
