package binky.reportrunner.ui.actions.dashboard;

import java.util.List;

import binky.reportrunner.data.RunnerDashboardAlert;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class ListAlerts  extends AdminRunnerAction {

	private static final long serialVersionUID = 1L;
	private DashboardService dashboardService;
	private List<RunnerDashboardAlert> alerts;
	@Override
	public String execute() throws Exception {
		alerts = dashboardService.getAllAlerts();
		return SUCCESS;
	}
	public DashboardService getDashboardService() {
		return dashboardService;
	}
	public void setDashboardService(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}
	public List<RunnerDashboardAlert> getAlerts() {
		return alerts;
	}
	
}
