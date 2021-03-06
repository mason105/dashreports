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
 * Module: ListCurrentExecutingJobsAction.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.admin;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import binky.reportrunner.data.RunnerDashboardItem;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.service.ReportService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class ListCurrentExecutingJobsAction extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;

	private ReportService jobService;
	private DashboardService dashboardService;
	private List<RunnerJob> jobs;
	private List<RunnerDashboardItem> items;
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String execute() throws Exception {
		this.jobs=jobService.getRunningJobs();
		this.items=dashboardService.getRunningItems();
		return SUCCESS;
	}

	public ReportService getJobService() {
		return jobService;
	}

	public void setJobService(ReportService jobService) {
		this.jobService = jobService;
	}

	public List<RunnerJob> getJobs() {
		return jobs;
	}

	public void setJobs(List<RunnerJob> jobs) {
		this.jobs = jobs;
	}


	public List<RunnerDashboardItem> getItems() {
		return items;
	}

	public void setItems(List<RunnerDashboardItem> items) {
		this.items = items;
	}

	public DashboardService getDashboardService() {
		return dashboardService;
	}

	public void setDashboardService(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}

}
