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
 * Module: SetupEditUser.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.user;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

import com.opensymphony.xwork2.Preparable;

public class SetupEditUser extends AdminRunnerAction implements Preparable {

	private static final long serialVersionUID = 1L;

	private String userName;

	private RunnerUser runnerUser;

	private List<RunnerGroup> groups;
	private List<String> userGroups;
	private static final Logger logger = Logger.getLogger(SetupEditUser.class);

	@Override
	public String execute() throws Exception {
		if ((userName != null) && (!userName.isEmpty())) {
			logger.debug("editing user: " + userName);
			userGroups = new LinkedList<String>();
			this.runnerUser = userDao.get(userName);
			for (RunnerGroup group:runnerUser.getGroups()) {
				userGroups.add(group.getGroupName());
				logger.debug("found group: " + group.getGroupName());
			}			
								
		} else {
			this.runnerUser=new RunnerUser();			
		}
		return SUCCESS;
	}

	public void prepare() throws Exception {
		this.groups = groupDao.getAll();
	}

	private ReportRunnerDao<RunnerUser,String> userDao;

	
	private ReportRunnerDao<RunnerGroup,String> groupDao;

	
	public void setUserDao(ReportRunnerDao<RunnerUser, String> userDao) {
		this.userDao = userDao;
	}

	public void setGroupDao(ReportRunnerDao<RunnerGroup, String> groupDao) {
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

	public String getUserName() {
		return userName;
	}

	public void setGroups(List<RunnerGroup> groups) {
		this.groups = groups;
	}

	public void setRunnerUser(RunnerUser runnerUser) {
		this.runnerUser = runnerUser;
	}

	public List<String> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<String> userGroups) {
		this.userGroups = userGroups;
	}

}
