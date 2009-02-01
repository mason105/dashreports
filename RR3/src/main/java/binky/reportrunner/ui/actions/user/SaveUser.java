package binky.reportrunner.ui.actions.user;

import java.util.LinkedList;
import java.util.List;

import com.opensymphony.xwork2.Preparable;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.dao.RunnerUserDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class SaveUser extends AdminRunnerAction  implements Preparable {

	private static final long serialVersionUID = 1L;

	private RunnerUserDao userDao;
	private RunnerGroupDao groupDao;
	private RunnerUser runnerUser;
	private String[] groupNames;
	private List<RunnerGroup> groups;

	@Override
	public String execute() throws Exception {
		List<RunnerGroup> groups = new LinkedList<RunnerGroup>();

		for (String groupName : groupNames) {
			RunnerGroup group = groupDao.getGroup(groupName);
			groups.add(group);
		}

		runnerUser.setGroups(groups);
		
		userDao.saveUpdateUser(runnerUser);
		return SUCCESS;
	}

	public void prepare() throws Exception {
		this.groups=groupDao.listGroups();
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

	public String[] getGroupNames() {
		return groupNames;
	}

	public void setGroupNames(String[] groupNames) {
		this.groupNames = groupNames;
	}


	public RunnerUser getRunnerUser() {
		return runnerUser;
	}

	public void setRunnerUser(RunnerUser runnerUser) {
		this.runnerUser = runnerUser;
	}

	public List<RunnerGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<RunnerGroup> groups) {
		this.groups = groups;
	}




}
