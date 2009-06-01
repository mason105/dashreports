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
 * Module: ChangeAllGroupJobStatus.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.group;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class ChangeAllGroupJobStatus extends StandardRunnerAction {

	private RunnerGroupDao groupDao;
	private RunnerJobService jobService;
	private String groupName;
	private Boolean status;

	public void setStatus(Boolean status) {
		this.status = status;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		if (super.getSessionUser().getGroups().contains(groupName)
				|| super.getSessionUser().getIsAdmin()) {

			RunnerGroup group = groupDao.getGroup(groupName);
			if (super.getSessionUser().getGroups().contains(group)) {
				for (RunnerJob job : group.getRunnerJobs()) {
					if (status) {
						jobService.resumeJob(job.getPk().getJobName(),
								groupName);

					} else {
						jobService
								.pauseJob(job.getPk().getJobName(), groupName);
					}
				}
			} else {
				SecurityException se = new SecurityException("Group "
						+ groupName + " not valid for user "
						+ super.getSessionUser().getUserName());
				// logger.fatal(se.getMessage(),se);
				throw se;
			}
		} else {
			SecurityException se = new SecurityException("Group " + groupName
					+ " not valid for user " + super.getSessionUser().getUserName());
			throw se;
		}
		return SUCCESS;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public RunnerGroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(RunnerGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public RunnerJobService getJobService() {
		return jobService;
	}

	public void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}

}
