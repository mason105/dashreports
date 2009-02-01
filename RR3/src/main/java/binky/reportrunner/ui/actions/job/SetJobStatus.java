package binky.reportrunner.ui.actions.job;

import org.apache.log4j.Logger;

import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class SetJobStatus extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SetJobStatus.class);
	private RunnerJobService jobService;
	private String jobName;
	private String groupName;
	private Boolean jobStatus;

	public Boolean getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(Boolean jobStatus) {
		this.jobStatus = jobStatus;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String execute() throws Exception {
		logger.info("setting status of job: " + groupName + "." + jobName + " to " + jobStatus);
		if (groupName != null && !groupName.isEmpty()
				&& (jobName != null && !jobName.isEmpty())) {
			// security check
			if (doesUserHaveGroup(groupName)) {
				if (jobStatus) {
					logger.debug("resume job");
					jobService.resumeJob(jobName, groupName);
				} else {
					logger.debug("pause job");
					jobService.pauseJob(jobName, groupName);

				}
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
