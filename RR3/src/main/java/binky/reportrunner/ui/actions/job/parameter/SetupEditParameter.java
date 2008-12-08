package binky.reportrunner.ui.actions.job.parameter;

import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class SetupEditParameter  extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;

	private RunnerJobService jobService;
	private String jobName;
	private String groupName;
	private Integer parameterIdx;
	private RunnerJobParameter parameter;
	
	@Override
	public String execute() throws Exception {
		RunnerJob job =jobService.getJob(jobName, groupName);
		parameter=new RunnerJobParameter();
		for (RunnerJobParameter parameter: job.getParameters()) {
			if (parameter.getPk().getParameterIdx().equals(this.parameterIdx)) {
				this.parameter=parameter;
			}
		}
		return SUCCESS;
	}

	public RunnerJobService getJobService() {
		return jobService;
	}

	public void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}

	public RunnerJobParameter getParameter() {
		return parameter;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setParameterIdx(Integer parameterIdx) {
		this.parameterIdx = parameterIdx;
	}

}
