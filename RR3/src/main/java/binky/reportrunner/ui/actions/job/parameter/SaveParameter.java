package binky.reportrunner.ui.actions.job.parameter;

import java.util.List;

import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.data.RunnerJobParameter_pk;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class SaveParameter extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;

	private RunnerJobService jobService;
	private String jobName;
	private String groupName;
	private Integer parameterIdx;
	private String parameterValue;
	private String parameterBurstColumn;
	private Integer parameterType;

	@Override
	public String execute() throws Exception {

		RunnerJob job = jobService.getJob(jobName, groupName);

		//create the parameter bean
		RunnerJobParameter_pk pk = new RunnerJobParameter_pk();
		pk.setRunnerJob(job);
		pk.setParameterIdx(parameterIdx);
		RunnerJobParameter parameter = new RunnerJobParameter();
		parameter.setPk(pk);
		parameter.setParameterType(parameterType);
		parameter.setParameterValue(parameterValue);
		parameter.setParameterBurstColumn(parameterBurstColumn);
		
		List<RunnerJobParameter> paramList = job.getParameters();

		//if there parameter already exists pop it out the list
		int c = 0;
		for (RunnerJobParameter paramCheck : paramList) {
			c++;
			if (paramCheck.getPk().getParameterIdx().equals(this.parameterIdx)) {
				paramList.remove(c);
			}
		}

		//stick our parameter in the list - if it was existing it's gone now
		paramList.add(parameter);
		
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

	public void setParameterType(Integer parameterType) {
		this.parameterType = parameterType;
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

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	public void setParameterBurstColumn(String parameterBurstColumn) {
		this.parameterBurstColumn = parameterBurstColumn;
	}

}
