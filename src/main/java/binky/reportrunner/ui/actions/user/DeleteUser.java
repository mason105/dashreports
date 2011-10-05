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
 * Module: DeleteUser.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.user;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class DeleteUser extends AdminRunnerAction {

	private static final long serialVersionUID = 1L;
	private String userName;

	private ReportRunnerDao<RunnerUser,String> userDao;
	
	@Override
	public String execute() throws Exception {
		userDao.delete(userName);
		return SUCCESS;
	}



	public void setUserDao(ReportRunnerDao<RunnerUser,String>  userDao) {
		this.userDao = userDao;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
