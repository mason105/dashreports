package binky.reportrunner.ui.actions.dashboard;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerDashboardItem;
import binky.reportrunner.ui.actions.dashboard.base.BaseDashboardAction;

public final class DashboardAction extends BaseDashboardAction {

	private String groupName;
	private static final long serialVersionUID = 0L;
	private List<RunnerDashboardItem> items;
	private Integer currentRow;

	private Logger logger = Logger.getLogger(DashboardAction.class);
	
	public String execute() throws Exception {

		items = new LinkedList<RunnerDashboardItem>();

		if (!super.getSessionUser().getIsAdmin()
				&& !super.doesUserHaveGroup(groupName)) {
			return ERROR;
		} else {
			items = super.getDashboardService().getItemsForGroup(this.groupName);
		
			if (logger.isDebugEnabled()) {
				
				if (items != null ) {
					logger.debug("items size is: " + items.size() + " for group: " + groupName);
				} else {
					logger.debug("items are null for group: " + groupName);
				}
				
			}
		
			return SUCCESS;
		}
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
