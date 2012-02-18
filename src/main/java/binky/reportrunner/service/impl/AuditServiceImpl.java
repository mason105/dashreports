package binky.reportrunner.service.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.googlecode.ehcache.annotations.Cacheable;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerHistoryEvent;
import binky.reportrunner.service.AuditService;

public class AuditServiceImpl implements AuditService {

	private static final Logger logger = Logger.getLogger(AuditServiceImpl.class); 
	
	private ReportRunnerDao<RunnerHistoryEvent, Long> historyDao;


	@Override
	public List<RunnerHistoryEvent> getFailedEvents(String module, Date from, Date to) {
		
			return historyDao.findByNamedQuery("getFailedEvents", new Object[]{module,from,to});
		

	}

	@Override
	public List<RunnerHistoryEvent> getLongestRunningEvents(String module,Date from, Date to) {
		
			return historyDao.findByNamedQuery("getLongestRunningEvents", new Object[]{module,from,to},50);
		

	}

	@Override
	public void deleteOldEvents(Date cutOff) {
		List<RunnerHistoryEvent> events = historyDao.findByNamedQuery("getOldEvents", new Object[]{cutOff});
		for (RunnerHistoryEvent e: events) {
			historyDao.delete(e.getEventId());
		}
	}

	@Override
	public List<RunnerHistoryEvent> getSuccessEvents(String module, Date from, Date to) {
		
			return historyDao.findByNamedQuery("getSuccessEvents", new Object[]{module,from,to});
		

	}

	@Override
	public void logAuditEvent(String module, boolean success, long runTime,String arguments, String method,String errorText) {
	
			String userName="";
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth!=null) userName=auth.getName();
	
		RunnerHistoryEvent event = new RunnerHistoryEvent(Calendar.getInstance().getTime(), success, runTime, userName, module, arguments, method,errorText);
		logger.trace("logging audit message: " + event.toString());
		historyDao.saveOrUpdate(event);
	}
	public void setHistoryDao(ReportRunnerDao<RunnerHistoryEvent, Long> historyDao) {
		this.historyDao = historyDao;
	}

	@Override
	@Cacheable(cacheName="auditCache")
	public List<String> getModuleNames() {
		return Arrays.asList(new String[]{"Scheduler","UserService","GroupService","DashboardService","DatasourceService","ReportService","GroupService","UserDetailsService"});
	}

}
