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
 * Module: SetupEditGroup.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.group;

import org.springframework.security.access.prepost.PreAuthorize;

import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.GroupService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class SetupEditGroup extends StandardRunnerAction {

	private GroupService groupService;
	

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	private RunnerGroup group;

	private static final long serialVersionUID = 1L;

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String execute() throws Exception {

		if (super.getSessionUser().getGroups().contains(groupName)
				|| super.getSessionUser().getIsAdmin()) {

			if (groupName != null) {
				this.group = groupService.getGroup(groupName);
			} else {
				this.group = new RunnerGroup();
			}
				
		} else {
			SecurityException se = new SecurityException("Group " + groupName
					+ " not valid for user " + super.getSessionUser().getUserName());
			throw se;
		}

		return SUCCESS;
	}

	public void setGroup(RunnerGroup group) {
		this.group = group;
	}



	public RunnerGroup getGroup() {
		return group;
	}

}
