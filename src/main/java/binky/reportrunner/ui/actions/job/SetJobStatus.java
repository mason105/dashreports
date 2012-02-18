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
 * Module: SetJobStatus.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.job;

import org.apache.log4j.Logger;

import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.ReportService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class SetJobStatus extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SetJobStatus.class);
	private ReportService jobService;
	private String jobName;
	private Boolean jobStatus;

	public Boolean getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(Boolean jobStatus) {
		this.jobStatus = jobStatus;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	@Override
	public String execute() throws Exception {
		logger.info("setting status of job: " + groupName + "." + jobName + " to " + jobStatus);
		if (groupName != null && !groupName.isEmpty()
				&& (jobName != null && !jobName.isEmpty())) {
			// security check
			if (doesUserHaveGroup(groupName) && !isUserReadOnly()) {
				if (jobStatus) {
					logger.debug("resume job");
					jobService.resumeJob(jobName, groupName);
				} else {
					logger.debug("pause job");
					jobService.pauseJob(jobName, groupName);

				}
			} else {
				SecurityException se = new SecurityException("Group "
						+ groupName + " not valid for user "
						+ super.getSessionUser().getUserName());
				logger.fatal(se.getMessage(), se);
				throw se;
			}
		}
		return SUCCESS;
	}

	public final ReportService getJobService() {
		return jobService;
	}

	public final void setJobService(ReportService jobService) {
		this.jobService = jobService;
	}

}
