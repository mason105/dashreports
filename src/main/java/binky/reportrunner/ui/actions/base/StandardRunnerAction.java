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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

public abstract class StandardRunnerAction extends ActionSupport implements
		SessionAware {

	protected Map<String, Object> sessionData;
	protected String groupName;

	public void setSession(Map<String, Object> sessionData) {
		this.sessionData = sessionData;
	}

	private static final long serialVersionUID = -5701712982967708713L;
	private static final Logger logger = Logger
			.getLogger(StandardRunnerAction.class);

	public abstract String execute() throws Exception;

	//ewww
	ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	private UserService userService = (UserService)ctx.getBean("userService"); 


	public final RunnerUser getSessionUser() {

		return userService.getUser(this.getSessionUserName());
	}

	public final String getSessionUserName() {
		// this is going to need caching to death
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		return auth.getName();		
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

	public List<RunnerGroup> getGroups() {
		return userService.getGroupsForUser(this.getSessionUserName());
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
	
	protected UserService getUserService() {
		return userService;
	}


}
