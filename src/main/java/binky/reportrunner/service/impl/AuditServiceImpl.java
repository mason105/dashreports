package binky.reportrunner.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerHistoryEvent;
import binky.reportrunner.data.RunnerHistoryEvent.Module;
import binky.reportrunner.service.AuditService;

public class AuditServiceImpl implements AuditService {

	private static final Logger logger = Logger.getLogger(AuditServiceImpl.class); 
	
	private ReportRunnerDao<RunnerHistoryEvent, Long> historyDao;
	
	@Override
	public List<RunnerHistoryEvent> getEventsByJob(String jobName,
			String groupName, int count) {
		if (count == 0) {
			return historyDao.findByNamedQuery("getEventsByJob", new String[]{jobName,groupName});
		} else {
			return historyDao.findByNamedQuery("getEventsByJob", new String[]{jobName,groupName},count);
		}
	}

	@Override
	public List<RunnerHistoryEvent> getEventsByModule(Module module, int count) {
		if (count == 0) {
			return historyDao.findByNamedQuery("getEventsByModule", new Module[]{module});
		} else {
			return historyDao.findByNamedQuery("getEventsByModule", new Module[]{module},count);
		}
	}

	@Override
	public List<RunnerHistoryEvent> getEventsByUserName(String userName, int count) {
		if (count == 0) {
			return historyDao.findByNamedQuery("getEventsByUserName", new String[]{userName});
		} else {
			return historyDao.findByNamedQuery("getEventsByUserName", new String[]{userName},count);
		}

	}

	@Override
	public List<RunnerHistoryEvent> getFailedEvents(int count) {
		if (count == 0) {
			return historyDao.findByNamedQuery("getFailedEvents", null);
		} else {
			return historyDao.findByNamedQuery("getFailedEvents", null,count);
		}

	}

	@Override
	public List<RunnerHistoryEvent> getLongestRunningEvents(int count) {
		if (count == 0) {
			return historyDao.findByNamedQuery("getLongestRunningEvents", null);
		} else {
			return historyDao.findByNamedQuery("getLongestRunningEvents", null,count);
		}

	}

	@Override
	public void deleteOldEvents(Date cutOff) {
		List<RunnerHistoryEvent> events = historyDao.findByNamedQuery("getOldEvents", new Object[]{cutOff});
		for (RunnerHistoryEvent e: events) {
			historyDao.delete(e.getEventId());
		}
	}

	@Override
	public List<RunnerHistoryEvent> getSuccessEvents(int count) {
		if (count == 0) {
			return historyDao.findByNamedQuery("getSuccessEvents", null);
		} else {
			return historyDao.findByNamedQuery("getSuccessEvents", null,count);
		}

	}

	@Override
	public void logAuditEvent(Module module, String message,
			String userName, boolean success, long runTime, String jobName,
			String groupName) {

		RunnerHistoryEvent event = new RunnerHistoryEvent(Calendar.getInstance().getTime(), jobName, groupName, message, success, runTime,userName, module);	
		logger.trace("logging audit message: " + event.toString());
		historyDao.saveOrUpdate(event);
		
	}

	public void setHistoryDao(ReportRunnerDao<RunnerHistoryEvent, Long> historyDao) {
		this.historyDao = historyDao;
	}

}
