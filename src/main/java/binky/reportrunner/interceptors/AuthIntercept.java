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
 * Module: AuthIntercept.java
 ******************************************************************************/
package binky.reportrunner.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import binky.reportrunner.service.AuthenticationService;
import binky.reportrunner.ui.Statics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class AuthIntercept implements Interceptor, StrutsStatics {

	private static final long serialVersionUID = -6151350585810759841L;

	private static final Logger logger = Logger.getLogger(AuthIntercept.class);

	private AuthenticationService authService;

	/**
	 *based on http://www.vitarara.org/cms/struts_2_cookbook/
	 * creating_a_login_interceptor
	 **/
	public String intercept(ActionInvocation invocation) throws Exception {
		// Get the action context from the invocation so we can access the
		// HttpServletRequest and HttpSession objects.
		final ActionContext context = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) context
				.get(HTTP_REQUEST);
		HttpSession session = request.getSession(true);

		// Is there a "user" object stored in the user's HttpSession?
		Object user = session.getAttribute(Statics.USER_HANDLE);
		if (user == null) {
			// The user has not logged in yet.

			// Is the user attempting to log in right now?
			String loginAttempt = request.getParameter(Statics.LOGIN_ATTEMPT);
			if (!StringUtils.isBlank(loginAttempt)) { // The user is attempting
														// to log in.
				String requestedAction = invocation.getProxy().getActionName();
				logger.debug("requested action is: " + requestedAction);
				// Process the user's login attempt.

				Object action = invocation.getAction();
				String username = request.getParameter(Statics.USERNAME);
				String password = request.getParameter(Statics.PASSWORD);

				user = authService.authUser(username, password);
				switch (authService.getLastResult()) {

				case FAIL:
					// The login failed. Set an error if we can on the action.
					if (action instanceof ValidationAware) {
						((ValidationAware) action)
								.addActionError("Username/password incorrect.");
					}
				case LOCKED:
					// The login failed. Set an error if we can on the action.
					if (action instanceof ValidationAware) {
						((ValidationAware) action)
								.addActionError("User locked.");
					}
				case SUCCESS:
					logger.info("login success for user:" + username);
				}
				if (user == null) {
					return "login";
				} else {
					// The login succeeded send them the login-success page
					session.setAttribute(Statics.USER_HANDLE, user);
					return "login-success";
				}
			}

			// Either the login attempt failed or the user hasn't tried to login
			// yet,
			// and we need to send the login form.

			// preserve the target URL - issue #5
			//Map<String, String> params = new HashMap<String, String>();

			/*while (request.getParameterNames().hasMoreElements()) {
				String key = (String) request.getParameterNames().nextElement();
				String value = request.getParameter(key);
				params.put(key, value);
				logger.debug("found parameter to forward:" + key + " = "
						+ value);

			}*/
			logger.debug("user session empty - sending to login screen");
			return "login";
		} else {
			return invocation.invoke();
		}
	}

	public AuthenticationService getAuthService() {
		return authService;
	}

	public void setAuthService(AuthenticationService authService) {
		this.authService = authService;
	}

	public void destroy() {

	}

	public void init() {

	}

}
