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

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerDashboardItem;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.scheduler.Scheduler;
import binky.reportrunner.scheduler.SchedulerException;
import binky.reportrunner.service.DashboardService;

public class DashboardServiceImpl implements DashboardService {
	
	private static final Logger logger = Logger.getLogger(DashboardServiceImpl.class);
	
	private ReportRunnerDao<RunnerDashboardItem,Integer> dashboardDao;
	private ReportRunnerDao<RunnerGroup,String> groupDao;
	private Scheduler scheduler;

	public RunnerDashboardItem getItem(Integer id) {
		return dashboardDao.get(id);
	}
	public void deleteItem(Integer id) throws SchedulerException {
		dashboardDao.delete(id);
		scheduler.removedDashboardAlert(id);		
	}

	public List<RunnerDashboardItem> getItemsForGroup(String groupName) {
		List<RunnerDashboardItem> as = dashboardDao.findByNamedQuery("getItemsByGroup", new String[]{groupName});			
		List<RunnerDashboardItem> alerts=new LinkedList<RunnerDashboardItem>();
		
		//temp hack
		for (RunnerDashboardItem a: as) 
		{
			long visualRefreshTime=60000;

			try {
				Date last = scheduler.getPreviousRunTime(a.getItemId());
				Date next = scheduler.getNextRunTime(a.getItemId());
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

	public List<RunnerDashboardItem> getAllItems() {
		return dashboardDao.getAll();
	}

	public void saveUpdateItem(RunnerDashboardItem alert) throws SchedulerException {
		logger.debug("alert is null=" + (alert==null));
		if (alert.getItemId()!=null){
			scheduler.removedDashboardAlert(alert.getItemId());
		}
		
		//hack to try to fix a batch update error		
		RunnerGroup group = groupDao.get(alert.getGroup().getGroupName());
		alert.setGroup(group);
		
		dashboardDao.saveOrUpdate(alert);
		scheduler.addDashboardAlert(alert.getItemId(),alert.getCronTab());		
	}
	public void setDashboardDao(ReportRunnerDao<RunnerDashboardItem,Integer> dashboardDao) {
		this.dashboardDao = dashboardDao;
	}
	public Scheduler getScheduler() {
		return scheduler;
	}
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	public List<RunnerDashboardItem> getRunningItems() {
		List<String> runningJobs = scheduler.getCurrentRunningJobs();
		List<RunnerDashboardItem> alerts = new LinkedList<RunnerDashboardItem>();
		for (String string : runningJobs) {
			String groupName = string.split(":|:")[0];
			if (groupName.equals(Scheduler.dashboardSchedulerGroup)) {
				logger.debug("job name: " + string);
				Integer id = Integer.parseInt(string.split(":|:")[2]);
				RunnerDashboardItem alert = dashboardDao.get(id);
				alerts.add(alert);
			}			
		}
		return alerts;
	}
	
	public void interruptRunningDashboardItem(Integer alertId)
	throws SchedulerException {
		logger.debug("interrupt alert: " + alertId);		
		scheduler.interruptRunningDashboardAlert(alertId);		
	}
	
	public void invokeDashboardItem(Integer itemId) throws SchedulerException {
		scheduler.invokeDashboardItem(itemId);
		
	}

	public void setGroupDao(ReportRunnerDao<RunnerGroup,String> groupDao) {
		this.groupDao = groupDao;
	}



}
