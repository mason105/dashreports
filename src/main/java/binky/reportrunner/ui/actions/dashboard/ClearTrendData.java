package binky.reportrunner.ui.actions.dashboard;

import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class ClearTrendData extends StandardRunnerAction {

	private DashboardService dashboardService;
	private static final long serialVersionUID = -7436981202092579819L;
	private int itemId;


	@Override
	public String execute() throws Exception {

		groupName = dashboardService.getItem(itemId).getGroup()
				.getGroupName();
		if (super.getSessionUser().getGroups().contains(groupName)
				|| super.getSessionUser().getIsAdmin()) {
			dashboardService.clearTrendData(itemId);
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

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}



}
