package binky.reportrunner.data;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class RunnerJobParameter_pk implements Serializable {

	private static final long serialVersionUID = 5839942860917772506L;
	
	@ManyToOne
	private RunnerJob runnerJob;
	private String parameterName;
	public RunnerJob getRunnerJob() {
		return runnerJob;
	}
	public void setRunnerJob(RunnerJob runnerJob) {
		this.runnerJob = runnerJob;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	
	
}
