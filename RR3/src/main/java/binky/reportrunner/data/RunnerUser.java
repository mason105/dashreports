package binky.reportrunner.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class RunnerUser implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private String userName;
	private String password;
	private String fullName;
	private Boolean isAdmin;

	public RunnerUser() {
	}

	public RunnerUser(String userName, String password, String fullName,
			Boolean isAdmin, List<RunnerGroup> groups) {
		super();
		this.userName = userName;
		this.password = password;
		this.fullName = fullName;
		this.isAdmin = isAdmin;
		this.groups = groups;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<RunnerGroup> groups;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public List<RunnerGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<RunnerGroup> groups) {
		this.groups = groups;
	}

}
