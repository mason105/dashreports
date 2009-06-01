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
 * Module: RunnerHistoryDaoImplTest.java
 ******************************************************************************/
package binky.reportrunner.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import binky.reportrunner.dao.RunnerHistoryDao;
import binky.reportrunner.data.RunnerHistoryEvent;

public class RunnerHistoryDaoImplTest extends TestCase {

	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			new String[] { "applicationContext.xml" });

	RunnerHistoryDao dao;

	@Override
	protected void setUp() throws Exception {
		this.dao = (RunnerHistoryDao) ctx.getBean("runnerHistoryDao");
	}

	@Override
	protected void tearDown() throws Exception {
		this.dao.deleteEvents("testgroup", "testjob");
	}

	public void testGetEvents() {
		RunnerHistoryEvent event1 = new RunnerHistoryEvent();
		event1.setGroupName("testgroup");
		event1.setJobName("testjob");
		event1.setMessage("testmessage1");
		Date now = Calendar.getInstance().getTime();
		event1.setTimestamp(now);

		RunnerHistoryEvent event2 = new RunnerHistoryEvent();
		event2.setGroupName("testgroup");
		event2.setJobName("testjob");
		event2.setMessage("testmessage2");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date then = cal.getTime();
		event2.setTimestamp(then);

		dao.saveEvent(event1);
		dao.saveEvent(event2);

		List<RunnerHistoryEvent> events;

		events = dao.getEvents("testgroup", "testjob");

		assertEquals(2,events.size());

		events = dao.getEvents("testgroup", "testjob", now, then);

		assertEquals(2,events.size());

		events = dao.getEvents("testgroup", "testjob", now, now);

		assertEquals(1,events.size());
		assertEquals(events.get(0).getMessage(), event1.getMessage());
				
		events = dao.getEvents("testgroup", "testjob", then, then);

		assertEquals(1,events.size());
		assertEquals(events.get(0).getMessage(), event2.getMessage());
		
	}

	public void testSaveEvents() {
		RunnerHistoryEvent event3 = new RunnerHistoryEvent();
		event3.setGroupName("testgroup");
		event3.setJobName("testjob");
		event3.setMessage("testmessage3");
		Date now = Calendar.getInstance().getTime();
		event3.setTimestamp(now);

		dao.saveEvent(event3);

		List<RunnerHistoryEvent> events = dao.getEvents("testgroup", "testjob");

		assertEquals(1,events.size());
		assertEquals(events.get(0).getMessage(), event3.getMessage());
	}
}
