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
			DataSource ds = dsService.getDataSource(rds);
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
