package binky.reportrunner.dao;

import java.util.Date;
import java.util.List;

import binky.reportrunner.data.DashboardAlertData;
import binky.reportrunner.data.RunnerDashboardAlert;

public interface RunnerDashboardAlertDao {

	public void saveUpdateAlert(RunnerDashboardAlert alert);
	public void deleteAlert(Integer id);
	public RunnerDashboardAlert getAlert(Integer id);
	public List<RunnerDashboardAlert> getAllAlerts();
	public List<RunnerDashboardAlert> getAlertsForGroup(String groupName);
	public void saveAlertData(DashboardAlertData data);
	public DashboardAlertData getLatestAlertData(Integer alertId);
	public List<DashboardAlertData> getAlertDataForRange(Integer alertId, Date startDateTime, Date endDateTime);
}
