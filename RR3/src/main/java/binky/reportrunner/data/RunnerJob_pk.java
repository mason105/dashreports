package binky.reportrunner.data;

import javax.persistence.Embeddable;

@Embeddable
public class RunnerJob_pk {

	private String jobName;
	private RunnerGroup group;
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public RunnerGroup getGroup() {
		return group;
	}
	public void setGroup(RunnerGroup group) {
		this.group = group;
	}
	
	
}
