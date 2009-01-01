package binky.reportrunner.data;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class RunnerJobParameter_pk implements Serializable {

	private static final long serialVersionUID = 5839942860917772506L;
	
	private RunnerJob_pk runnerJob_pk;
	private Integer parameterIdx;

	
	
	public RunnerJob_pk getRunnerJob_pk() {
		return runnerJob_pk;
	}
	public void setRunnerJob_pk(RunnerJob_pk runnerJob_pk) {
		this.runnerJob_pk = runnerJob_pk;
	}
	public Integer getParameterIdx() {
		return parameterIdx;
	}
	public void setParameterIdx(Integer parameterIdx) {
		this.parameterIdx = parameterIdx;
	}

	public String toString() {
		return this.runnerJob_pk.getGroup().getGroupName() + "." + this.runnerJob_pk.getJobName() +" - " + this.parameterIdx;
	}
	
}
