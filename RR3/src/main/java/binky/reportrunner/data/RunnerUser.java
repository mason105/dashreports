package binky.reportrunner.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

@Entity
public class RunnerUser implements Serializable {

	private static final long serialVersionUID = 8376600609942516518L;

	@Id
	private String userName;

	private String password;

	private String fullName;

	private Boolean isAdmin = false;

	private Boolean isLocked = false;
	
	private Boolean isReadOnly = false;

	public Boolean getIsReadOnly() {
		return isReadOnly;
	}

	public void setIsReadOnly(Boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	public RunnerUser() {
	}

	public RunnerUser(String userName, String password, String fullName,
			Boolean isAdmin, List<RunnerGroup> groups) {
		super();
		this.userName = userName;
		this.password = password;
		this.fullName = fullName;

		if (isAdmin == null) {
			this.isAdmin = false;
		} else {
			this.isAdmin = isAdmin;
		}
		this.groups = groups;
		this.isLocked = false;
	}

	public Boolean getIsAdmin() {
		return isAdmin==null?false:isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	private List<RunnerGroup> groups;

	@RequiredStringValidator
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@RequiredStringValidator
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

	public Boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

}
