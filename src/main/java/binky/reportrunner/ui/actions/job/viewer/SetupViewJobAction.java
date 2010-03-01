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
 * Module: SetupViewJobAction.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.job.viewer;

import java.util.List;
import java.util.Map;

import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class SetupViewJobAction extends StandardRunnerAction {

	private static final long serialVersionUID = 7570287447973430981L;

	private String jobName;
	private Map<RunnerJobParameter, List<Object>> parameters;
	private RunnerJobService jobService;
	
	@Override
	public String execute() throws Exception {
		
		parameters = jobService.getPossibleParameterValues(jobName, groupName);		
		
		return SUCCESS;
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

	public Map<RunnerJobParameter, List<Object>> getParameters() {
		return parameters;
	}

	public void setParameters(Map<RunnerJobParameter, List<Object>> parameters) {
		this.parameters = parameters;
	}
	
}
