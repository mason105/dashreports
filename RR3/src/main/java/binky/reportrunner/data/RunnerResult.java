package binky.reportrunner.data;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.hibernate.annotations.Entity;

@Entity
public final class RunnerResult {

	@Id 
	private String id;
	private Date timeStart;
	private Date timeEnd;
	private Exception exception;
	private List<String> outputs;
	private String jobName;
	private String datasourceName;
	private String runnerEngine;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}
	public Date getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public List<String> getOutputs() {
		return outputs;
	}
	public void setOutputs(List<String> outputs) {
		this.outputs = outputs;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getDatasourceName() {
		return datasourceName;
	}
	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}
	public String getRunnerEngine() {
		return runnerEngine;
	}
	public void setRunnerEngine(String runnerEngine) {
		this.runnerEngine = runnerEngine;
	}
}
