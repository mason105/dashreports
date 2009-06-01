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
 * Module: RunnerUserDaoImplTest.java
 ******************************************************************************/
package binky.reportrunner.dao.impl;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import binky.reportrunner.dao.RunnerUserDao;
import binky.reportrunner.data.RunnerUser;

public class RunnerUserDaoImplTest extends TestCase {

	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			new String[] { "applicationContext.xml" });

	RunnerUserDao dao;
	
	@Override
	protected void setUp() throws Exception {
		this.dao = (RunnerUserDao) ctx.getBean("runnerUserDao");
	}

	private RunnerUser getDemoUser(String userId){
		RunnerUser user = new RunnerUser();
		user.setFullName(userId);
		user.setUserName(userId);
		user.setPassword("password");
		return user;
	}
	
	public void testDeleteUser() {
		RunnerUser user = getDemoUser("demouser");
		dao.saveUpdateUser(user);
		dao.deleteUser(user.getUserName());
		RunnerUser userComp= dao.getUser(user.getUserName());
		assertNull(userComp);
	}


	public void testListUsers() {
		RunnerUser user1 = getDemoUser("demouser1");
		RunnerUser user2 = getDemoUser("demouser2");
		dao.saveUpdateUser(user1);
		dao.saveUpdateUser(user2);
		List<RunnerUser> users=dao.listUsers();
		assertTrue(users.size()>=2);
		dao.deleteUser(user1.getUserName());
		dao.deleteUser(user2.getUserName());
	}

	public void testSaveUpdateUser() {
		RunnerUser user = getDemoUser("demouser");
		dao.saveUpdateUser(user);
		RunnerUser userComp= dao.getUser(user.getUserName());
		assertNotNull(userComp);
		assertEquals(user.getUserName(),userComp.getUserName());
		assertEquals(user.getFullName(),userComp.getFullName());
		assertEquals(user.getPassword(),userComp.getPassword());
		dao.deleteUser(user.getUserName());
	}

	/*public void testCreateAdminUser() {
		RunnerUser user = getDemoUser("admin");
		user.setFullName("Administration Account");
		user.setIsAdmin(true);
		dao.saveUpdateUser(user);
	}*/
}
