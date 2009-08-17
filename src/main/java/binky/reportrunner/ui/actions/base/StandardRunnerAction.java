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
 * Module: StandardRunnerAction.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.base;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.ui.Statics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public abstract class StandardRunnerAction extends ActionSupport implements SessionAware {

	protected Map<String, Object> sessionData; 
	
	public void setSession(Map<String, Object> sessionData) {
		this.sessionData=sessionData;		
	}

	private static final long serialVersionUID = -5701712982967708713L;
	private static final Logger logger = Logger.getLogger(StandardRunnerAction.class);
	public abstract String execute() throws Exception;

	public final RunnerUser getSessionUser() {
		//hack to deal with thread local issues
		RunnerUser user;
		if ((ActionContext.getContext()==null) || (ActionContext.getContext().getSession()==null)) {
			user = (RunnerUser) sessionData.get(Statics.USER_HANDLE);
		} else {		
			user = (RunnerUser) ActionContext.getContext().getSession()
			.get(Statics.USER_HANDLE);
			sessionData.put(Statics.USER_HANDLE, user);
		}
		return user;
	}
	
	public final String getActionName() {
		return this.getClass().getName();
	}

	public final boolean doesUserHaveGroup(String groupName) {

		if (getSessionUser().getIsAdmin()) {
			return true;
		} else {

			for (RunnerGroup g : getSessionUser().getGroups()) {
				if (g.getGroupName().equals(groupName))
					return true;
			}

			return false;
		}
	}
	
	public final boolean isUserReadOnly() {
		if (getSessionUser().getIsAdmin()) {
			return false;
		} else {
			return getSessionUser().getIsReadOnly();
		}	
	}

	public void listAllVars(String className) {
		Map<String, Object> params = ActionContext.getContext().getContextMap();
		logger.debug("dumping context map for action class: " + className);
		for (String key : params.keySet()) {			 
			logger.debug(key + " - " + params.get(key));
		}
	
		
	}
	
	public Boolean getExpandAdmin() {
		return (this instanceof AdminRunnerAction) ;
	}
	public Boolean getExpandGroups() {
		String actionName=getActionName();
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
				return true;			       
		} else {
			return false;
		}
	}
	public List<RunnerGroup> getGroups() {
		return getSessionUser().getGroups();
	}
}
