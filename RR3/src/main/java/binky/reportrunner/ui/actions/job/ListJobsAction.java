package binky.reportrunner.ui.actions.job;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.dao.RunnerJobDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class ListJobsAction extends StandardRunnerAction {

	private static final long serialVersionUID = 6919067344312363024L;
	private String groupName;
	private String jobName="";
	
	private static Logger logger = Logger.getLogger(ListJobsAction.class);

	private List<RunnerJob> jobs;
	private RunnerJobDao jobDao;
	private RunnerGroupDao groupDao;
	@Override
	public String execute() throws Exception {
		if ((groupName != null) && (!groupName.isEmpty())) {
			logger.debug("looking for group: " + groupName);
				RunnerGroup group = groupDao.getGroup(groupName);
				if (super.getUser().getGroups().contains(group)) {			
					this.jobs=jobDao.listJobs(groupName);		
				} else {
					SecurityException se = new SecurityException("Group " + groupName + " not valid for user " + super.getUser().getUserName());
					logger.fatal(se.getMessage(),se);
					throw se;
				}
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

	public final String getJobName() {
		return jobName;
	}

	public final void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public RunnerGroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(RunnerGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	
	
}
