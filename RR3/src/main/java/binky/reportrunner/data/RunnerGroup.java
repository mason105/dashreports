package binky.reportrunner.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

@Entity
public class RunnerGroup implements Serializable {

	private static final long serialVersionUID = -5727491198024680384L;

	public RunnerGroup(String groupName, String groupDescription,
			List<RunnerJob> runnerJobs) {
		super();
		this.groupName = groupName;
		this.groupDescription = groupDescription;
		this.runnerJobs = runnerJobs;		
	}
	public RunnerGroup(){};
	@Id
	private String groupName;
	private String groupDescription;

	@ManyToMany
	private List<RunnerUser> users;
	
	@OneToMany
	private List<RunnerJob> runnerJobs;
	
	@RequiredStringValidator
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
	
	public String toString() {
		return this.groupName;
	}
	public List<RunnerUser> getUsers() {
		return users;
	}
	public void setUsers(List<RunnerUser> users) {
		this.users = users;
	}

}
