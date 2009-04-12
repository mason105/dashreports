package binky.reportrunner.dao;

import java.util.Date;
import java.util.List;

import binky.reportrunner.data.RunnerHistoryEvent;

public interface RunnerHistoryDao {

	public List<RunnerHistoryEvent> getEvents(String groupName, String jobName);
	public List<RunnerHistoryEvent> getEvents(String groupName, String jobName, 
			Date startTime, Date endTime);
	public void saveEvent(RunnerHistoryEvent event);
	public List<RunnerHistoryEvent> getLongestRunningEvents(int eventCount);
	public List<RunnerHistoryEvent> getSuccessEvents(int eventCount);
	public List<RunnerHistoryEvent> getFailEvents(int eventCount);
	
	/**
	 * 
	 * Only really used to clean up after the test
	 * 
	 * @param eventId
	 */
	public void deleteEvents(String groupName, String jobName);
}
