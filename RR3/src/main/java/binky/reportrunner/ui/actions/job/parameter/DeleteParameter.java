package binky.reportrunner.ui.actions.job.parameter;

import java.util.List;

import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class DeleteParameter extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;

	private RunnerJobService jobService;
	private String jobName;
	private String groupName;
	private Integer parameterIdx;

	@Override
	public String execute() throws Exception {

		RunnerJob job = jobService.getJob(jobName, groupName);
		
		List<RunnerJobParameter> paramList = job.getParameters();

		//if there parameter already exists pop it out the list
		int c = 0;
		for (RunnerJobParameter paramCheck : paramList) {
			c++;
			if (paramCheck.getPk().getParameterIdx().equals(this.parameterIdx)) {
				paramList.remove(c);
			}
		}

		//pop the list back into the job bean
		job.setParameters(paramList);
		
		// security check
		if (super.getUser().getGroups().contains(groupName)) {
			jobService.addUpdateJob(job);
		} else {
			SecurityException se = new SecurityException("Group " + groupName
					+ " not valid for user " + super.getUser().getUserName());
			// logger.fatal(se.getMessage(), se);
			throw se;
		}

		return SUCCESS;
	}

	public RunnerJobService getJobService() {
		return jobService;
	}

	public void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
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
