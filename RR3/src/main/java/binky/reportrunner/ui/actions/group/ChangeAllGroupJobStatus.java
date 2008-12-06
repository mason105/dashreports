package binky.reportrunner.ui.actions.group;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class ChangeAllGroupJobStatus  extends StandardRunnerAction {

	private RunnerGroupDao groupDao;
	private RunnerJobService jobService;
	private String groupName;
	private Boolean status;
	public void setStatus(Boolean status) {
		this.status = status;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		RunnerGroup group = groupDao.getGroup(groupName);
		if (super.getUser().getGroups().contains(group)) {			
			for (RunnerJob job : group.getRunnerJobs()) {
				if (status) {
					jobService.resumeJob(job.getPk().getJobName(), groupName);
					
				} else {
					jobService.pauseJob(job.getPk().getJobName(), groupName);
				}
			}
		} else {
			SecurityException se = new SecurityException("Group " + groupName + " not valid for user " + super.getUser().getUserName());
		//	logger.fatal(se.getMessage(),se);
			throw se;
		}		
		return SUCCESS;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public RunnerGroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(RunnerGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public RunnerJobService getJobService() {
		return jobService;
	}

	public void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}


}
