package binky.reportrunner.ui.actions.dashboard;

import binky.reportrunner.data.RunnerDashboardAlert;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class PopUpChartAction extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;

	private Integer alertId;
	private String alertName;
	private DashboardService dashboardService;
	
	@Override
	public String execute() throws Exception {
		
		RunnerDashboardAlert alert= dashboardService.getAlert(alertId);
		this.alertName=alert.getAlertName();
		if (!doesUserHaveGroup(alert.getGroup().getGroupName())) {
			return ERROR;
		}
				
		return SUCCESS;
	}

	public Integer getAlertId() {
		return alertId;
	}

	public void setAlertId(Integer alertId) {
		this.alertId = alertId;
	}

	public String getAlertName() {
		return alertName;
	}

	public void setAlertName(String alertName) {
		this.alertName = alertName;
	}

	public DashboardService getDashboardService() {
		return dashboardService;
	}

	public void setDashboardService(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}

	
	
}
