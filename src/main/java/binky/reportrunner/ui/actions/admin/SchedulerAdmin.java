package binky.reportrunner.ui.actions.admin;

import java.util.Date;

import binky.reportrunner.scheduler.Scheduler;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class SchedulerAdmin extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;
	
	private Scheduler scheduler;
	
	private int jobCount;
	
	private String summary;
	
	private int jobsExectuted;
	private Date runningSince;
	
	private int schedulerState;
	
	@Override
	public String execute() throws Exception {
	
		
		this.jobCount=scheduler.getJobCount();
		
		this.summary=scheduler.getSummary();
		
		this.runningSince=scheduler.getActiveFrom();
		this.jobsExectuted=scheduler.getJobsExecuted();
		
		this.schedulerState=scheduler.isSchedulerActive() ? 1:0;
		
		return SUCCESS;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public int getJobCount() {
		return jobCount;
	}


	public int getJobsExectuted() {
		return jobsExectuted;
	}

	public Date getRunningSince() {
		return runningSince;
	}

	public int getSchedulerState() {
		return schedulerState;
	}
	
	public String getSummary() {
		return summary;
	}
	
}
