package binky.reportrunner.ui.actions.dashboard;

import binky.reportrunner.data.RunnerDashboardAlert;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class SaveAlert  extends AdminRunnerAction {

	private static final long serialVersionUID = 1L;
	private DashboardService dashboardService;
	private RunnerDashboardAlert alert;
	@Override
	public String execute() throws Exception {

		dashboardService.saveUpdateAlert(alert);
		
		return SUCCESS;
	}
	public DashboardService getDashboardService() {
		return dashboardService;
	}
	public void setDashboardService(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}
	public void setAlert(RunnerDashboardAlert alert) {
		this.alert = alert;
	}
	
}
