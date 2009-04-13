/*
 * $Id: IndexAction.java,v 1.2 2009-04-13 01:11:11 danielgrout Exp $
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerDashboardAlert;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;
import binky.reportrunner.ui.actions.dashboard.DashboardBean;

/**
 * 
 */
public class IndexAction extends StandardRunnerAction {

	private DashboardService dashboardService;
	private static final long serialVersionUID = 9093344521097271797L;
	private Map<String,List<DashboardBean>> dashboardBeans;
	private RunnerGroupDao groupDao;
	public String execute() throws Exception {
	
		dashboardBeans = new HashMap<String, List<DashboardBean>>();
		
		List<RunnerGroup> groups;
		
		if (super.getSessionUser().getIsAdmin()) {
			groups=groupDao.listGroups();
		} else {
			groups=getSessionUser().getGroups();
		}
		
		for (RunnerGroup g: groups) {
			List<RunnerDashboardAlert> alerts = dashboardService.getAlertsForGroup(g.getGroupName());
			List<DashboardBean>beans=new LinkedList<DashboardBean>();
			for (RunnerDashboardAlert a:alerts) {
				DashboardBean bean = new DashboardBean();
				bean.setAlertName(a.getAlertName());
				switch (a.getDisplayType()) {
				case CHART:
					String chartUID = dashboardService.getChartForAlert(a.getId());
					bean.setChart(true);
					bean.setChartUID(chartUID);
					break;
				case GRID:
					bean.setChart(false);
					bean.setData(a.getCurrentDataset());
					break;
				}
				beans.add(bean);
			}
			dashboardBeans.put(g.getGroupName(), beans);
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

	public Map<String, List<DashboardBean>> getDashboardBeans() {
		return dashboardBeans;
	}

	public void setDashboardBeans(Map<String, List<DashboardBean>> dashboardBeans) {
		this.dashboardBeans = dashboardBeans;
	}
	
}
