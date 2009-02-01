package binky.reportrunner.ui.actions.user;

import binky.reportrunner.dao.RunnerUserDao;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class ChangePassword  extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;
	private String oldPassword;
	private String newPassword1;
	private String newPassword2;
	private RunnerUserDao userDao;
	
	@Override
	public String execute() throws Exception {

		
		if (newPassword1.equals(newPassword2)) {
			if (oldPassword.equals(newPassword1)) {
				RunnerUser currentUser = this.getSessionUser();
				currentUser.setPassword(newPassword1);
				userDao.saveUpdateUser(currentUser);
				return SUCCESS;
			} else {
				super.addActionError("Old password invalid!");
				return INPUT;
			}
		} else {
			super.addActionError("Passwords do not match!");
			return INPUT;
		}
		
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
