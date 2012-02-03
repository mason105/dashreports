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
 * Module: ListGroupsAction.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.group;

import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.service.GroupService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class ListGroupsAction extends StandardRunnerAction {

	private static final long serialVersionUID = -1335751757190312426L;
	private static final Logger logger = Logger.getLogger(ListGroupsAction.class);
	private GroupService groupService;
	

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	private List<RunnerGroup> groups;
	@Override
	public String execute() throws Exception{
		logger.debug("listing groups for: " + super.getSessionUser().getUserName());
		logger.debug("is admin is null = " + super.getSessionUser().getIsAdmin()==null);
		if (super.getSessionUser().getIsAdmin()) {
			logger.debug("is admin - allowing all groups");
			 this.groups=groupService.getAll();
		} else {
			logger.debug("is not admin - restricting groups");
			this.groups = super.getSessionUser().getGroups();
		}
		
		return SUCCESS;		
	}

	public List<RunnerGroup> getGroups() {
		return groups;
	}

	
	
}
