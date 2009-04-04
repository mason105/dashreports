package binky.reportrunner.dao;

import java.util.List;

import binky.reportrunner.data.DashboardAlertData;
import binky.reportrunner.data.RunnerDashboardAlert;

public interface RunnerDashboardAlertDao {

	public void saveUpdateAlert();
	public void deleteAlert(Integer id);
	public RunnerDashboardAlert getAlert(Integer id);
	public List<RunnerDashboardAlert> getAllAlerts();
	public List<RunnerDashboardAlert> getAlertsForGroup(String groupName);
	public void saveAlertData(DashboardAlertData data);
	
}
