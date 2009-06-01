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
 * Module: SchedulerImplTest.java
 ******************************************************************************/
package binky.reportrunner.scheduler.impl;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import binky.reportrunner.scheduler.Scheduler;
import binky.reportrunner.scheduler.SchedulerException;

public class SchedulerImplTest extends TestCase {

	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			new String[] { "applicationContext.xml" });

	Scheduler scheduler;
	String testGroup = "testgroup" + Calendar.getInstance().getTimeInMillis();

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

	public void testAddJobGetStatus() {
		try {
			scheduler.addJob("test1", testGroup,
					"0 11 11 11 11 ?", new Date(), null);
			assertTrue(scheduler.isJobActive("test1", testGroup));
			assertNotNull(scheduler.getNextRunTime("test1", testGroup));
			scheduler.removeJob("test1", testGroup);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testStopStartScheduler() {
		try {
			scheduler.stopScheduler();
			assertFalse(scheduler.isSchedulerActive());
			scheduler.startScheduler();
			assertTrue(scheduler.isSchedulerActive());
		} catch (SchedulerException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	public void testPauseResumeJob() {
		try {
			scheduler.addJob("test1", testGroup,					
					"0 11 11 11 11 ?", new Date(), null);
			assertTrue(scheduler.isJobActive("test1", testGroup));
			scheduler.pauseJob("test1", testGroup);
			assertFalse(scheduler.isJobActive("test1", testGroup));
			scheduler.resumeJob("test1", testGroup);
			assertTrue(scheduler.isJobActive("test1", testGroup));
			scheduler.removeJob("test1", testGroup);
		} catch (SchedulerException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/*
	 * These are rock hard to test - so i'll think on it
	 * 
	 * public void testInvokeJob() { fail("Not yet implemented"); }
	 * 
	 * public void testGetCurrentRunningJobs() { fail("Not yet implemented"); }
	 * 
	 * public void testInterruptRunningJob() { fail("Not yet implemented"); }
	 */

}
