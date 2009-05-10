package binky.reportrunner.ui.actions.admin;

import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class InterruptCurrentExecutingJobAction extends AdminRunnerAction {

	private static final long serialVersionUID = 1L;
	private RunnerJobService jobService;
	private String jobName;
	private String groupName;
	@Override
	public String execute() throws Exception {
		jobService.interruptRunningJob(jobName, groupName);
		return SUCCESS;
	}

	public RunnerJobService getJobService() {
		return jobService;
	}

	public void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

}
