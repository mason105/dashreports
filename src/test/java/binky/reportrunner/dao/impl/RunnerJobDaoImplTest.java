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
 * Module: RunnerJobDaoImplTest.java
 ******************************************************************************/
package binky.reportrunner.dao.impl;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.dao.RunnerJobDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.data.RunnerJobParameter_pk;
import binky.reportrunner.data.RunnerJob_pk;

public class RunnerJobDaoImplTest extends TestCase {

	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			new String[] { "applicationContext.xml" });

	RunnerJobDao dao;
	RunnerGroup testGroup;
	
	@Override
	protected void setUp() throws Exception {
		this.dao = (RunnerJobDao) ctx.getBean("runnerJobDao");
		RunnerGroupDao groupDao = (RunnerGroupDao)ctx.getBean("runnerGroupDao");
		testGroup = RunnerGroupDaoImplTest.getDemoGroup("test"+Calendar.getInstance().getTimeInMillis());
		groupDao.saveUpdateGroup(testGroup);
	}

	protected void tearDown() throws Exception {
		RunnerGroupDao groupDao = (RunnerGroupDao)ctx.getBean("runnerGroupDao");
		groupDao.deleteGroup(testGroup.getGroupName());
	}
	private RunnerJob getDemoJob(String jobName){
		RunnerJob_pk pk = new RunnerJob_pk();
		pk.setGroup(testGroup);
		pk.setJobName(jobName);
		RunnerJob runnerJob= new RunnerJob();
		runnerJob.setPk(pk);
		runnerJob.setOutputUrl("test");
		return runnerJob;
	}
	
	public void testListJobs() {
		RunnerJob runnerJob1 = getDemoJob("test1");
		dao.saveUpdateJob(runnerJob1);
		RunnerJob runnerJob2 = getDemoJob("test2");
		dao.saveUpdateJob(runnerJob2);
		List<RunnerJob> jobs = dao.listJobs(testGroup.getGroupName()); 
		dao.deleteJob("test1", testGroup.getGroupName());
		dao.deleteJob("test2", testGroup.getGroupName());
		assertEquals(2,jobs.size());
	}

	public void testSaveUpdateJob() {

		RunnerJob runnerJob = getDemoJob("test1");
		RunnerJobParameter p = new RunnerJobParameter();
		RunnerJobParameter_pk ppk = new RunnerJobParameter_pk();
		ppk.setParameterIdx(1);
		p.setPk(ppk);
		p.setParameterValue("10000");
		List<RunnerJobParameter> params = new LinkedList<RunnerJobParameter>();
		params.add(p);
		runnerJob.setParameters(params);
		dao.saveUpdateJob(runnerJob);
		RunnerJob compareJob = dao.getJob("test1", testGroup.getGroupName());
		
		assertEquals(runnerJob.getPk().getJobName(), compareJob.getPk().getJobName());
		assertEquals(runnerJob.getPk().getGroup().getGroupName(), compareJob.getPk().getGroup().getGroupName());
		assertEquals(runnerJob.getOutputUrl(),compareJob.getOutputUrl());
		assertTrue(runnerJob.getParameters().size()>0);
		dao.deleteJob("test1", testGroup.getGroupName());

	}

	public void testDeleteJob() {
		
		RunnerJob runnerJob = getDemoJob("test1");
		dao.saveUpdateJob(runnerJob);
		dao.deleteJob("test1", testGroup.getGroupName());
		RunnerJob compareJob = dao.getJob("test1", testGroup.getGroupName());
		assertNull(compareJob);		
	}
}
