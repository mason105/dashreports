package binky.reportrunner.util;

import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import binky.reportrunner.dao.RunnerHistoryDao;

public class PruneEventHistory extends QuartzJobBean {

	private int daysToKeep;
	private RunnerHistoryDao historyDao;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 0-daysToKeep);
		Date oldest = cal.getTime(); 
		historyDao.deleteRangeOfEvents(oldest);
	}

	public int getDaysToKeep() {
		return daysToKeep;
	}

	public void setDaysToKeep(int daysToKeep) {
		this.daysToKeep = daysToKeep;
	}

	public RunnerHistoryDao getHistoryDao() {
		return historyDao;
	}

	public void setHistoryDao(RunnerHistoryDao historyDao) {
		this.historyDao = historyDao;
	}

	
}
