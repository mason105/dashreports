package binky.reportrunner.ui.actions.job;

import binky.reportrunner.dao.RunnerHistoryDao;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class ViewJobDetail extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;

	private RunnerHistoryDao historyDao;

	private RunnerJobService jobService;

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public final RunnerJobService getJobService() {
		return jobService;
	}

	public final void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}

	public final RunnerHistoryDao getHistoryDao() {
		return historyDao;
	}

	public final void setHistoryDao(RunnerHistoryDao historyDao) {
		this.historyDao = historyDao;
	}

}
