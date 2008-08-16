package binky.reportrunner.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

public class RunnerJobServiceImplTest extends TestCase {
	
	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			new String[] { "applicationContext.xml" });

	protected void setUp() throws Exception {
		
	}

	protected void tearDown() throws Exception {
		
	}

	public void testAddUpdateJob() {
		fail("Not yet implemented");
	}

	public void testDeleteJob() {
		fail("Not yet implemented");
	}

	public void testGetJob() {
		fail("Not yet implemented");
	}

	public void testListJobs() {
		fail("Not yet implemented");
	}


}
