package binky.reportrunner.ui.actions.dashboard.base;

import binky.reportrunner.service.DashboardService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public abstract class BaseDashboardAction extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;

	private DashboardService dashboardService;
	public final DashboardService getDashboardService() {
		return dashboardService;
	}

	public final void setDashboardService(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}
}
