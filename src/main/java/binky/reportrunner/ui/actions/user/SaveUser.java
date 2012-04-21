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
 * Module: SaveUser.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.user;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;

import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.service.GroupService;
import binky.reportrunner.service.UserService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

import com.opensymphony.xwork2.Preparable;

public class SaveUser extends StandardRunnerAction  implements Preparable {

	private static final long serialVersionUID = 1L;

	private UserService userService;
	private GroupService groupService;
	private RunnerUser runnerUser;
	private String[] groupNames;
	private List<RunnerGroup> groups;
	private List<String> userGroups;
	
	private static final Logger logger = Logger.getLogger(SaveUser.class);
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String execute() throws Exception {
		if (runnerUser!=null) {
			boolean valid = true;
			//quick validation
			if ((runnerUser.getUserName()==null)||(runnerUser.getUserName().trim().isEmpty())) {
				valid=false;
				super.addActionError("Please enter a username");				
			} else {
				if (StringUtils.isBlank(runnerUser.getPassword())||StringUtils.isEmpty(runnerUser.getPassword())) {
				
					RunnerUser userCompare = userService.getUser(runnerUser.getUserName());
					if (userCompare==null) {
						valid = false;
						super.addActionError("Please enter a password");
					} else {
						//put the old password back in
						runnerUser.setPassword(userCompare.getPassword());
					}
				
					
				}
			}
	
			
			if (userGroups!=null) {
				List<RunnerGroup> groups = new LinkedList<RunnerGroup>();
				for (String g: userGroups) {
					logger.debug("user now has group: " + g);
					RunnerGroup group = groupService.getGroup(g);
					groups.add(group);
				}
				runnerUser.setGroups(groups);
			}
			
			
			
			if (valid) {
				if (userService.getUser(runnerUser.getUserName())!=null) {
					userService.saveOrUpdate(runnerUser);
				}else {
					runnerUser=userService.createUser(runnerUser.getUsername(), runnerUser.getPassword(), runnerUser.getFullName(), runnerUser.getIsReadOnly(), runnerUser.getIsAdmin(), runnerUser.getIsLocked(),runnerUser.getGroups());
				}
				return SUCCESS;
			} else {
				return INPUT;
			}
		} else {
			throw new Exception("USER WAS NULL!");
		}
	}

	public void prepare() throws Exception {
		this.groups=groupService.getAll();
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

	public List<String> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<String> userGroups) {
		this.userGroups = userGroups;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

}
