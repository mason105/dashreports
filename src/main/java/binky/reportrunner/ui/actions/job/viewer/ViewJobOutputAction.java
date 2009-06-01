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
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.RunnerHistoryDao;
import binky.reportrunner.data.RunnerHistoryEvent;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.engine.ViewerResults;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class ViewJobOutputAction extends StandardRunnerAction {

	private static final long serialVersionUID = 2677747746707641721L;

	private String jobName;

	private String groupName;

	private List<RunnerJobParameter> parameters;

	private Map<String, ViewerResults> results;
	
	private RunnerHistoryDao historyDao;
	
	
	private RunnerJobService jobService;
	private static final Logger logger = Logger.getLogger(ViewJobOutputAction.class);
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		long startTime = (new Date()).getTime();
		//TODO: NASTY HACK ALERT
		if ((this.parameters != null) && (this.parameters.size() > 0)) {
			RunnerJob job = jobService.getJob(jobName, groupName);
			
			List<RunnerJobParameter> parameters = job.getParameters();
			
			for (int i=0;i<parameters.size();i++) {
				RunnerJobParameter p = parameters.get(i);
				p.setParameterValue(this.parameters.get(i).getParameterValue());
			}
			
			results = jobService.getResultsForJob(jobName, groupName,
					parameters);
		} else {
			results = jobService.getResultsForJob(jobName, groupName);
		}
		
		if (logger.isDebugEnabled()) {
			//Quick debuggering
			for (String key:results.keySet()) {
				List rows= results.get(key).getRowSet().getRows();
				logger.debug("key="+key+" row count="+rows.size());
				for (Object o:rows) {
					logger.debug("key="+key+" row=" + o);
				}
			}
		}
		
		long endTime = (new Date()).getTime();
		
		//create an event for this so we can track performance of bad queries
		RunnerHistoryEvent event = new RunnerHistoryEvent();
		event.setGroupName(groupName);
		event.setJobName(jobName);
		String message = "User:" + super.getSessionUser().getUserName() + " ran job viewer";
		event.setMessage(message);
		event.setRunTime(endTime-startTime);
		event.setTimestamp(new Date());
		event.setSuccess(true);
		historyDao.saveEvent(event);
		
		return SUCCESS;
	}

	public RunnerJobService getJobService() {
		return jobService;
	}

	public void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	public Map<String, ViewerResults> getResults() {
		return results;
	}

	public void setResults(Map<String, ViewerResults> results) {
		this.results = results;
	}

	public RunnerHistoryDao getHistoryDao() {
		return historyDao;
	}

	public void setHistoryDao(RunnerHistoryDao historyDao) {
		this.historyDao = historyDao;
	}



}
