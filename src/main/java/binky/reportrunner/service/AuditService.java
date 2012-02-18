package binky.reportrunner.service;

import java.util.Date;
import java.util.List;

import binky.reportrunner.data.RunnerHistoryEvent;

public interface AuditService {

	public void logAuditEvent(String module,  boolean success, long runTime,String arguments, String method,String errorText);

	
	public List<RunnerHistoryEvent> getFailedEvents(String module, int count);
	public List<RunnerHistoryEvent> getLongestRunningEvents(String module, int count);
	public List<RunnerHistoryEvent> getSuccessEvents(String module, int count);
	public void deleteOldEvents(Date cutOff);
	public List<String> getModuleNames();
	
}
