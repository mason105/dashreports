package binky.reportrunner.service.impl;

import java.io.IOException;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.service.misc.JDBCDriverDefinition;

public class DatasourceServiceImplTest extends TestCase {

	public void testGetJDBCDriverDefinitions() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		
		DatasourceService dss = (DatasourceService)ctx.getBean("runnerDatasourceService");
		

		try {			
			for (JDBCDriverDefinition def:dss.getJDBCDriverDefinitions().getDefinitions().values()) {
				System.out.println(def.getLabel() + ", " + def.getDriverName() + ", "  + def.getUrl());
			}
			
			assertTrue(dss.getJDBCDriverDefinitions().getDefinitions().values().size()>0);

		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (SAXException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}

}
