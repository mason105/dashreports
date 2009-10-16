package binky.reportrunner.util;

import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import binky.reportrunner.dao.RunnerHistoryDao;

public class PruneEventHistory extends QuartzJobBean {

	private int daysToKeep;
	private RunnerHistoryDao historyDao;
	private static final String APPLICATION_CONTEXT_KEY = "applicationContext";

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		 ApplicationContext appCtx;
		try {
			appCtx = getApplicationContext(context);
		} catch (SchedulerException e) {
			throw new JobExecutionException(e);
		}        
		 historyDao = (RunnerHistoryDao) appCtx.getBean("runnerHistoryDao");
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 0 - daysToKeep);
		Date oldest = cal.getTime();
		historyDao.deleteRangeOfEvents(oldest);
	}

	private ApplicationContext getApplicationContext(JobExecutionContext context)
			throws JobExecutionException,SchedulerException {
		ApplicationContext appCtx = null;
		appCtx = (ApplicationContext) context.getScheduler().getContext().get(
				APPLICATION_CONTEXT_KEY);
		if (appCtx == null) {
			throw new JobExecutionException(
					"No application context available in scheduler context for key \""
							+ APPLICATION_CONTEXT_KEY + "\"");
		}
		return appCtx;
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
