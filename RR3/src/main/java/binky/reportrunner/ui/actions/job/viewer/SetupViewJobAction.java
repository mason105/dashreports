package binky.reportrunner.ui.actions.job.viewer;

import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class SetupViewJobAction extends StandardRunnerAction {

	private static final long serialVersionUID = 7570287447973430981L;

	private String jobName;
	private String groupName;
	private RunnerJob job;
	private RunnerJobService jobService;
	
	@Override
	public String execute() throws Exception {
		job = jobService.getJob(jobName, groupName);
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

	public RunnerJob getJob() {
		return job;
	}

	public void setJob(RunnerJob job) {
		this.job = job;
	}
	
}
