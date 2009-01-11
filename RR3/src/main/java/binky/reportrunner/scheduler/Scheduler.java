package binky.reportrunner.scheduler;

import java.util.Date;
import java.util.List;

public interface Scheduler {

	public void startScheduler() throws SchedulerException;

	public void stopScheduler() throws SchedulerException;
	
	public Boolean isSchedulerActive()  throws SchedulerException;

	public void addJob(String jobName, String groupName, String className,String cronString,
			Date startDate, Date endDate) throws SchedulerException;
	
	public void removeJob(String jobName, String groupName) throws SchedulerException;
	
	public void pauseJob(String jobName, String groupName) throws SchedulerException;
	
	public void resumeJob(String jobName, String groupName) throws SchedulerException;

	public void invokeJob(String jobName, String groupName) throws SchedulerException;
	
	public Date getNextRunTime(String jobName, String groupName)  throws SchedulerException;
	
	public Date getPreviousRunTime(String jobName, String groupName)  throws SchedulerException;
	
	public Boolean isJobActive(String jobName, String groupName)  throws SchedulerException;
		
	public void interruptRunningJob(String jobName, String groupName)  throws SchedulerException;
	
	public List<String> getCurrentRunningJobs();
	
	public Boolean isScheduled(String jobName, String groupName)
	throws SchedulerException;
	
	public void pauseGroup(String groupName) throws SchedulerException;
	public void resumeGroup(String groupName) throws SchedulerException;	

}
