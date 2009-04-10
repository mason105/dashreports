package binky.reportrunner.ui.actions.admin;

import binky.reportrunner.dao.RunnerHistoryDao;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class SetupJobStatsAction extends AdminRunnerAction {

	private static final long serialVersionUID = 1L;
	private RunnerHistoryDao historyDao;
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return SUCCESS;
	}
	public RunnerHistoryDao getHistoryDao() {
		return historyDao;
	}
	public void setHistoryDao(RunnerHistoryDao historyDao) {
		this.historyDao = historyDao;
	}

}
