package binky.reportrunner.util;

import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerHistoryEvent;

public class PruneEventHistory extends QuartzJobBean {

	private int daysToKeep;
	private ReportRunnerDao<RunnerHistoryEvent,Long> historyDao;
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
		 historyDao = ( ReportRunnerDao<RunnerHistoryEvent,Long> ) appCtx.getBean("runnerHistoryDao");
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 0 - daysToKeep);
		Date oldest = cal.getTime();
		//TODO:refactor
		for (RunnerHistoryEvent e:  historyDao.findByNamedQuery("getOldEvents", new Object[]{oldest})) {
			historyDao.delete(e.getEventId());
		}
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

	public void setHistoryDao( ReportRunnerDao<RunnerHistoryEvent,Long>  historyDao) {
		this.historyDao = historyDao;
	}

}
