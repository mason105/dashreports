package binky.reportrunner.ui.actions.dashboard;

import binky.reportrunner.service.DashboardService;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class DeleteAlert extends AdminRunnerAction {

	private static final long serialVersionUID = 1L;
	private DashboardService dashboardService;
	private Integer id;
	@Override
	public String execute() throws Exception {
		dashboardService.deleteAlert(id);
		return SUCCESS;
	}
	public DashboardService getDashboardService() {
		return dashboardService;
	}
	public void setDashboardService(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
