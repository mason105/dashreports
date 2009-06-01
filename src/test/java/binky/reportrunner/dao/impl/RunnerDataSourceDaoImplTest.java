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
 * Module: RunnerDataSourceDaoImplTest.java
 ******************************************************************************/
package binky.reportrunner.dao.impl;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import binky.reportrunner.dao.RunnerDataSourceDao;
import binky.reportrunner.data.RunnerDataSource;

public class RunnerDataSourceDaoImplTest extends TestCase {

	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			new String[] { "applicationContext.xml" });

	RunnerDataSourceDao dao;

	protected void setUp() throws Exception {
		dao = (RunnerDataSourceDao) ctx.getBean("runnerDataSourceDao");
	}

	protected void tearDown() throws Exception {
	}

	private RunnerDataSource getDemoDataSource(String id) {
		RunnerDataSource ds = new RunnerDataSource();
		ds.setDataSourceName(id);
		ds.setJdbcClass("testclass");
		ds.setJdbcUrl("testurl");
		ds.setJndiName("testjndi");
		ds.setPassword("testpassword");
		ds.setUsername("testusername");
		return ds;
	}

	private void compareDs(RunnerDataSource ds1, RunnerDataSource ds2) {
		assertEquals(ds1.getDataSourceName(), ds2.getDataSourceName());
		assertEquals(ds1.getJdbcClass(), ds2.getJdbcClass());
		assertEquals(ds1.getJdbcUrl(), ds2.getJdbcUrl());
		assertEquals(ds1.getJndiName(), ds2.getJndiName());
		assertEquals(ds1.getPassword(), ds2.getPassword());
		assertEquals(ds1.getUsername(), ds2.getUsername());
	}

	public void testSaveUpdateDataSource() {
		RunnerDataSource ds = getDemoDataSource("1");
		dao.saveUpdateDataSource(ds);

		RunnerDataSource ds_test = dao.getDataSource("1");
		compareDs(ds, ds_test);

		dao.deleteDataSource("1");

	}

	public void testDeleteDataSource() {
		RunnerDataSource ds = getDemoDataSource("1");
		dao.saveUpdateDataSource(ds);
		dao.deleteDataSource("1");
		RunnerDataSource ds_test = dao.getDataSource("1");
		assertEquals(null, ds_test);
	}

	public void testListDataSources() {
		RunnerDataSource ds = getDemoDataSource("1");
		RunnerDataSource ds2 = getDemoDataSource("2");
		dao.saveUpdateDataSource(ds);
		dao.saveUpdateDataSource(ds2);

		assertTrue(dao.listDataSources().size() >= 2);
		dao.deleteDataSource("1");
		dao.deleteDataSource("2");

	}

}
