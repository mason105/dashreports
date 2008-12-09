package binky.reportrunner.ui.actions.job;

import org.apache.log4j.Logger;

import binky.reportrunner.exceptions.InvalidParameterException;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class DeleteJob extends StandardRunnerAction {
	private static final Logger logger = Logger.getLogger(DeleteJob.class);
	private static final long serialVersionUID = 1L;

	private RunnerJobService jobService;
	private String groupName;
	private String jobName;
	
	@Override
	public String execute() throws Exception {
		if (groupName != null && !groupName.isEmpty()
				&& (jobName != null && !jobName.isEmpty())) {
			// security check
			if (super.getUser().getGroups().contains(groupName) || super.getUser().getIsAdmin()) {
				jobService.deleteJob(jobName, groupName);
			} else {
				SecurityException se = new SecurityException("Group "
						+ groupName + " not valid for user "
						+ super.getUser().getUserName());
				logger.fatal(se.getMessage(), se);
				throw se;
			}

		} else {
			InvalidParameterException e = new InvalidParameterException("one or more parameters are invalid!");
			logger.error(e);
			throw e;
		}	
		return SUCCESS;
	}

	public final RunnerJobService getJobService() {
		return jobService;
	}

	public final void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}

	public final String getGroupName() {
		return groupName;
	}

	public final void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public final String getJobName() {
		return jobName;
	}

	public final void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
}
