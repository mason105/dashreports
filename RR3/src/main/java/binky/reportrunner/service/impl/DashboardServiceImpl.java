package binky.reportrunner.service.impl;

import java.io.IOException;
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
		return dashboardDao.getAlertsForGroup(groupName);
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


}
