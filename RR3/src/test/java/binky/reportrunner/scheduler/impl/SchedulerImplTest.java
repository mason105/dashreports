package binky.reportrunner.scheduler.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerJob;
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

	public void testAddJob() {

		RunnerJob job = new RunnerJob();
		job.setJobName("testJob1");
		job.setGroupName("testGroup1");
		job.setOutputUrl("file://testurl");
		job.setRunnerEngine("binky.reportrunner.engine.impl.TestEngine");
		Map<String, Object> engineParameters = new HashMap<String, Object>();
		job.setEngineParameters(engineParameters);
		job.setCronString("0 0 12 * * ?");
		RunnerDataSource datasource = new RunnerDataSource();
		job.setDatasource(datasource);

		try {
			scheduler.addJob(job);
			RunnerJob jobTest = scheduler.getRunnerJob(job.getJobName(), job
					.getGroupName());
			assertEquals(job.getRunnerEngine(), jobTest.getRunnerEngine());
			assertEquals(job.getCronString(), jobTest.getCronString());
			assertTrue(scheduler.isJobActive(job.getJobName(), job
					.getGroupName()));
			scheduler.removeJob(job.getJobName(), job.getGroupName());
		} catch (SchedulerException e) {
			e.printStackTrace();
			fail("Scheduler error: " + e);
		}

	}

	public void testListGroups() {
		RunnerJob job1 = new RunnerJob();
		job1.setJobName("testJob1");
		job1.setGroupName("testGroup1");
		job1.setOutputUrl("file://testurl");
		job1.setRunnerEngine("binky.reportrunner.engine.impl.TestEngine");
		Map<String, Object> engineParameters = new HashMap<String, Object>();
		job1.setEngineParameters(engineParameters);
		job1.setCronString("0 0 12 * * ?");
		RunnerDataSource datasource = new RunnerDataSource();
		job1.setDatasource(datasource);

		RunnerJob job2 = new RunnerJob();
		job2.setJobName("testJob2");
		job2.setGroupName("testGroup2");
		job2.setOutputUrl("file://testurl");
		job2.setRunnerEngine("binky.reportrunner.engine.impl.TestEngine");
		job2.setEngineParameters(engineParameters);
		job2.setCronString("0 0 12 * * ?");
		job2.setDatasource(datasource);

		try {
			scheduler.addJob(job1);
			scheduler.addJob(job2);

			List<String> groups = scheduler.listGroups();

			assertTrue(groups.contains("testGroup1"));
			assertTrue(groups.contains("testGroup2"));

			scheduler.removeJob(job1.getJobName(), job1.getGroupName());
			scheduler.removeJob(job2.getJobName(), job2.getGroupName());
		} catch (SchedulerException e) {
			e.printStackTrace();		
			fail("Scheduler error: " + e);
		}
	}

	public void testGetNextRunTime() {
		RunnerJob job = new RunnerJob();
		job.setJobName("testJob1");
		job.setGroupName("testGroup1");
		job.setOutputUrl("file://testurl");
		job.setRunnerEngine("binky.reportrunner.engine.impl.TestEngine");
		Map<String, Object> engineParameters = new HashMap<String, Object>();
		job.setEngineParameters(engineParameters);
		job.setCronString("0 0 12 * * ?");
		RunnerDataSource datasource = new RunnerDataSource();
		job.setDatasource(datasource);

		try {
			scheduler.addJob(job);

			Date nextRunTime = scheduler.getNextRunTime(job.getJobName(), job
					.getGroupName());
			Calendar cal = Calendar.getInstance();
			if ((cal.get(Calendar.HOUR) > 12)
					|| ((cal.get(Calendar.HOUR) == 12) && (cal
							.get(Calendar.MINUTE) > 0
							|| cal.get(Calendar.SECOND) > 0 || cal
							.get(Calendar.MILLISECOND) > 0))) {
				// past midday
				Calendar timeToTest = Calendar.getInstance();
				timeToTest.add(Calendar.DAY_OF_MONTH, 1);
				timeToTest.set(Calendar.HOUR, 12);
				timeToTest.set(Calendar.MINUTE, 0);
				timeToTest.set(Calendar.SECOND, 0);
				timeToTest.set(Calendar.MILLISECOND, 0);
				assertEquals(timeToTest.getTimeInMillis(), nextRunTime
						.getTime());

			} else {
				// before midday
				Calendar timeToTest = Calendar.getInstance();
				timeToTest.set(Calendar.HOUR, 12);
				timeToTest.set(Calendar.MINUTE, 0);
				timeToTest.set(Calendar.SECOND, 0);
				timeToTest.set(Calendar.MILLISECOND, 0);
				assertEquals(timeToTest.getTimeInMillis(), nextRunTime
						.getTime());
			}

			scheduler.removeJob(job.getJobName(), job.getGroupName());
		} catch (SchedulerException e) {
			e.printStackTrace();
			fail("Scheduler error: " + e);
		}

	}

	public void testIsJobActive() {
		RunnerJob job = new RunnerJob();
		job.setJobName("testJob1");
		job.setGroupName("testGroup1");
		job.setOutputUrl("file://testurl");
		job.setRunnerEngine("binky.reportrunner.engine.impl.TestEngine");
		Map<String, Object> engineParameters = new HashMap<String, Object>();
		job.setEngineParameters(engineParameters);
		job.setCronString("0 0 12 * * ?");
		RunnerDataSource datasource = new RunnerDataSource();
		job.setDatasource(datasource);

		try {
			scheduler.addJob(job);

			assertTrue(scheduler.isJobActive(job.getJobName(), job
					.getGroupName()));
			
			scheduler.removeJob(job.getJobName(), job.getGroupName());
		} catch (SchedulerException e) {
			e.printStackTrace();
			fail("Scheduler error: " + e);
		}
	}

	public void testListJobs() {
		RunnerJob job1 = new RunnerJob();
		job1.setJobName("testJob1");
		job1.setGroupName("testGroup");
		job1.setOutputUrl("file://testurl");
		job1.setRunnerEngine("binky.reportrunner.engine.impl.TestEngine");
		Map<String, Object> engineParameters = new HashMap<String, Object>();
		job1.setEngineParameters(engineParameters);
		job1.setCronString("0 0 12 * * ?");
		RunnerDataSource datasource = new RunnerDataSource();
		job1.setDatasource(datasource);

		RunnerJob job2 = new RunnerJob();
		job2.setJobName("testJob2");
		job2.setGroupName("testGroup");
		job2.setOutputUrl("file://testurl");
		job2.setRunnerEngine(" binky.reportrunner.engine.impl.TestEngine");
		job2.setEngineParameters(engineParameters);
		job2.setCronString("0 0 12 * * ?");
		job2.setDatasource(datasource);

		try {
			scheduler.addJob(job1);
			scheduler.addJob(job2);

			List<String> jobs = scheduler.listJobs("testGroup");

			assertTrue(jobs.contains("testJob1"));
			assertTrue(jobs.contains("testJob2"));

			scheduler.removeJob(job1.getJobName(), job1.getGroupName());
			scheduler.removeJob(job2.getJobName(), job2.getGroupName());
		} catch (SchedulerException e) {
			e.printStackTrace();
			fail("Scheduler error: " + e);
		}
	}

	public void testInvokeJob() {
		fail("Not yet implemented");
	}

	public void testIsSchedulerActive() {
		fail("Not yet implemented");
	}

	public void testRemoveJob() {
		fail("Not yet implemented");
	}
}
