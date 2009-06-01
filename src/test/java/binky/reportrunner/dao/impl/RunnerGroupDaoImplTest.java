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
 * Module: RunnerGroupDaoImplTest.java
 ******************************************************************************/
package binky.reportrunner.dao.impl;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerGroup;

public class RunnerGroupDaoImplTest extends TestCase {

	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			new String[] { "applicationContext.xml" });

	RunnerGroupDao dao;

	protected void setUp() throws Exception {
		dao = (RunnerGroupDao) ctx.getBean("runnerGroupDao");
	}

	public static RunnerGroup getDemoGroup(String id) {
		RunnerGroup group = new RunnerGroup();
		group.setGroupName(id);
		group.setGroupDescription("test group " + id);
		return group;
	}
	
	public void testDeleteGroup() {
		RunnerGroup group =getDemoGroup("group1");
		dao.saveUpdateGroup(group);				
		assertNotNull(dao.getGroup(group.getGroupName()));
		dao.deleteGroup(group.getGroupName());
		assertNull(dao.getGroup(group.getGroupName()));		
	}


	public void testListGroups() {
		RunnerGroup group1 =getDemoGroup("group1");
		dao.saveUpdateGroup(group1);
		RunnerGroup group2 =getDemoGroup("group2");
		dao.saveUpdateGroup(group2);
		assertTrue(dao.listGroups().size()>=2);
		dao.deleteGroup("group1");
		dao.deleteGroup("group2");
	}

	public void testSaveUpdateGroup() {
		RunnerGroup group =getDemoGroup("group1");
		dao.saveUpdateGroup(group);
		RunnerGroup tGroup = dao.getGroup(group.getGroupName());
		assertEquals(group.getGroupName(),tGroup.getGroupName());
		assertEquals(group.getGroupDescription(), tGroup.getGroupDescription());
		dao.deleteGroup(group.getGroupName());
	}

}
