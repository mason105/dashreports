package binky.reportrunner.util;

import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.Configuration.ConfigurationType;
import binky.reportrunner.data.RunnerHistoryEvent;
import binky.reportrunner.service.ConfigurationService;

public class PruneEventHistory extends QuartzJobBean {

	@SuppressWarnings("unchecked")
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {

		ReportRunnerDao<RunnerHistoryEvent, Long> historyDao = (ReportRunnerDao<RunnerHistoryEvent, Long>) ApplicationContextProvider
				.getApplicationContext().getBean("runnerHistoryDao");
		ConfigurationService configurationService = (ConfigurationService) ApplicationContextProvider
				.getApplicationContext().getBean("configurationService");
		int daysToKeep = Integer.parseInt(configurationService
				.getConfigurationItem(ConfigurationType.AUDIT_PURGE_DAYS)
				.getValue());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 0 - daysToKeep);
		Date oldest = cal.getTime();
		// TODO:refactor
		for (RunnerHistoryEvent e : historyDao.findByNamedQuery("getOldEvents",
				new Object[] { oldest })) {
			historyDao.delete(e.getEventId());
		}
	}

}
