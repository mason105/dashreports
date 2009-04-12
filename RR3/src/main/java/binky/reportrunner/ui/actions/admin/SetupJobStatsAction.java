package binky.reportrunner.ui.actions.admin;

import java.util.List;

import binky.reportrunner.dao.RunnerHistoryDao;
import binky.reportrunner.data.RunnerHistoryEvent;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class SetupJobStatsAction extends AdminRunnerAction {

	private static final long serialVersionUID = 1L;
	private RunnerHistoryDao historyDao;
	private List<RunnerHistoryEvent> longestEvents;
	private List<RunnerHistoryEvent> latestSuccessEvents;
	private List<RunnerHistoryEvent> latestFailEvents;
	
	
	@Override
	public String execute() throws Exception {

		this.longestEvents = historyDao.getLongestRunningEvents(20);
		this.latestSuccessEvents = historyDao.getSuccessEvents(20);
		this.latestFailEvents = historyDao.getFailEvents(20);
		
		return SUCCESS;
	}
	public RunnerHistoryDao getHistoryDao() {
		return historyDao;
	}
	public void setHistoryDao(RunnerHistoryDao historyDao) {
		this.historyDao = historyDao;
	}
	public List<RunnerHistoryEvent> getLatestFailEvents() {
		return latestFailEvents;
	}
	public List<RunnerHistoryEvent> getLatestSuccessEvents() {
		return latestSuccessEvents;
	}
	public List<RunnerHistoryEvent> getLongestEvents() {
		return longestEvents;
	}
	public void setLatestFailEvents(List<RunnerHistoryEvent> latestFailEvents) {
		this.latestFailEvents = latestFailEvents;
	}
	public void setLatestSuccessEvents(List<RunnerHistoryEvent> latestSuccessEvents) {
		this.latestSuccessEvents = latestSuccessEvents;
	}
	public void setLongestEvents(List<RunnerHistoryEvent> longestEvents) {
		this.longestEvents = longestEvents;
	}

}
