package binky.reportrunner.scheduler.impl;

import java.util.List;

import org.quartz.SchedulerFactory;

import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.scheduler.Scheduler;
import binky.reportrunner.scheduler.SchedulerException;

public class SchedulerImpl implements Scheduler {

	private SchedulerFactory quartzFactory;

	public void addJob(RunnerJob job) throws SchedulerException {
		// TODO Auto-generated method stub

	}

	public void invokeJob(RunnerJob job) throws SchedulerException {
		// TODO Auto-generated method stub
	}

	public void pauseJob(RunnerJob job) throws SchedulerException {
		// TODO Auto-generated method stub

	}

	public void removeJob(RunnerJob job) throws SchedulerException {
		// TODO Auto-generated method stub

	}

	public void resumeJob(RunnerJob job) throws SchedulerException {
		// TODO Auto-generated method stub

	}

	public void startScheduler() throws SchedulerException {
		// TODO Auto-generated method stub

	}

	public void stopScheduler() throws SchedulerException {
		// TODO Auto-generated method stub

	}

	public List<RunnerJob> listJobs() {
		// TODO Auto-generated method stub
		return null;
	}

	public SchedulerFactory getQuartzFactory() {
		return quartzFactory;
	}

	public void setQuartzFactory(SchedulerFactory quartzFactory) {
		this.quartzFactory = quartzFactory;
	}

}
