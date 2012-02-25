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

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.ui.Statics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public abstract class StandardRunnerAction extends ActionSupport implements
		SessionAware {

	protected Map<String, Object> sessionData;
	protected String groupName;

	public void setSession(Map<String, Object> sessionData) {
		this.sessionData = sessionData;
	}

	private static final long serialVersionUID = -5701712982967708713L;

	public abstract String execute() throws Exception;



	public final RunnerUser getSessionUser() {
		// hack to deal with thread local issues
		RunnerUser user;
		if ((ActionContext.getContext() == null)
				|| (ActionContext.getContext().getSession() == null)) {
			user = (RunnerUser) sessionData.get(Statics.USER_HANDLE);
		} else {
			user = (RunnerUser) ActionContext.getContext().getSession().get(
					Statics.USER_HANDLE);
			sessionData.put(Statics.USER_HANDLE, user);
		}
		return user;
	}
	public final List<RunnerGroup> getSessionGroups() {
		// hack to deal with thread local issues
		List<RunnerGroup>  groups;
		if ((ActionContext.getContext() == null)
				|| (ActionContext.getContext().getSession() == null)) {
			groups = ( List<RunnerGroup> ) sessionData.get(Statics.GROUPS_HANDLE);
		} else {
			groups = ( List<RunnerGroup> ) ActionContext.getContext().getSession().get(
					Statics.GROUPS_HANDLE);
			sessionData.put(Statics.GROUPS_HANDLE, groups);
		}
		return groups;
	}
	public final String getSessionUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth!=null) {
			return auth.getName();
		} else {
			RunnerUser user = getSessionUser();
			if (user!=null) {
				return user.getUsername();
			} else {
				return null;
			}
		}
		
	}
	
	public long getRandomNumber() {
		return Math.round(Math.random()*100000000);
	}
	
	public final String getActionName() {
		return this.getClass().getName();
	}

	public final boolean doesUserHaveGroup(String groupName) {
		if (getSessionUser() == null)
			return false;

		if (getSessionUser().getIsAdmin()) {
			return true;
		} else {

			for (RunnerGroup g : getSessionGroups()) {
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

	public List<RunnerGroup> getGroups() {
		return this.getSessionGroups();
	}

	protected boolean isStringPopulated(String value) {
		if (value != null) {
			if (value.trim().isEmpty()) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	public final String getCurrentGroupName() {
		return groupName;
	}

	public final String getGroupName() {
		return groupName;
	}

	public final void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	

}
