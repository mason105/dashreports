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
 * Module: ListAlerts.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.dashboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerDashboardAlert;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class ListAlerts  extends AdminRunnerAction {

	private static final long serialVersionUID = 1L;
	private DashboardService dashboardService;
	private RunnerGroupDao groupDao;
	
	private Map<RunnerGroup,List<RunnerDashboardAlert>> alerts;
	@Override
	public String execute() throws Exception {
		alerts=new HashMap<RunnerGroup, List<RunnerDashboardAlert>>();
		List<RunnerGroup> groups = groupDao.listGroups();
		for (RunnerGroup group:groups){
			List<RunnerDashboardAlert> a = dashboardService.getAlertsForGroup(group.getGroupName());
			alerts.put(group, a);
		}
		return SUCCESS;
	}
	public DashboardService getDashboardService() {
		return dashboardService;
	}
	public void setDashboardService(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}
	public RunnerGroupDao getGroupDao() {
		return groupDao;
	}
	public void setGroupDao(RunnerGroupDao groupDao) {
		this.groupDao = groupDao;
	}
	public Map<RunnerGroup, List<RunnerDashboardAlert>> getAlerts() {
		return alerts;
	}
	public void setAlerts(Map<RunnerGroup, List<RunnerDashboardAlert>> alerts) {
		this.alerts = alerts;
	}

	
}
