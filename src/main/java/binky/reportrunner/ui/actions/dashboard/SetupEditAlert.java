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
 * Module: SetupEditAlert.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.dashboard;

import java.util.Arrays;
import java.util.List;

import binky.reportrunner.dao.RunnerDataSourceDao;
import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerDashboardAlert;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerDashboardAlert.ChartSize;
import binky.reportrunner.data.RunnerDashboardAlert.ChartType;
import binky.reportrunner.data.RunnerDashboardAlert.DisplayType;
import binky.reportrunner.data.RunnerDashboardAlert.XAxisStep;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

import com.opensymphony.xwork2.Preparable;

public class SetupEditAlert extends AdminRunnerAction implements Preparable {

	private static final long serialVersionUID = 1L;

	private DashboardService dashboardService;

	private RunnerDashboardAlert dashboardAlert;

	private Integer id;

	private RunnerDataSourceDao dataSourceDao;

	private RunnerGroupDao groupDao;

	private List<RunnerGroup> groups;

	private List<RunnerDataSource> runnerDataSources;

	public void prepare() throws Exception {
		runnerDataSources = dataSourceDao.listDataSources();
		groups = groupDao.listGroups();
	}

	@Override
	public String execute() throws Exception {
		if (id==null) {
			dashboardAlert=new RunnerDashboardAlert();
		} else {
			dashboardAlert = dashboardService.getAlert(id);
		}
		return SUCCESS;
	}

	public DashboardService getDashboardService() {
		return dashboardService;
	}

	public void setDashboardService(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}


	public RunnerDashboardAlert getDashboardAlert() {
		return dashboardAlert;
	}

	public void setDashboardAlert(RunnerDashboardAlert dashboardAlert) {
		this.dashboardAlert = dashboardAlert;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<ChartType> getChartTypes() {
		return Arrays.asList(ChartType.values());
	}

	public List<DisplayType> getDisplayTypes() {
		return Arrays.asList(DisplayType.values());
	}
	
	public List<ChartSize> getChartSizes() {
		return Arrays.asList(ChartSize.values());
	}
	
	public List<XAxisStep> getXAxisSteps() {
		return Arrays.asList(XAxisStep.values());
	}
	
	
	public RunnerDataSourceDao getDataSourceDao() {
		return dataSourceDao;
	}

	public void setDataSourceDao(RunnerDataSourceDao dataSourceDao) {
		this.dataSourceDao = dataSourceDao;
	}

	public RunnerGroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(RunnerGroupDao groupDao) {
		this.groupDao = groupDao;
	}



	public List<RunnerDataSource> getRunnerDataSources() {
		return runnerDataSources;
	}

	public void setRunnerDataSources(List<RunnerDataSource> runnerDataSources) {
		this.runnerDataSources = runnerDataSources;
	}

	public List<RunnerGroup> getGroups() {
		return groups;
	}
}
