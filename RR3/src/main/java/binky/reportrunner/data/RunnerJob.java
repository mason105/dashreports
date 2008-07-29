package binky.reportrunner.data;

import java.io.Serializable;
import java.util.Map;

public class RunnerJob implements Serializable {

	private static final long serialVersionUID = 2036013437864145537L;
	
	private String jobName;
	private Boolean isBurst;
	
	//Bursting configuration
	private String burstQuery;
	private String burstDatasource;
	private String burstColumnName;
	private String burstParameterName; 
	
	private String runnerEngine;
	private String datasourceName;
	private Map<String,Object> engineParameters;
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public Boolean getIsBurst() {
		return isBurst;
	}
	public void setIsBurst(Boolean isBurst) {
		this.isBurst = isBurst;
	}
	public String getBurstQuery() {
		return burstQuery;
	}
	public void setBurstQuery(String burstQuery) {
		this.burstQuery = burstQuery;
	}
	public String getBurstDatasource() {
		return burstDatasource;
	}
	public void setBurstDatasource(String burstDatasource) {
		this.burstDatasource = burstDatasource;
	}
	public String getBurstColumnName() {
		return burstColumnName;
	}
	public void setBurstColumnName(String burstColumnName) {
		this.burstColumnName = burstColumnName;
	}
	public String getBurstParameterName() {
		return burstParameterName;
	}
	public void setBurstParameterName(String burstParameterName) {
		this.burstParameterName = burstParameterName;
	}
	public String getRunnerEngine() {
		return runnerEngine;
	}
	public void setRunnerEngine(String runnerEngine) {
		this.runnerEngine = runnerEngine;
	}
	public String getDatasourceName() {
		return datasourceName;
	}
	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}
	public Map<String, Object> getEngineParameters() {
		return engineParameters;
	}
	public void setEngineParameters(Map<String, Object> engineParameters) {
		this.engineParameters = engineParameters;
	}
	
	
	
}
