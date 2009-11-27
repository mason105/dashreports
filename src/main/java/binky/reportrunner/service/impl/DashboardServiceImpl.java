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
 * Module: DashboardServiceImpl.java
 ******************************************************************************/
package binky.reportrunner.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.RunnerDashboardAlertDao;
import binky.reportrunner.data.RunnerDashboardAlert;
import binky.reportrunner.engine.renderers.ChartRenderer;
import binky.reportrunner.scheduler.Scheduler;
import binky.reportrunner.scheduler.SchedulerException;
import binky.reportrunner.service.DashboardService;

public class DashboardServiceImpl implements DashboardService {
	
	private static final Logger logger = Logger.getLogger(DashboardServiceImpl.class);
	
	private RunnerDashboardAlertDao dashboardDao;
	private Scheduler scheduler;
	public RunnerDashboardAlert getAlert(Integer id) {
		return dashboardDao.getAlert(id);
	}
	public void deleteAlert(Integer id) throws SchedulerException {
		dashboardDao.deleteAlert(id);
		scheduler.removedDashboardAlert(id);		
	}

	public List<RunnerDashboardAlert> getAlertsForGroup(String groupName) {
		List<RunnerDashboardAlert> as = dashboardDao.getAlertsForGroup(groupName);			
		List<RunnerDashboardAlert> alerts=new LinkedList<RunnerDashboardAlert>();
		
		//temp hack
		for (RunnerDashboardAlert a: as) 
		{
			long visualRefreshTime=60000;

			try {
				Date last = scheduler.getPreviousRunTime(a.getId());
				Date next = scheduler.getNextRunTime(a.getId());
				if ((last!=null) && (next!=null) && (last.getTime()<next.getTime())) {
					visualRefreshTime=(next.getTime()-last.getTime());
				}				
			} catch (SchedulerException e) {
				logger.error(e.getMessage(),e);
			}		
			
			a.setVisualRefreshTime(visualRefreshTime);
			alerts.add(a);
			
		}
		return alerts;
		
		
	}

	public List<RunnerDashboardAlert> getAllAlerts() {
		return dashboardDao.getAllAlerts();
	}

	public void saveUpdateAlert(RunnerDashboardAlert alert) throws SchedulerException {
		logger.debug("alert is null=" + (alert==null));
		if (alert.getId()!=null){
			scheduler.removedDashboardAlert(alert.getId());
		}
		dashboardDao.saveUpdateAlert(alert);
		scheduler.addDashboardAlert(alert.getId(),alert.getCronTab());		
	}
	public RunnerDashboardAlertDao getDashboardDao() {
		return dashboardDao;
	}
	public void setDashboardDao(RunnerDashboardAlertDao dashboardDao) {
		this.dashboardDao = dashboardDao;
	}
	public Scheduler getScheduler() {
		return scheduler;
	}
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	public String getChartForAlert(Integer id) throws NumberFormatException, IOException {
		
		RunnerDashboardAlert alert = dashboardDao.getAlert(id);
		
		ChartRenderer render = new ChartRenderer();
		
		String uid = render.renderChart(alert.getAlertName(), alert.getCurrentDataset(), 
				alert.getXaxisColumn(), alert.getChartType());
		
		return uid;
	}
	public List<RunnerDashboardAlert> getRunningAlerts() {
		List<String> runningJobs = scheduler.getCurrentRunningJobs();
		List<RunnerDashboardAlert> alerts = new LinkedList<RunnerDashboardAlert>();
		for (String string : runningJobs) {
			String groupName = string.split(":|:")[0];
			if (groupName.equals(Scheduler.dashboardSchedulerGroup)) {
				logger.debug("job name: " + string);
				Integer id = Integer.parseInt(string.split(":|:")[2]);
				RunnerDashboardAlert alert = dashboardDao.getAlert(id);
				alerts.add(alert);
			}			
		}
		return alerts;
	}
	
	public void interruptRunningDashboardAlert(Integer alertId)
	throws SchedulerException {
		logger.debug("interrupt alert: " + alertId);		
		scheduler.interruptRunningDashboardAlert(alertId);		
	}
	@Override
	public void invokeDashboardAlert(Integer alertId) throws SchedulerException {
		scheduler.invokeDashboardAler(alertId);
		
	}


}
