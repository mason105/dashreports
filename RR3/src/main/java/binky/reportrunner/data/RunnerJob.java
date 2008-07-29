package binky.reportrunner.data;

import java.io.Serializable;
import java.util.Map;

public class RunnerJob implements Serializable {

	private static final long serialVersionUID = 2036013437864145537L;
	
	private String jobName;
	private String groupName;
	private String outputUrl;
	private String runnerEngine;
	private RunnerDataSource datasource;
	private Map<String,Object> engineParameters;
	private String cronString;
	
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getRunnerEngine() {
		return runnerEngine;
	}
	public void setRunnerEngine(String runnerEngine) {
		this.runnerEngine = runnerEngine;
	}
	public Map<String, Object> getEngineParameters() {
		return engineParameters;
	}
	public void setEngineParameters(Map<String, Object> engineParameters) {
		this.engineParameters = engineParameters;
	}
	public String getOutputUrl() {
		return outputUrl;
	}
	public void setOutputUrl(String outputUrl) {
		this.outputUrl = outputUrl;
	}
	public RunnerDataSource getDatasource() {
		return datasource;
	}
	public void setDatasource(RunnerDataSource datasource) {
		this.datasource = datasource;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getCronString() {
		return cronString;
	}
	public void setCronString(String cronString) {
		this.cronString = cronString;
	}	
	
	
}
