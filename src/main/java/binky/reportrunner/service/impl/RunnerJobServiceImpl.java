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
 * Module: RunnerJobServiceImpl.java
 ******************************************************************************/
package binky.reportrunner.service.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.RunnerJobDao;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.engine.RunnerResultGenerator;
import binky.reportrunner.engine.beans.ViewerResults;
import binky.reportrunner.engine.impl.RunnerResultGeneratorImpl;
import binky.reportrunner.engine.utils.SQLProcessor;
import binky.reportrunner.engine.utils.impl.SQLProcessorImpl;
import binky.reportrunner.exceptions.RenderException;
import binky.reportrunner.scheduler.Scheduler;
import binky.reportrunner.scheduler.SchedulerException;
import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.service.RunnerJobService;

public class RunnerJobServiceImpl implements RunnerJobService {
	private Scheduler scheduler;

	private RunnerJobDao runnerJobDao;

	private DatasourceService dataSourceService;

	private static final Logger logger = Logger
			.getLogger(RunnerJobServiceImpl.class);

	public RunnerJobDao getRunnerJobDao() {
		return runnerJobDao;
	}

	public void setRunnerJobDao(RunnerJobDao runnerJobDao) {
		this.runnerJobDao = runnerJobDao;
	}

	public void addUpdateJob(RunnerJob job) throws SchedulerException {
		String groupName = job.getPk().getGroup().getGroupName();
		String jobName = job.getPk().getJobName();
		logger.debug("add update job: " + groupName + "." + jobName);
		RunnerJob job_comp = runnerJobDao.getJob(jobName, groupName);
		if ((job_comp != null)
				&& ((job_comp.getCronString() != null) && !job_comp
						.getCronString().isEmpty()) && !job_comp.getCronString().equals(job.getCronString())) {
			scheduler.removeJob(jobName, groupName);
		}

		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			scheduler.addJob(jobName, groupName, job
					.getCronString(), job.getStartDate(), job.getEndDate());
		}

