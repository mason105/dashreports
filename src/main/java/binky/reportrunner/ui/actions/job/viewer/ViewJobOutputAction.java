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

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.log4j.Logger;

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
	private List<RunnerJobParameter> parameters;
	private Map<String,RowSetDynaClass> gridResults;

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
							logger.debug(p.getParameterValue() + " " + p.getParameterValue().equals("**********"));
							if (!p.getParameterValue().equals("**********"))  {
								logger.debug("setting parameter value for + " +  p.getPk().getParameterIdx() + " to: " + p.getParameterValue());
								jp.setParameterValue(p.getParameterValue());
							}
							break;
						}
					}
				}
				dynaSets = jobService.getResultSet(groupName, jobName,
						jobParameters);

			} else {
				logger.debug("not using parameters");
				dynaSets = jobService.getResultSet(groupName, jobName);
			}


			this.gridResults= dynaSets;		
						

			// create an event for this so we can track performance of bad
			// queries			
		
			logger.debug("redirecting to grid");
			return "GRID";			
		
		} else {
			logger.debug("doing file download");
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


			// create an event for this so we can track performance of bad
			// queries
			
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




	public Map<String, RowSetDynaClass> getGridResults() {
		return gridResults;
	}

	public void setGridResults(Map<String, RowSetDynaClass> gridResults) {
		this.gridResults = gridResults;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}




}
