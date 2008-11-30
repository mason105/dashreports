package binky.reportrunner.data;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class RunnerGroup {

	@Id
	private String groupName;
	private String groupDescription;

	@OneToMany
	private List<RunnerJob> runnerJobs;
	
	@ManyToMany
	private List<RunnerDataSource> dataSources;

	public List<RunnerDataSource> getDataSources() {
		return dataSources;
	}

	public void setDataSources(List<RunnerDataSource> dataSources) {
		this.dataSources = dataSources;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public List<RunnerJob> getRunnerJobs() {
		return runnerJobs;
	}

	public void setRunnerJobs(List<RunnerJob> runnerJobs) {
		this.runnerJobs = runnerJobs;
	}

}
