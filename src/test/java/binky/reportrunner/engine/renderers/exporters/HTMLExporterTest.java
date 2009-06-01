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
 * Module: HTMLExporterTest.java
 ******************************************************************************/
package binky.reportrunner.engine.renderers.exporters;

import java.io.File;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import binky.reportrunner.engine.FileSystemHandler;
import binky.reportrunner.engine.SQLProcessor;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class HTMLExporterTest extends TestCase {

	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			new String[] { "applicationContext.xml" });

	public void testExport() {
		try {
			AbstractExporter exp = new HTMLExporter();
			FileSystemHandler fs = new FileSystemHandler();
			OutputStream os = fs.getOutputStreamForUrl("file://"
					+ System.getProperty("java.io.tmpdir") + "/test.file");
			SQLProcessor proc = new SQLProcessor();
			ComboPooledDataSource ds = (ComboPooledDataSource) ctx
					.getBean("dataSource");
			Connection connection = ds.getConnection();
			String sql = "select * from runneruser";
			ResultSet res = proc.getResults(connection, sql);
			exp.export(res, os);
			os.close();
			connection.close();			
			File test =  new File(System.getProperty("java.io.tmpdir") + File.separatorChar + "test.file");
			assertTrue(test.exists());
			assertTrue(test.isFile());
			assertTrue(test.length()>0);
			test.delete();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
