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
 * Module: LeftNavAction.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.navigation;

import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class LeftNavAction extends StandardRunnerAction {
	
	private static final Logger logger = Logger.getLogger(LeftNavAction.class);
	private static final long serialVersionUID = -2321083106251542716L;
	private RunnerGroupDao groupDao;
	private List<RunnerGroup> groups;
	private Boolean expandAdmin;
	private Boolean expandGroups;
	private String actionName;
	
	
	
	@Override
	public String execute() throws Exception {
		//hack to force the tree to keep the correct folders open
		if ((Class.forName(actionName).newInstance() instanceof AdminRunnerAction) 
				&& getSessionUser().getIsAdmin() ){
			expandAdmin=true;
		} else {
			expandAdmin=false;
		}
		logger.debug("admin section being accessed: " + expandAdmin);
		logger.debug("action name is set to: " + actionName);
		if (actionName.toLowerCase().contains("setupviewjob")
				||actionName.toLowerCase().contains("viewjoboutput")
				||actionName.toLowerCase().contains("invokejob")
				||actionName.toLowerCase().contains("changeallgroupjobstatus")
				||actionName.toLowerCase().contains("viewjobdetail")
				||actionName.toLowerCase().contains("setupeditjob")
				||actionName.toLowerCase().contains("setjobstatus")
				||actionName.toLowerCase().contains("savejob")
				||actionName.toLowerCase().contains("deletejob")
				||actionName.toLowerCase().contains("listjobs")
			) {
				expandGroups=true;			       
		} else {
			expandGroups=false;
		}
		
		if (super.getSessionUser().getIsAdmin()) {
			groups = groupDao.listGroups();
		} else {			
			super.getSessionUser().getGroups();
		}
		
		
		return SUCCESS;
	}
	public RunnerGroupDao getGroupDao() {
		return groupDao;
	}
	public void setGroupDao(RunnerGroupDao groupDao) {
		this.groupDao = groupDao;
	}
	public List<RunnerGroup> getGroups() {
		return groups;
	}
	public void setGroups(List<RunnerGroup> groups) {
		this.groups = groups;
	}
	public Boolean getExpandAdmin() {
		return expandAdmin;
	}
	public void setExpandAdmin(Boolean expandAdmin) {
		this.expandAdmin = expandAdmin;
	}
	public Boolean getExpandGroups() {
		return expandGroups;
	}
	public void setExpandGroups(Boolean expandGroups) {
		this.expandGroups = expandGroups;
	}
	
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	

}
