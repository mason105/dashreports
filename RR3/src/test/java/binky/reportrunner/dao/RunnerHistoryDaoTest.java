package binky.reportrunner.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import binky.reportrunner.data.RunnerHistoryEvent;

public class RunnerHistoryDaoTest extends TestCase {

	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			new String[] { "applicationContext.xml" });

	RunnerHistoryDao dao;

	@Override
	protected void setUp() throws Exception {
		this.dao = (RunnerHistoryDao) ctx.getBean("runnerHistoryDao");
	}

	@Override
	protected void tearDown() throws Exception {
		this.dao.deleteAllEvents("testgroup", "testjob");
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
