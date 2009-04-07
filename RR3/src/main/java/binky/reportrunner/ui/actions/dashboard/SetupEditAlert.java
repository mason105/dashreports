package binky.reportrunner.ui.actions.dashboard;

import binky.reportrunner.data.RunnerDashboardAlert;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class SetupEditAlert  extends AdminRunnerAction {

	private static final long serialVersionUID = 1L;
	private DashboardService dashboardService;
	private RunnerDashboardAlert alert;
	private Integer id;
	@Override
	public String execute() throws Exception {
		alert=dashboardService.getAlert(id);
		return SUCCESS;
	}
	public DashboardService getDashboardService() {
		return dashboardService;
	}
	public void setDashboardService(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}
	public RunnerDashboardAlert getAlert() {
		return alert;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
