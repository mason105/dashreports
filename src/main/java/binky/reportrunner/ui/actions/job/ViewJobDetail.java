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
 * Module: ViewJobDetail.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerHistoryEvent;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;
import binky.reportrunner.ui.actions.job.beans.DisplayJob;

public class ViewJobDetail extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;



	private String jobName;
	private DisplayJob job;
	private List<RunnerHistoryEvent> events;
	private static final Logger logger = Logger.getLogger(ViewJobDetail.class);
	private RunnerJobService jobService;

	@Override
	public String execute() throws Exception {
		if (groupName != null && !groupName.isEmpty()
				&& (jobName != null && !jobName.isEmpty())) {
			// security check
			if (doesUserHaveGroup(groupName)) {
				RunnerJob job = jobService.getJob(jobName, groupName);
				this.job=new DisplayJob();
				this.job.setDescription(job.getDescription());
				this.job.setGroupName(groupName);
				this.job.setJobName(jobName);
				
				if ((job.getCronString()!=null)&&(!job.getCronString().trim().isEmpty())) {
				
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					this.job.setNextRunTime(sdf.format(jobService.getNextRunTime(jobName, groupName)));
				
					Date prt = jobService.getPreviousRunTime(jobName, groupName);
					if (prt!=null) {
						this.job.setPreviousRunTime(sdf.format(prt));
					}
				}
				this.job.setIsScheduleActive(jobService.isJobActive(jobName, groupName));
				
			} else {
				SecurityException se = new SecurityException("Group "
						+ groupName + " not valid for user "
						+ super.getSessionUser().getUserName());
				logger.fatal(se.getMessage(), se);
				throw se;
			}

		} else {
			job = new DisplayJob();
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

	public final DisplayJob getJob() {
		return job;
	}

	public final void setJob(DisplayJob job) {
		this.job = job;
	}

	public List<RunnerHistoryEvent> getEvents() {
		return events;
	}


	
	

}
