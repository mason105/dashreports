package binky.reportrunner.service.impl;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import binky.reportrunner.service.AuditService;

public class AuditServiceImplTest extends TestCase {

	private AuditService auditService;
	Date from;
	protected void setUp() throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		auditService = (AuditService)ctx.getBean("auditService");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		from=cal.getTime();
		for (int i =0 ; i < 100;i++) {
			auditService.logAuditEvent("testsuccess", true, Math.round(Math.random()*1000), "{test,test}", "test"+i, null);
			auditService.logAuditEvent("testfail", false, Math.round(Math.random()*1000), "{test,test}", "test"+i, "test");
		}
	}

	protected void tearDown() throws Exception {
		auditService.deleteOldEvents(Calendar.getInstance().getTime());
	}

	public void testGetFailedEvents() {
		assertTrue(auditService.getFailedEvents("testfail", from, Calendar.getInstance().getTime()).size()>0);
	}

	public void testGetLongestRunningEvents() {
		assertTrue(auditService.getLongestRunningEvents("testsuccess", from, Calendar.getInstance().getTime()).size()>0);
	}

	public void testGetSuccessEvents() {
		assertTrue(auditService.getSuccessEvents("testsuccess", from, Calendar.getInstance().getTime()).size()>0);
	}

	public void testGetModuleNames() {
		assertTrue(auditService.getModuleNames().size()>0);
	}

}