		runnerJobDao.saveUpdateJob(job);

	}

	public void deleteJob(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("delete job: " + groupName + "." + jobName);
		RunnerJob job = runnerJobDao.getJob(jobName, groupName);
		runnerJobDao.deleteJob(jobName, groupName);
		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			scheduler.removeJob(jobName, groupName);
		}
	}

	public RunnerJob getJob(String jobName, String groupName) {
		logger.debug("get job: " + groupName + "." + jobName);
		return runnerJobDao.getJob(jobName, groupName);
	}

	public List<RunnerJob> listJobs(String groupName) {
		return runnerJobDao.listJobs(groupName);
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public Boolean isJobActive(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("is job active: " + groupName + "." + jobName);
		RunnerJob job = runnerJobDao.getJob(jobName, groupName);
		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			if (scheduler.isScheduled(jobName, groupName)) {
				return this.scheduler.isJobActive(jobName, groupName);
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void pauseJob(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("pause job: " + groupName + "." + jobName);
		RunnerJob job = runnerJobDao.getJob(jobName, groupName);
		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			scheduler.pauseJob(jobName, groupName);
		}
	}

	public void resumeJob(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("resume job: " + groupName + "." + jobName);
		RunnerJob job = runnerJobDao.getJob(jobName, groupName);
		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			scheduler.resumeJob(jobName, groupName);
		}
	}

	public List<RunnerJob> getRunningJobs() throws SchedulerException {
		List<String> runningJobs = scheduler.getCurrentRunningJobs();
		List<RunnerJob> jobs = new LinkedList<RunnerJob>();
		for (String string : runningJobs) {
			String groupName = string.split(":|:")[0];
			String jobName = string.split(":|:")[2];
			if (!groupName.equals(Scheduler.dashboardSchedulerGroup)) {
				RunnerJob job = runnerJobDao.getJob(jobName, groupName);
				jobs.add(job);
			}
		}
		return jobs;
	}

	public void interruptRunningJob(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("interrupt job: " + groupName + "." + jobName);
		RunnerJob job = runnerJobDao.getJob(jobName, groupName);
		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			scheduler.interruptRunningJob(jobName, groupName);
		}
	}

	public void invokeJob(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("invoke job: " + groupName + "." + jobName);
		RunnerJob job = runnerJobDao.getJob(jobName, groupName);
		if ((job.getCronString() != null) && !job.getCronString().isEmpty()) {
			// if already in scheduler lets go
			scheduler.invokeJob(jobName, groupName);
		} else {
			// schedule it then remove it
			// TODO test this works as it is the mother of all hacks
			Date date = Calendar.getInstance().getTime();
			scheduler.addJob(jobName, groupName, 
					"0/1 * * * * ?", date, new Date(date.getTime() + 1000));
			scheduler.invokeJob(jobName, groupName);
		}

	}

	public Date getPreviousRunTime(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("get previous run time job: " + groupName + "." + jobName);
		if (scheduler.isScheduled(jobName, groupName)) {
			return scheduler.getPreviousRunTime(jobName, groupName);
		} else {
			return null;
		}
	}

	public Date getNextRunTime(String jobName, String groupName)
			throws SchedulerException {
		logger.debug("get next run time job: " + groupName + "." + jobName);
		if (scheduler.isScheduled(jobName, groupName)) {
			return scheduler.getNextRunTime(jobName, groupName);
		} else {
			return null;
		}
	}

	public void pauseGroup(String groupName) throws SchedulerException {
		logger.debug("pause group: " + groupName);
		this.scheduler.pauseGroup(groupName);
	}

	public void resumeGroup(String groupName) throws SchedulerException {
		logger.debug("resume group: " + groupName);
		this.scheduler.resumeGroup(groupName);
	}

	
	
	public Map<String, ViewerResults> getResultsForJob(String jobName,
			String groupName, List<RunnerJobParameter> parameters)
			throws SQLException, NumberFormatException, ParseException, RenderException, IOException {
		Map<String, ViewerResults> results = new HashMap<String, ViewerResults>();
		RunnerJob job = runnerJobDao.getJob(jobName, groupName);
		DataSource ds = dataSourceService.getDataSource(job.getDatasource());
		Connection conn = ds.getConnection();

		// get all the results
		logger.debug("going to get a set of results for job: "
				+ job.getPk().getJobName() + "/"
				+ job.getPk().getGroup().getGroupName());
		RunnerResultGenerator res = new RunnerResultGeneratorImpl(conn);
		Map<String, ResultSet> rs = new HashMap<String, ResultSet>();
		if (parameters != null)
			job.setParameters(parameters);
		res.getResultsForJob(job, rs);

		logger.debug("converting to dynasets");

		for (String key : rs.keySet()) {
			ResultSet result = rs.get(key);
			int lastRow = 0;
			if ((result != null)) {
				// &&(result.last())){
				// lastRow=result.getRow();
				// result.first();
				
				String id =UUID.randomUUID().toString();
				res.renderReport(result,"tmp://"+id+".tmp",job.getTemplateFile(),job.getTemplateType(),job.getFileFormat().toString());
				results.put(key, new ViewerResults(id));

			}
			logger.debug("Tab name=" + key + " rows=" + lastRow);
		}

		conn.close();
		return results;
	}

	public Map<String, ViewerResults> getResultsForJob(String jobName,
			String groupName) throws SQLException, NumberFormatException,
			ParseException,RenderException, IOException {
		return getResultsForJob(jobName, groupName, null);
	}

	public Map<RunnerJobParameter, List<Object>> getPossibleParameterValues(
			String jobName, String groupName) throws SQLException,
			NumberFormatException, ParseException {
		Map<RunnerJobParameter, List<Object>> paramValues = new HashMap<RunnerJobParameter, List<Object>>();
		RunnerJob job = runnerJobDao.getJob(jobName, groupName);
		DataSource ds = dataSourceService.getDataSource(job.getDatasource());
		Connection conn = ds.getConnection();
		SQLProcessor sqlProcessor = new SQLProcessorImpl();
		if ((job.getIsBurst()==null)||(!job.getIsBurst())) {
			for (RunnerJobParameter p : job.getParameters()) {
				paramValues.put(p, null);
			}
			return paramValues;
		}
		logger.debug("getting burst result for " + jobName + "/" + groupName);
		try {
			ResultSet rs = sqlProcessor.getResults(conn, job.getBurstQuery());

			for (RunnerJobParameter p : job.getParameters()) {

				List<Object> values = new LinkedList<Object>();

				if ((p.getParameterBurstColumn() != null)
						&& (!p.getParameterBurstColumn().isEmpty())) {

					//rs.beforeFirst();
					logger.debug("getting values for parameter: "
							+ p.getDescription());
					while (rs.next()) {
						Object value = rs
								.getObject(p.getParameterBurstColumn());
						if (!values.contains(value)) {
							logger.debug("found value: " + value);
							values.add(value);
						}
					}

				}
				paramValues.put(p, values);

			}
		} finally {
			conn.close();
		}
		return paramValues;
	}

	public DataSource getDataSource(RunnerDataSource runnerDs) {
		return dataSourceService.getDataSource(runnerDs);
	}

	public DatasourceService getDataSourceService() {
		return dataSourceService;
	}

	public void setDataSourceService(DatasourceService dataSourceService) {
		this.dataSourceService = dataSourceService;
	}


	

}
