package binky.reportrunner.service;

import java.util.List;

import binky.reportrunner.data.RunnerDashboardAlert;

public interface DashboardService {

	public List<RunnerDashboardAlert> getAlertsForGroup(String groupName);
	public List<RunnerDashboardAlert> getAllAlerts();
	public void saveUpdateAlert(RunnerDashboardAlert alert);
	public void deleteAlert(Integer id);
	public RunnerDashboardAlert getAlert(Integer id);
}
