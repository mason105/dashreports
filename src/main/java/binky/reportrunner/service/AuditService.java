package binky.reportrunner.service;

import java.util.Date;
import java.util.List;

import binky.reportrunner.data.RunnerHistoryEvent;
import binky.reportrunner.data.RunnerHistoryEvent.Module;

public interface AuditService {

	public void logAuditEvent(Module module, String message, String userName, boolean success, long runTime,String jobName, String groupName);
	
	public List<RunnerHistoryEvent> getEventsByUserName(String userName, int count); 
	public List<RunnerHistoryEvent> getEventsByModule(Module module, int count); 
	public List<RunnerHistoryEvent> getEventsByJob(String jobName, String groupName, int count);	
	public List<RunnerHistoryEvent> getFailedEvents(Module module, int count);
	public List<RunnerHistoryEvent> getLongestRunningEvents(Module module, int count);
	public List<RunnerHistoryEvent> getSuccessEvents(Module module, int count);
	public void deleteOldEvents(Date cutOff);
	
	
}
