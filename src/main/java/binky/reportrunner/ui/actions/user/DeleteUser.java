package binky.reportrunner.ui.actions.user;

import binky.reportrunner.dao.RunnerUserDao;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class DeleteUser extends AdminRunnerAction {

	private static final long serialVersionUID = 1L;
	private String userName;

	@Override
	public String execute() throws Exception {
		userDao.deleteUser(userName);
		return SUCCESS;
	}

	private RunnerUserDao userDao;

	public RunnerUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(RunnerUserDao userDao) {
		this.userDao = userDao;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
