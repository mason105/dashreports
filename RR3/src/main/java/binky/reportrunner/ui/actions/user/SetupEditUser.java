package binky.reportrunner.ui.actions.user;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.dao.RunnerUserDao;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class SetupEditUser extends AdminRunnerAction {

	private static final long serialVersionUID = 1L;
	private String userName;
	private RunnerUser runnerUser;

	@Override
	public String execute() throws Exception {
		runnerUser = userDao.getUser(userName);
		return SUCCESS;
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
}
