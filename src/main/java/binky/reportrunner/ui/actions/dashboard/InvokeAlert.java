package binky.reportrunner.ui.actions.dashboard;

import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class InvokeAlert extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;
	private DashboardService dashboardService;
	private Integer alertId;
	private String groupName;
	@Override
	public String execute() throws Exception {
	
		if (super.getSessionUser().getGroups().contains(groupName)
				|| super.getSessionUser().getIsAdmin()) {
			dashboardService.invokeDashboardAlert(alertId);
			return SUCCESS;
		} else {
				SecurityException se = new SecurityException("Group " + groupName
					+ " not valid for user "
					+ super.getSessionUser().getUserName());
			throw se;
		}
	}
	public DashboardService getDashboardService() {
		return dashboardService;
	}
	public void setDashboardService(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}
	public Integer getAlertId() {
		return alertId;
	}
	public void setAlertId(Integer alertId) {
		this.alertId = alertId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
