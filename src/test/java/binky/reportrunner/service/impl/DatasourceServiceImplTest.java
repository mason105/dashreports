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
 * Module: DatasourceServiceImplTest.java
 ******************************************************************************/
package binky.reportrunner.service.impl;

import java.sql.ResultSet;

import javax.sql.DataSource;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.service.DatasourceService;

public class DatasourceServiceImplTest extends TestCase {
	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			new String[] { "applicationContext.xml" });

	public void testGetDataSource() {
		DatasourceService dsService = (DatasourceService) ctx
				.getBean("runnerDatasourceService");

		RunnerDataSource rds = new RunnerDataSource();
		rds.setDataSourceName("testds");
		rds.setJdbcClass("com.mysql.jdbc.Driver");
		rds.setJdbcUrl("jdbc:mysql://localhost:3306/reportrunner");
		rds.setPassword("dng50010");
		rds.setUsername("reportrunner");
		rds.setMaxPoolSize(5);
		rds.setMinPoolSize(1);
		rds.setInitialPoolSize(3);
		try {
			DataSource ds = dsService.getJDBCDataSource(rds);
			ResultSet rs = ds.getConnection().getMetaData().getCatalogs();
			rs.last();
			int c = rs.getRow();
			assertTrue(c>0);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());			
		}		
	}

}
