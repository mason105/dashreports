package binky.reportrunner.service.impl;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.RunnerUserDao;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.service.AuthenticationService;

public class AuthenticationServiceImpl implements AuthenticationService {

	private RunnerUserDao userDao;
	private static final Logger logger = Logger
			.getLogger(AuthenticationServiceImpl.class);

	public RunnerUser authUser(String userName, String password) {

		logger.info("Authenticating user: " + userName);

		RunnerUser user = userDao.getUser(userName);
		if (user == null) {
			logger.warn("Authentication failed - unknown user");			
			return null;
		} else {
			if (password.equals(user.getPassword())) {
				// TODO:hashing
				logger.info("Authenticated user: " + userName + " " + user.getFullName());
				return user;
			} else {
				logger.warn("Authentication failed - invalid password");
				return null;				
			}
		}

	}

	public RunnerUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(RunnerUserDao userDao) {
		this.userDao = userDao;
	}

}
