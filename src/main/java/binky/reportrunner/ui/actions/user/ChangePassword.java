/*******************************************************************************
 * Copyright (c) 2009 Daniel Grout.
 * 
 * GNU GENERAL PUBLIC LICENSE - Version 3
 * 
 * This file is part of Report Runner (http://code.google.com/p/reportrunner).
 * 
 * Report Runner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Runner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Runner. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Module: ChangePassword.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.user;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;
import binky.reportrunner.util.EncryptionUtil;

public class ChangePassword  extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;
	private String oldPassword;
	private String newPassword1;
	private String newPassword2;
	private ReportRunnerDao<RunnerUser,String> userDao;
	
	@Override
	public String execute() throws Exception {
		EncryptionUtil enc = new EncryptionUtil();
		oldPassword=enc.hashString(oldPassword);
		
		if (newPassword1.equals(newPassword2)) {
			if (oldPassword.equals(getSessionUser().getPassword())) {
				RunnerUser currentUser = this.getSessionUser();
				//hash the password
				currentUser.setPassword(enc.hashString(newPassword1));				
				userDao.saveOrUpdate(currentUser);
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
	
	public void setUserDao(ReportRunnerDao<RunnerUser,String>  userDao) {
		this.userDao = userDao;
	}




}
