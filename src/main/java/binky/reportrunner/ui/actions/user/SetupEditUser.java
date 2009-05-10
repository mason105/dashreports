package binky.reportrunner.ui.actions.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.dao.RunnerUserDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

import com.opensymphony.xwork2.Preparable;

public class SetupEditUser extends AdminRunnerAction implements Preparable {

	private static final long serialVersionUID = 1L;

	private String userName;

	private RunnerUser runnerUser;

	private List<RunnerGroup> groups;

	private String[] groupNames;

	private static final Logger logger = Logger.getLogger(SetupEditUser.class);

	@Override
	public String execute() throws Exception {
		if ((userName != null) && (!userName.isEmpty())) {
			this.runnerUser = userDao.getUser(userName);
			List<String> gn = new ArrayList<String>();
			for (RunnerGroup g : runnerUser.getGroups()) {
				gn.add(g.getGroupName());
				logger.debug("found group: " + g);
			}
			this.groupNames = gn.toArray(new String[0]);
		} else {
			this.runnerUser=new RunnerUser();
			this.groupNames = new String[0];
		}
		return SUCCESS;
	}

	public void prepare() throws Exception {
		this.groups = groupDao.listGroups();
	}

	private RunnerUserDao userDao;

	public RunnerUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(RunnerUserDao userDao) {
		this.userDao = userDao;
	}

	private RunnerGroupDao groupDao;

	public RunnerGroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(RunnerGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public RunnerUser getRunnerUser() {
		return runnerUser;
	}

	public List<RunnerGroup> getGroups() {
		return groups;
	}

	public String[] getGroupNames() {
		return groupNames;
	}

	public void setGroupNames(String[] groupNames) {
		this.groupNames = groupNames;
	}

	public String getUserName() {
		return userName;
	}

	public void setGroups(List<RunnerGroup> groups) {
		this.groups = groups;
	}

	public void setRunnerUser(RunnerUser runnerUser) {
		this.runnerUser = runnerUser;
	}

}
