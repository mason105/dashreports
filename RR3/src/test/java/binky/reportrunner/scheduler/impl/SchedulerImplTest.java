package binky.reportrunner.scheduler.impl;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import binky.reportrunner.scheduler.Scheduler;
import binky.reportrunner.scheduler.SchedulerException;

public class SchedulerImplTest extends TestCase {

	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			new String[] { "applicationContext.xml" });

	Scheduler scheduler;

	@Override
	protected void setUp() throws Exception {
		this.scheduler = (Scheduler) ctx.getBean("scheduler");
	}

	protected void tearDown() throws Exception {

	}


	public void testIsSchedulerActive() {
		try {
			assertTrue(scheduler.isSchedulerActive());
		} catch (SchedulerException e) {			
			e.printStackTrace();
			fail("Scheduler error: " + e);
		}
	}

	
}
