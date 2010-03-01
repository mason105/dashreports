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
 * Module: DeleteJob.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.job;

import org.apache.log4j.Logger;

import binky.reportrunner.exceptions.InvalidParameterException;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class DeleteJob extends StandardRunnerAction {
	private static final Logger logger = Logger.getLogger(DeleteJob.class);
	private static final long serialVersionUID = 1L;

	private RunnerJobService jobService;
	private String jobName;
	
	@Override
	public String execute() throws Exception {
		if (groupName != null && !groupName.isEmpty()
				&& (jobName != null && !jobName.isEmpty())) {
			// security check
			if (doesUserHaveGroup(groupName) && !isUserReadOnly()) {
				jobService.deleteJob(jobName, groupName);
			} else {
				SecurityException se = new SecurityException("Group "
						+ groupName + " not valid for user "
						+ super.getSessionUser().getUserName());
				logger.fatal(se.getMessage(), se);
				throw se;
			}

		} else {
			InvalidParameterException e = new InvalidParameterException("one or more parameters are invalid!");
			logger.error(e);
			throw e;
		}	
		return SUCCESS;
	}

	public final RunnerJobService getJobService() {
		return jobService;
	}

	public final void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}

	public final String getJobName() {
		return jobName;
	}

	public final void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
}
