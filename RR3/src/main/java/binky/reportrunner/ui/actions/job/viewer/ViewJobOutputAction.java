package binky.reportrunner.ui.actions.job.viewer;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class ViewJobOutputAction extends StandardRunnerAction {

	private static final long serialVersionUID = 2677747746707641721L;

	private String jobName;

	private String groupName;

	private List<RunnerJobParameter> parameters;

	private Map<String, RowSetDynaClass> results;

	private RunnerJobService jobService;
	private static final Logger logger = Logger.getLogger(ViewJobOutputAction.class);
	@Override
	public String execute() throws Exception {

		if ((parameters != null) && (parameters.size() > 0)) {
			results = jobService.getResultsForJob(jobName, groupName,
					parameters);
		} else {
			results = jobService.getResultsForJob(jobName, groupName);
		}
		
		if (logger.isDebugEnabled()) {
			//Quick debuggering
			for (String key:results.keySet()) {
				List rows= results.get(key).getRows();
				logger.debug("key="+key+" row count="+rows.size());
				for (Object o:rows) {
					logger.debug("key="+key+" row=" + o);
				}
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

	public List<RunnerJobParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<RunnerJobParameter> parameters) {
		this.parameters = parameters;
	}

	public Map<String, RowSetDynaClass> getResults() {
		return results;
	}

	public void setResults(Map<String, RowSetDynaClass> results) {
		this.results = results;
	}

}
