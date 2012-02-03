package binky.reportrunner.service;

import java.util.Date;
import java.util.List;

import binky.reportrunner.data.RunnerHistoryEvent;

public interface AuditService {

	public void logAuditEvent(String moduleName, String message, String userName, boolean success, long runTime,String jobName, String groupName);
	
	public List<RunnerHistoryEvent> getEventsByUserName(String userName, int count); 
	public List<RunnerHistoryEvent> getEventsByModule(String moduleName, int count); 
	public List<RunnerHistoryEvent> getEventsByJob(String jobName, String groupName, int count);	
	public List<RunnerHistoryEvent> getFailedEvents(int count);
	public List<RunnerHistoryEvent> getLongestRunningEvents(int count);
	public List<RunnerHistoryEvent> getSuccessEvents(int count);
	public void deleteOldEvents(Date cutOff);
	
	
}
