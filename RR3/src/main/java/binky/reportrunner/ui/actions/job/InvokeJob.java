package binky.reportrunner.ui.actions.job;

import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class InvokeJob extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;

	private RunnerJobService jobService;
	private String jobName;
	private String groupName;

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String execute() throws Exception {
		if (groupName != null && !groupName.isEmpty()
				&& (jobName != null && !jobName.isEmpty())) {
			// security check
			if (doesUserHaveGroup(groupName)) {

				jobService.invokeJob(jobName, groupName);

			} else {
				SecurityException se = new SecurityException("Group "
						+ groupName + " not valid for user "
						+ super.getSessionUser().getUserName());
				// logger.fatal(se.getMessage(), se);
				throw se;
			}
		}
		return SUCCESS;
	}

	public final RunnerJobService getJobService() {
		return jobService;
	}

	public final void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}

	public String getGroupName() {
		return groupName;
	}

}
