package binky.reportrunner.ui.actions.user;

import java.util.LinkedList;
import java.util.List;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.dao.RunnerUserDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class SaveUser extends AdminRunnerAction {

	private static final long serialVersionUID = 1L;

	private RunnerUserDao userDao;
	private RunnerGroupDao groupDao;
	private String userName;
	private String password;
	private String fullName;
	private Boolean isAdmin;
	private List<String> groupNames;

	@Override
	public String execute() throws Exception {
		List<RunnerGroup> groups = new LinkedList<RunnerGroup>();

		for (String groupName : groupNames) {
			RunnerGroup group = groupDao.getGroup(groupName);
			groups.add(group);
		}

		RunnerUser user = new RunnerUser(userName, password, fullName, isAdmin,
				groups);

		userDao.saveUpdateUser(user);
		return SUCCESS;
	}

	public RunnerGroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(RunnerGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public RunnerUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(RunnerUserDao userDao) {
		this.userDao = userDao;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setGroupNames(List<String> groupNames) {
		this.groupNames = groupNames;
	}

}
