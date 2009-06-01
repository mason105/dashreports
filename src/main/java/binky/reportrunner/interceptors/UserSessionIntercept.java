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
 * Module: UserSessionIntercept.java
 ******************************************************************************/
package binky.reportrunner.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.ui.Statics;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class UserSessionIntercept implements Interceptor, StrutsStatics {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UserSessionIntercept.class);
	public void destroy() {

	}

	public void init() {

	}

	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext context = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) context
				.get(HTTP_REQUEST);
		HttpSession session = request.getSession(true);
		RunnerUser user = (RunnerUser) session.getAttribute(Statics.USER_HANDLE);
		// if this is an admin function action then check all is well
		logger.debug("class name is: " + invocation.getAction().getClass().getName());
		logger.debug("(invocation.getAction() instanceof AdminRunnerAction)" + (invocation.getAction() instanceof AdminRunnerAction));
		if ((invocation.getAction() instanceof AdminRunnerAction) && !user.getIsAdmin() ) {
			logger.warn("access denied to " + invocation.getAction().getClass() + " for user: " + user.getUserName()) ;
			return "securityError";			
		} else {
			return invocation.invoke();
		}
		
	}
}
