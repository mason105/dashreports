package binky.reportrunner.scheduler.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.impl.StdScheduler;

import binky.reportrunner.data.RunnerJob;
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

	public void addJob(RunnerJob job) throws SchedulerException {

		// Create our job with the specification RunnerJob
		JobDetail jobDetail;
		try {
			jobDetail = new JobDetail(job.getJobName(), job.getGroupName(),
					Class.forName(job.getRunnerEngine()));
		} catch (ClassNotFoundException e) {
			throw new SchedulerException(
					"Error with RunnerEngine specification", e);
		}

		// Create the trigger
		CronTrigger jobTrigger = new CronTrigger();
		try {
			jobTrigger.setCronExpression(job.getCronString());
		} catch (ParseException e) {
			throw new SchedulerException("Error with cron configuration", e);
		}

		jobTrigger.setStartTime(new Date());
		jobTrigger.setName(job.getJobName() + "Trigger");
		jobTrigger.setGroup(job.getGroupName());

		// Persist our job configuration
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("runnerJob", job);
		jobDetail.setJobDataMap(jobDataMap);

		// Bind the listener
		jobDetail.addJobListener("ReportRunnerCoreJobListener");

		// schedule the job
		try {
			this.quartzScheduler.scheduleJob(jobDetail, jobTrigger);
		} catch (org.quartz.SchedulerException e) {
			throw new SchedulerException("Error scheduling with quartz", e);
		}
	}

	public List<String> listGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	public void startScheduler() throws SchedulerException {
		// TODO Auto-generated method stub

	}

	public void stopScheduler() throws SchedulerException {
		// TODO Auto-generated method stub

	}

	public Date getNextRunTime(String jobName, String groupName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean isJobActive(String jobName, String groupName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> listJobs(String groupName) {
		// TODO Auto-generated method stub
		return null;
	}

	public void invokeJob(String jobName, String groupName)
			throws SchedulerException {
		// TODO Auto-generated method stub

	}

	public Boolean isSchedulerActive() throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

	public void pauseJob(String jobName, String groupName)
			throws SchedulerException {
		// TODO Auto-generated method stub

	}

	public void removeJob(String jobName, String groupName)
			throws SchedulerException {
		// TODO Auto-generated method stub

	}

	public void resumeJob(String jobName, String groupName)
			throws SchedulerException {
		// TODO Auto-generated method stub

	}

	public RunnerJob getRunnerJob(String jobName, String groupName)
			throws SchedulerException {
		// TODO Auto-generated method stub
		return null;
	}

}
