package binky.reportrunner.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import binky.reportrunner.dao.RunnerHistoryDao;

public class RunnerJobListener implements JobListener {

	private RunnerHistoryDao runnerHistoryDao;
	
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void jobExecutionVetoed(JobExecutionContext arg0) {
		// TODO Auto-generated method stub

	}

	public void jobToBeExecuted(JobExecutionContext arg0) {
		// TODO Auto-generated method stub

	}

	public void jobWasExecuted(JobExecutionContext arg0,
			JobExecutionException arg1) {
		// TODO Auto-generated method stub

	}

	public RunnerHistoryDao getRunnerHistoryDao() {
		return runnerHistoryDao;
	}

	public void setRunnerHistoryDao(RunnerHistoryDao runnerHistoryDao) {
		this.runnerHistoryDao = runnerHistoryDao;
	}


}
