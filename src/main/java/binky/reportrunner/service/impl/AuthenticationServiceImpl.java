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

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.service.AuditService;
import binky.reportrunner.service.AuthenticationService;
import binky.reportrunner.util.EncryptionUtil;

public class AuthenticationServiceImpl implements AuthenticationService {

	private ReportRunnerDao<RunnerUser, String> userDao;
	private static final Logger logger = Logger
			.getLogger(AuthenticationServiceImpl.class);
	private AuditService auditService;
	public void setUserDao(ReportRunnerDao<RunnerUser, String> userDao) {
		this.userDao = userDao;
	}

	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		
		logger.info("authenticate service invoked");
		
		if (StringUtils.isBlank((String) authentication.getPrincipal())
				|| StringUtils
						.isBlank((String) authentication.getCredentials())) {
			logger.debug("userName blank is "
					+ StringUtils.isBlank((String) authentication
							.getPrincipal()
							+ " password blank is "
							+ StringUtils.isBlank((String) authentication
									.getCredentials())));
			throw new BadCredentialsException("Invalid username/password");

		}

		String userName = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();

		RunnerUser user = userDao.get(userName);

		EncryptionUtil enc = new EncryptionUtil();

		List<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();
		try {
			if (user != null
					&& user.getPassword().equals(enc.hashString(password))) {
				if (user.getIsAdmin()) {
					logger.info("admin login for user: " + userName);
					authorities.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
				} else {
					logger.info("user login for user: " + userName);
				}
				authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
			} else {
				logger.warn("login fail for user: " + userName);
				logger.debug("hash got: " + enc.hashString(password)
						+ " hash expected: " + user.getPassword());
				throw new BadCredentialsException("Invalid username/password");
			}
		} catch (Exception e) {
			
			logger.fatal(e.getMessage(), e);
			throw new AuthenticationServiceException(e.getMessage(), e);
		}
		
		return new UsernamePasswordAuthenticationToken(userName,
				authentication.getCredentials(), authorities);

	}

	public boolean supports(Class<? extends Object> arg0) {
		return true;
	}

	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException {
		
		logger.info("authenticate service invoked for userName: " + userName);
		return userDao.get(userName);

	}

	public void setAuditService(AuditService auditService) {
		this.auditService = auditService;
	}

}
