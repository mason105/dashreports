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
 * Module: AuthenticationServiceImpl.java
 ******************************************************************************/
package binky.reportrunner.service.impl;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.service.AuthenticationService;

public class AuthenticationServiceImpl implements AuthenticationService {
	
	private ReportRunnerDao<RunnerUser,String> userDao;
	private static final Logger logger = Logger
			.getLogger(AuthenticationServiceImpl.class);
	private  AUTH_RESULT lastResult;
	
	public RunnerUser authUser(String userName, String password ) {

		RunnerUser  user = userDao.get(userName);
		if (user == null) {
			logger.warn("Authentication failed - unknown user - " + userName);
			user=null;
			this.lastResult = AUTH_RESULT.FAIL;			
		} else {
			if (password.equals(user.getPassword())) {
				if (!user.getIsLocked()) {
					logger.info("Authenticated user: " + userName + " " + user.getFullName());
					this.lastResult = AUTH_RESULT.SUCCESS;
				} else {
					logger.warn("Authentication failed - locked");
					user=null;
					this.lastResult = AUTH_RESULT.LOCKED;
				}
			} else {
				logger.warn("Authentication failed - invalid password for " + userName);
				user=null;
				this.lastResult = AUTH_RESULT.FAIL;				
			}
		}
		return user;
	}


	public void setUserDao(ReportRunnerDao<RunnerUser, String> userDao) {
		this.userDao = userDao;
	}

	public  AUTH_RESULT getLastResult() {
		return this.lastResult;
	}
}
