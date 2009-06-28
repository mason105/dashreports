package binky.reportrunner.ui.actions.dashboard.popup;

import java.util.LinkedList;
import java.util.List;

import binky.reportrunner.data.RunnerDashboardAlert;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class PopUpDashboardAction extends StandardRunnerAction {

	private String groupName;
	private DashboardService dashboardService;
	private static final long serialVersionUID = 0L;
	private List<RunnerDashboardAlert> alerts;
	private Integer currentRow;

	public String execute() throws Exception {

		alerts = new LinkedList<RunnerDashboardAlert>();

		if (!super.getSessionUser().getIsAdmin()
				&& !super.doesUserHaveGroup(groupName)) {
			return ERROR;
		} else {
			alerts = dashboardService.getAlertsForGroup(this.groupName);

			return SUCCESS;
		}
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	public void setAlerts(List<RunnerDashboardAlert> alerts) {
		this.alerts = alerts;
	}

	public Integer getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(Integer currentRow) {
		this.currentRow = currentRow;
	}

}
