package binky.reportrunner.ui.actions.dashboard;

import java.util.LinkedList;
import java.util.List;

import binky.reportrunner.data.RunnerDashboardItem;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class DashboardAction extends StandardRunnerAction {

	private String groupName;
	private DashboardService dashboardService;
	private static final long serialVersionUID = 0L;
	private List<RunnerDashboardItem> items;
	private Integer currentRow;

	public String execute() throws Exception {

		items = new LinkedList<RunnerDashboardItem>();

		if (!super.getSessionUser().getIsAdmin()
				&& !super.doesUserHaveGroup(groupName)) {
			return ERROR;
		} else {
			items = dashboardService.getItemsForGroup(this.groupName);

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

	public List<RunnerDashboardItem> getItems() {
		return items;
	}

	public void setItems(List<RunnerDashboardItem> items) {
		this.items = items;
	}

	public Integer getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(Integer currentRow) {
		this.currentRow = currentRow;
	}

}
