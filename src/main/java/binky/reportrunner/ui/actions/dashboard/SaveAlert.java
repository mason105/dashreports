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
 * Module: SaveAlert.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.dashboard;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.RunnerDataSourceDao;
import binky.reportrunner.data.RunnerDashboardAlert;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerDashboardAlert.DisplayType;
import binky.reportrunner.data.RunnerDashboardAlert.Height;
import binky.reportrunner.data.RunnerDashboardAlert.Width;
import binky.reportrunner.data.RunnerDashboardAlert.XAxisStep;
import binky.reportrunner.engine.renderers.ChartRenderer.ChartType;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

import com.opensymphony.xwork2.Preparable;

public class SaveAlert extends StandardRunnerAction implements Preparable {

	private static final long serialVersionUID = 1L;

	private DashboardService dashboardService;

	private RunnerDashboardAlert dashboardAlert;

	private RunnerDataSourceDao dataSourceDao;

	private List<RunnerDataSource> runnerDataSources;

	private static final Logger logger = Logger.getLogger(SaveAlert.class);
	
	public void prepare() throws Exception {
		runnerDataSources = dataSourceDao.listDataSources();
	}

	@Override
	public String execute() throws Exception {
		
		
		logger.debug("alert is " + dashboardAlert.toString());	
		String groupName=dashboardAlert.getGroup().getGroupName();
		
		if (super.getSessionUser().getGroups().contains(groupName)
				|| super.getSessionUser().getIsAdmin()) {

			//preserve teh current data
			if (dashboardAlert.getId()!=null) {
				RunnerDashboardAlert currentAlert = dashboardService.getAlert(dashboardAlert.getId());
				dashboardAlert.setCurrentDataset(currentAlert.getCurrentDataset());
				dashboardAlert.setLastUpdated(currentAlert.getLastUpdated());
			}
			RunnerGroup group = new RunnerGroup();
			group.setGroupName(groupName);
			dashboardAlert.setGroup(group);
			
			dashboardService.saveUpdateAlert(dashboardAlert);

			return SUCCESS;
		} else {

			SecurityException se = new SecurityException("Group " + groupName
					+ " not valid for user "
					+ super.getSessionUser().getUserName());
			throw se;
		}
		
	}

	public DashboardService getDashboardService() {
		return dashboardService;
	}

	public void setDashboardService(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}



	public List<ChartType> getChartTypes() {
		return Arrays.asList(ChartType.values());
	}

	public List<DisplayType> getDisplayTypes() {
		return Arrays.asList(DisplayType.values());
	}
	public List<Width> getWidths() {
		return Arrays.asList(Width.values());
	}
	public List<Height> getHeights() {
		return Arrays.asList(Height.values());
	}
	
	public RunnerDataSourceDao getDataSourceDao() {
		return dataSourceDao;
	}

	public void setDataSourceDao(RunnerDataSourceDao dataSourceDao) {
		this.dataSourceDao = dataSourceDao;
	}



	public List<RunnerDataSource> getRunnerDataSources() {
		return runnerDataSources;
	}

	public void setRunnerDataSources(List<RunnerDataSource> runnerDataSources) {
		this.runnerDataSources = runnerDataSources;
	}

	

	public RunnerDashboardAlert getDashboardAlert() {
		return dashboardAlert;
	}

	public void setDashboardAlert(RunnerDashboardAlert dashboardAlert) {
		this.dashboardAlert = dashboardAlert;
	}
	public List<XAxisStep> getXAxisSteps() {
		return Arrays.asList(XAxisStep.values());
	}
	
}
