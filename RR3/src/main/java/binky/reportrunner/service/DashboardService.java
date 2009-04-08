package binky.reportrunner.service;

import java.util.Date;
import java.util.List;

import binky.reportrunner.data.DashboardAlertData;
import binky.reportrunner.data.RunnerDashboardAlert;
import binky.reportrunner.scheduler.SchedulerException;

public interface DashboardService {

	public List<RunnerDashboardAlert> getAlertsForGroup(String groupName);
	public List<RunnerDashboardAlert> getAllAlerts();
	public void saveUpdateAlert(RunnerDashboardAlert alert) throws SchedulerException;
	public void deleteAlert(Integer id) throws SchedulerException;
	public RunnerDashboardAlert getAlert(Integer id);
	public DashboardAlertData getLatestAlertData(Integer alertId);
	public List<DashboardAlertData> getAlertDataForRange(Integer alertId, Date startDateTime, Date endDateTime);
	public List<DashboardAlertData> getLatestAlertData(Integer alertId, int count);

}
