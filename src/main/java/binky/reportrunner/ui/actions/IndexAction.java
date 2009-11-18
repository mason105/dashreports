/*
 * $Id: IndexAction.java,v 1.6 2009-05-05 15:17:50 danielgrout Exp $
 *
 * Copyright 2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package binky.reportrunner.ui.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerDashboardAlert;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

/**
 * 
 */
public class IndexAction extends StandardRunnerAction {

	private DashboardService dashboardService;
	private static final long serialVersionUID = 9093344521097271797L;
	private static final Logger logger = Logger.getLogger(IndexAction.class);
	private  Map<String, List<RunnerDashboardAlert>>  alerts;
	private RunnerGroupDao groupDao;
	private Integer currentRow;
	public String execute() throws Exception {
	
		alerts = new  HashMap<String, List<RunnerDashboardAlert>>();
		
		List<RunnerGroup> groups;
		
		if (super.getSessionUser().getIsAdmin()) {
			logger.debug("is admin so fetching admin groups");
			groups=groupDao.listGroups();
		} else {
			logger.debug("is not admin so pulling groups from session");
			groups=getSessionUser().getGroups();
		}
		
		for (RunnerGroup g: groups) {
			
			List<RunnerDashboardAlert> a = dashboardService.getAlertsForGroup(g.getGroupName());		
			alerts.put(g.getGroupName(), a);
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

	public Map<String, List<RunnerDashboardAlert>> getAlerts() {
		return alerts;
	}

	public void setAlerts(Map<String, List<RunnerDashboardAlert>> alerts) {
		this.alerts = alerts;
	}

	public Integer getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(Integer currentRow) {
		this.currentRow = currentRow;
	}

	
}
