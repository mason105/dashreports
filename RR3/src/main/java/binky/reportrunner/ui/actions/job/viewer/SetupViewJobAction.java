package binky.reportrunner.ui.actions.job.viewer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class SetupViewJobAction extends StandardRunnerAction {

	private static final long serialVersionUID = 7570287447973430981L;

	private String jobName;
	private String groupName;
	private Map<RunnerJobParameter, List<Object>> parameters;
	private RunnerJobService jobService;
	
	@Override
	public String execute() throws Exception {
		//parameters = jobService.getPossibleParameterValues(jobName, groupName);
		
		//temporary until i figure out what to do with this auto population
		parameters= new HashMap<RunnerJobParameter, List<Object>>();
		for (RunnerJobParameter p : jobService.getJob(jobName, groupName).getParameters()) {
			parameters.put(p, null);
		}
		
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

	public Map<RunnerJobParameter, List<Object>> getParameters() {
		return parameters;
	}

	public void setParameters(Map<RunnerJobParameter, List<Object>> parameters) {
		this.parameters = parameters;
	}
	
}
