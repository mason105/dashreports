package binky.reportrunner.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.RunnerDashboardAlertDao;
import binky.reportrunner.data.RunnerDashboardAlert;
import binky.reportrunner.scheduler.Scheduler;
import binky.reportrunner.service.DashboardService;

public class DashboardServiceImpl implements DashboardService {
	
	private static final Logger logger = Logger.getLogger(DashboardServiceImpl.class);
	
	private RunnerDashboardAlertDao dashboardDao;
	private Scheduler scheduler;
	public RunnerDashboardAlert getAlert(Integer id) {
		return null;
	}
	public void deleteAlert(RunnerDashboardAlert alert) {
		// TODO Auto-generated method stub
		
	}

	public List<RunnerDashboardAlert> getAlertsForGroup(String groupName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<RunnerDashboardAlert> getAllAlerts() {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveUpdateAlert(RunnerDashboardAlert alert) {
		// TODO Auto-generated method stub
		
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

}
