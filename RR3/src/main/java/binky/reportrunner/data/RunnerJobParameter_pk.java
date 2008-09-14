package binky.reportrunner.data;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class RunnerJobParameter_pk implements Serializable {

	private static final long serialVersionUID = 5839942860917772506L;
	
	@ManyToOne
	private RunnerJob runnerJob;
	private Integer parameterIdx;
	public RunnerJob getRunnerJob() {
		return runnerJob;
	}
	public void setRunnerJob(RunnerJob runnerJob) {
		this.runnerJob = runnerJob;
	}
	public Integer getParameterIdx() {
		return parameterIdx;
	}
	public void setParameterIdx(Integer parameterIdx) {
		this.parameterIdx = parameterIdx;
	}

	
	
}
