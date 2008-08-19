package binky.reportrunner.data;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class RunnerJob_pk implements Serializable {

	private static final long serialVersionUID = -1882140077608940619L;
	
	private String jobName;
	@ManyToOne
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
