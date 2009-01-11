package binky.reportrunner.ui.actions.user;

import binky.reportrunner.dao.RunnerUserDao;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class ChangePassword  extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;
	private String oldPassword;
	private String newPassword1;
	private String newPassword2;

	private RunnerUserDao userDao;
	
	@Override
	public String execute() throws Exception {
		//RunnerUser currentUser = this.getUser();
		//currentUser.setPassword(newPassword);
		///userDao.saveUpdateUser(currentUser);
		return SUCCESS;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword1() {
		return newPassword1;
	}
	public void setNewPassword1(String newPassword1) {
		this.newPassword1 = newPassword1;
	}
	public String getNewPassword2() {
		return newPassword2;
	}
	public void setNewPassword2(String newPassword2) {
		this.newPassword2 = newPassword2;
	}
	public RunnerUserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(RunnerUserDao userDao) {
		this.userDao = userDao;
	}




}
