package binky.reportrunner.ui.actions.job;

import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class SetupEditJob extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;
	private String jobName;
	private String groupName;
	private RunnerJob job;
	private static final Logger logger = Logger.getLogger(SetupEditJob.class);
	private RunnerJobService jobService;

	@Override
	public String execute() throws Exception {
		if (groupName != null && !groupName.isEmpty()
				&& (jobName != null && !jobName.isEmpty())) {
			// security check
			if (super.getUser().getGroups().contains(groupName)) {
				job = jobService.getJob(jobName, groupName);
			} else {
				SecurityException se = new SecurityException("Group "
						+ groupName + " not valid for user "
						+ super.getUser().getUserName());
				logger.fatal(se.getMessage(), se);
				throw se;
			}

		} else {
			job = new RunnerJob();
		}
		return SUCCESS;
	}

	public final RunnerJobService getJobService() {
		return jobService;
	}

	public final void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}

	public final String getJobName() {
		return jobName;
	}

	public final void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public final String getGroupName() {
		return groupName;
	}

	public final void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public final RunnerJob getJob() {
		return job;
	}

	public final void setJob(RunnerJob job) {
		this.job = job;
	}

}
