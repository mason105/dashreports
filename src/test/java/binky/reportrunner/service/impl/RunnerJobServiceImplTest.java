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
 * Module: RunnerJobServiceImplTest.java
 ******************************************************************************/
package binky.reportrunner.service.impl;

import java.util.Calendar;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJob_pk;
import binky.reportrunner.scheduler.SchedulerException;
import binky.reportrunner.service.RunnerJobService;

public class RunnerJobServiceImplTest extends TestCase {

	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			new String[] { "applicationContext.xml" });

	RunnerJobService runnerJobService;

	RunnerGroup testGroup;

	protected void setUp() throws Exception {
		runnerJobService = (RunnerJobService) ctx.getBean("runnerJobService");
		RunnerGroupDao groupDao = (RunnerGroupDao) ctx
				.getBean("runnerGroupDao");
		testGroup = new RunnerGroup();
		testGroup.setGroupName("test"
				+ Calendar.getInstance().getTimeInMillis());
		testGroup.setGroupDescription("test group");
		groupDao.saveUpdateGroup(testGroup);
	}

	protected void tearDown() throws Exception {
		RunnerGroupDao groupDao = (RunnerGroupDao) ctx
				.getBean("runnerGroupDao");
		groupDao.deleteGroup(testGroup.getGroupName());
	}

	private RunnerJob getDemoJob(String jobName) {
		RunnerJob_pk pk = new RunnerJob_pk();
		pk.setGroup(testGroup);
		pk.setJobName(jobName);
		RunnerJob runnerJob = new RunnerJob();
		runnerJob.setPk(pk);
		runnerJob.setOutputUrl("test");
		return runnerJob;
	}

	public void testAddUpdateJob() {
		RunnerJob runnerJob = getDemoJob("test job1");
		try {
			runnerJobService.addUpdateJob(runnerJob);
			RunnerJob compareJob = runnerJobService.getJob(runnerJob.getPk()
					.getJobName(), runnerJob.getPk().getGroup().getGroupName());
			assertEquals(runnerJob.getPk().getJobName(), compareJob.getPk()
					.getJobName());
			assertEquals(runnerJob.getPk().getGroup().getGroupName(),
					compareJob.getPk().getGroup().getGroupName());
			assertEquals(runnerJob.getOutputUrl(), compareJob.getOutputUrl());
			runnerJobService.deleteJob(runnerJob.getPk().getJobName(),
					runnerJob.getPk().getGroup().getGroupName());
		} catch (SchedulerException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testDeleteJob() {
		try {
			RunnerJob runnerJob = getDemoJob("test1");
			runnerJobService.addUpdateJob(runnerJob);
			runnerJobService.deleteJob("test1", testGroup.getGroupName());
			RunnerJob compareJob = runnerJobService.getJob("test1", testGroup
					.getGroupName());
			assertNull(compareJob);
		} catch (SchedulerException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testListJobs() {
		try {
			RunnerJob runnerJob1 = getDemoJob("test1");
			runnerJobService.addUpdateJob(runnerJob1);
			RunnerJob runnerJob2 = getDemoJob("test2");
			runnerJobService.addUpdateJob(runnerJob2);
			List<RunnerJob> jobs = runnerJobService.listJobs(testGroup
					.getGroupName());
			runnerJobService.deleteJob("test1", testGroup.getGroupName());
			runnerJobService.deleteJob("test2", testGroup.getGroupName());
			assertEquals(2, jobs.size());
		} catch (SchedulerException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

}
