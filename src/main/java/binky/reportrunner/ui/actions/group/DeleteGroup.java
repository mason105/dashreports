package binky.reportrunner.ui.actions.group;

import java.util.List;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class DeleteGroup extends AdminRunnerAction {

	private RunnerGroupDao groupDao;
	private RunnerJobService jobService;
	private String groupName;
	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		if (super.getSessionUser().getGroups().contains(groupName)
				|| super.getSessionUser().getIsAdmin()) {

			List<RunnerJob> jobs = jobService.listJobs(groupName);
			if ((jobs != null)
					&& (jobs.size() > 0)) {
				for (RunnerJob job : jobs) {
					jobService.deleteJob(job.getPk().getJobName(), groupName);
				}
			}
			groupDao.deleteGroup(groupName);
		} else {
			SecurityException se = new SecurityException("Group " + groupName
					+ " not valid for user " + super.getSessionUser().getUserName());
			throw se;
		}
		return SUCCESS;
	}

	public RunnerGroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(RunnerGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public RunnerJobService getJobService() {
		return jobService;
	}

	public void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}

}
