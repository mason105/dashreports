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
 * Module: ViewJobOutputAction.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.job.viewer;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.log4j.Logger;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerHistoryEvent;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.engine.beans.ViewerResults;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class ViewJobOutputAction extends StandardRunnerAction {

	private static final long serialVersionUID = 2677747746707641721L;

	private String jobName;

	private Map<String, ViewerResults> downloadResults;
	private Map<String, List<String>> columns;
	private ReportRunnerDao<RunnerHistoryEvent, Long> historyDao;
	private List<RunnerJobParameter> parameters;
	private Map<String,List<Map<String, Object>>> gridResults;

	private RunnerJobService jobService;

	 private static final Logger logger = Logger.getLogger(ViewJobOutputAction.class);
	@Override
	public String execute() throws Exception {

		if (!super.doesUserHaveGroup(groupName))
			throw new SecurityException(
					"User does not have permissions for group: " + groupName);
		

		
		if (jobService.getJob(jobName, groupName)!=null && jobService.getJob(jobName, groupName).getTemplateType()
				.equals(RunnerJob.Template.NONE)) {
			logger.debug("populating grid");
			Map<String,RowSetDynaClass> dynaSets;
		
			if ((this.parameters != null) && (this.parameters.size() > 0)) {
				logger.debug("using parameters");
				RunnerJob job = jobService.getJob(jobName, groupName);

				List<RunnerJobParameter> jobParameters = job.getParameters();

				for (RunnerJobParameter p : this.parameters) {

					for (RunnerJobParameter jp : jobParameters) {
						if (jp.getPk().getParameterIdx()
								.equals(p.getPk().getParameterIdx())) {
							jp.setParameterValue(p.getParameterValue());
							break;
						}
					}
				}
				dynaSets = jobService.getResultSet(groupName, jobName,
						parameters);

			} else {
				logger.debug("not using parameters");
				dynaSets = jobService.getResultSet(groupName, jobName);
			}


			this.gridResults= new LinkedHashMap<String, List<Map<String,Object>>>();			
			
			this.columns = new HashMap<String, List<String>>();
			
			for (String key : dynaSets.keySet()) {
				List<Map<String,Object>> rows = new LinkedList<Map<String,Object>>();
				logger.debug("getting columns for result:" + key);
				// populate columns
				List<String> cols = new LinkedList<String>();
				RowSetDynaClass rsdc = dynaSets.get(key);				
				for (DynaProperty d : rsdc.getDynaProperties()) {
					logger.debug("got column: " + d.getName());
					cols.add(d.getName());
				}
				columns.put(key, cols);
				
				
				//populate data
				for (Object r:dynaSets.get(key).getRows()) {
					Map<String, Object> row=new LinkedHashMap<String, Object>();
					DynaBean b = (DynaBean) r;
					for (String c:cols)  {
						Object v = b.get(c);
						row.put(c, v);
					}
					rows.add(row);
				}
				this.gridResults.put(key, rows);
			}
			

			logger.debug("redirecting to grid");
			return "GRID";			
		
		} else {
			logger.debug("doing file download");
			long startTime = (new Date()).getTime();
			// TODO: NASTY HACK ALERT
			if ((this.parameters != null) && (this.parameters.size() > 0)) {
				RunnerJob job = jobService.getJob(jobName, groupName);

				List<RunnerJobParameter> jobParameters = job.getParameters();

				for (RunnerJobParameter p : this.parameters) {

					for (RunnerJobParameter jp : jobParameters) {
						if (jp.getPk().getParameterIdx()
								.equals(p.getPk().getParameterIdx())) {
							jp.setParameterValue(p.getParameterValue());
							break;
						}
					}

				}

				downloadResults = jobService.getResultsForJob(jobName,
						groupName, jobParameters);
			} else {
				downloadResults = jobService.getResultsForJob(jobName,
						groupName);
			}

			long endTime = (new Date()).getTime();

			// create an event for this so we can track performance of bad
			// queries
			RunnerHistoryEvent event = new RunnerHistoryEvent();
			event.setGroupName(groupName);
			event.setJobName(jobName);
			String message = "User:" + super.getSessionUser().getUserName()
					+ " ran job viewer";
			event.setMessage(message);
			event.setRunTime(endTime - startTime);
			event.setTimestamp(new Date());
			event.setSuccess(true);
			historyDao.saveOrUpdate(event);

			return "DOWNLOAD";
		}
	}

	public RunnerJobService getJobService() {
		return jobService;
	}

	public void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public List<RunnerJobParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<RunnerJobParameter> parameters) {
		this.parameters = parameters;
	}

	public void setHistoryDao(
			ReportRunnerDao<RunnerHistoryEvent, Long> historyDao) {
		this.historyDao = historyDao;
	}

	public Map<String, ViewerResults> getDownloadResults() {
		return downloadResults;
	}

	public void setDownloadResults(Map<String, ViewerResults> downloadResults) {
		this.downloadResults = downloadResults;
	}

	public Map<String, List<String>> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, List<String>> columns) {
		this.columns = columns;
	}


	public Map<String, List<Map<String, Object>>> getGridResults() {
		return gridResults;
	}

	public void setGridResults(Map<String, List<Map<String, Object>>> gridResults) {
		this.gridResults = gridResults;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ReportRunnerDao<RunnerHistoryEvent, Long> getHistoryDao() {
		return historyDao;
	}

}
