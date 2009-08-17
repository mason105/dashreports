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
 * Module: SetupEditJob.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.job;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Preparable;

import binky.reportrunner.dao.RunnerDataSourceDao;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.data.RunnerJobParameter.DataType;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class SetupEditJob extends StandardRunnerAction implements Preparable {

	private static final long serialVersionUID = 1L;
	private String jobName;
	private String groupName;
	private RunnerJob job;
	private static final Logger logger = Logger.getLogger(SetupEditJob.class);
	private RunnerJobService jobService;
	private String activeTab="report";
	private RunnerDataSourceDao dataSourceDao;
	private Integer paramCount;

	private List<RunnerDataSource> dataSources;
	
	public void prepare() throws Exception {
		dataSources = dataSourceDao.listDataSources();

	}

	// TODO:clean up this mess
	@Override
	public String execute() throws Exception {
		if (groupName != null && !groupName.isEmpty()
				&& (jobName != null && !jobName.isEmpty())) {
			// security check
			if (super.doesUserHaveGroup(groupName) && !isUserReadOnly()) {
				job = jobService.getJob(jobName, groupName);
				if (job.getParameters() == null) {
					paramCount = 0;
				} else {
					paramCount = job.getParameters().size();
				}

			} else {
				SecurityException se = new SecurityException("Group "
						+ groupName + " not valid for user "
						+ super.getSessionUser().getUserName());
				logger.fatal(se.getMessage(), se);
				throw se;
			}

		} else if (groupName != null && !groupName.isEmpty()) {
			job = new RunnerJob();
			paramCount = 0;
		} else {
			SecurityException se = new SecurityException("Group not passed");
			logger.fatal(se.getMessage(), se);
		}
		return SUCCESS;
	}

	public final RunnerJobService getJobService() {
		return jobService;
	}

	public final void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}

	public final void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public final String getGroupName() {
		return groupName;
	}

	public final void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public final RunnerJob getJob() {
		return job;
	}

	public RunnerDataSourceDao getDataSourceDao() {
		return dataSourceDao;
	}

	public void setDataSourceDao(RunnerDataSourceDao dataSourceDao) {
		this.dataSourceDao = dataSourceDao;
	}

	public List<RunnerDataSource> getDataSources() {
		return dataSources;
	}

	public Integer getParamCount() {
		return paramCount;
	}

	public List<RunnerJob.FileFormat> getFileFormats() {
		return Arrays.asList(RunnerJob.FileFormat.values());
	}
	

	public List<RunnerJob.Template> getTemplateTypes() {
		return Arrays.asList(RunnerJob.Template.values());
	}
	
	public List<DataType> getDataTypes() {
		return Arrays.asList(RunnerJobParameter.DataType.values());
	}

	public String getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}
	
	
}
