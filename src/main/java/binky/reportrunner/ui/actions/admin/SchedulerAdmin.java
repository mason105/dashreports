package binky.reportrunner.ui.actions.admin;

import java.util.Date;

import org.quartz.SchedulerMetaData;

import binky.reportrunner.scheduler.Scheduler;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class SchedulerAdmin extends AdminRunnerAction {

	private static final long serialVersionUID = 1L;
	
	private Scheduler scheduler;
	
	private int jobCount;
	
	private String instanceId;
	
	private String schedulerName;
	
	private String summary;
	
	private String version;
	
	private int threadPoolSize;
	private int jobsExectuted;
	private Date runningSince;
	
	private int schedulerState;
	
	@Override
	public String execute() throws Exception {
	
		SchedulerMetaData meta= scheduler.getMetaData();
		this.instanceId=meta.getSchedulerInstanceId();
		this.schedulerName=meta.getSchedulerName();
		
		this.jobCount=scheduler.getJobCount();
		
		this.summary=meta.getSummary();
		
		this.version =  meta.getVersion();
		
		this.threadPoolSize=meta.getThreadPoolSize();
		
		this.jobsExectuted=meta.numJobsExecuted();
		
		this.runningSince=meta.runningSince();
		
		if (meta.isStarted()) {
			this.schedulerState=1;
		}
		if (meta.isShutdown()) {
			this.schedulerState=0;
		}
	
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

	public String getInstanceId() {
		return instanceId;
	}

	public String getSchedulerName() {
		return schedulerName;
	}

	public String getSummary() {
		return summary;
	}

	public String getVersion() {
		return version;
	}

	public int getThreadPoolSize() {
		return threadPoolSize;
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
	
	
}
