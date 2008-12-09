package binky.reportrunner.ui.actions.user;

import binky.reportrunner.dao.RunnerUserDao;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class ChangePassword  extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;
	private String newPassword;
	@Override
	public String execute() throws Exception {
		RunnerUser currentUser = this.getUser();
		currentUser.setPassword(newPassword);
		userDao.saveUpdateUser(currentUser);
		return SUCCESS;
	}
	private RunnerUserDao userDao;

	public RunnerUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(RunnerUserDao userDao) {
		this.userDao = userDao;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}


}
