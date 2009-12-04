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
 * Module: DeleteGroup.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.group;

import java.util.List;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerDashboardItem;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class DeleteGroup extends AdminRunnerAction {

	private RunnerGroupDao groupDao;
	private DashboardService dashboardService;
	private RunnerJobService jobService;
	private String groupName;
	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		if (super.getSessionUser().getGroups().contains(groupName)
				|| super.getSessionUser().getIsAdmin()) {

			List<RunnerJob> jobs = jobService.listJobs(groupName);
			if ((jobs != null)
					&& (jobs.size() > 0)) {
				for (RunnerJob job : jobs) {
					jobService.deleteJob(job.getPk().getJobName(), groupName);
				}
			}
			
			//fix for issue 58 - unable to delete groups
			List<RunnerDashboardItem> alerts= dashboardService.getItemsForGroup(groupName);
			if ((alerts!=null)&&(alerts.size()>0)) {
				for (RunnerDashboardItem a: alerts) {
					dashboardService.deleteItem(a.getId());
				}
			}
			
			groupDao.deleteGroup(groupName);
		} else {
			SecurityException se = new SecurityException("Group " + groupName
					+ " not valid for user " + super.getSessionUser().getUserName());
			throw se;
		}
		return SUCCESS;
	}

	public RunnerGroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(RunnerGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public RunnerJobService getJobService() {
		return jobService;
	}

	public void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}

	public DashboardService getDashboardService() {
		return dashboardService;
	}

	public void setDashboardService(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}

}
