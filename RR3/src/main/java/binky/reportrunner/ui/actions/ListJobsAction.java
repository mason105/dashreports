package binky.reportrunner.ui.actions;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.RunnerJobDao;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class ListJobsAction extends StandardRunnerAction {

	private static final long serialVersionUID = 6919067344312363024L;
	private String groupName;

	private static Logger logger = Logger.getLogger(ListJobsAction.class);

	private List<RunnerJob> jobs;
	private RunnerJobDao jobDao;
	@Override
	public String execute() throws Exception {
		if ((groupName != null) && (!groupName.isEmpty())) {
			logger.debug("looking for group: " + groupName);
			this.jobs=jobDao.listJobs(groupName);		
		} else {
			this.jobs = new LinkedList<RunnerJob>();
		}
		return SUCCESS;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<RunnerJob> getJobs() {
		return jobs;
	}

	public final RunnerJobDao getJobDao() {
		return jobDao;
	}

	public final void setJobDao(RunnerJobDao jobDao) {
		this.jobDao = jobDao;
	}

	
}
