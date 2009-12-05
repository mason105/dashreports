/**
 * 
 */
package binky.reportrunner.ui.actions.dashboard;

import binky.reportrunner.data.RunnerDashboardItem;
import binky.reportrunner.ui.actions.dashboard.base.BaseDashboardAction;

/**
 * @author Daniel Grout
 *
 */
public class DashboardWidgetAction extends BaseDashboardAction {

	/* (non-Javadoc)
	 * @see binky.reportrunner.ui.actions.base.StandardRunnerAction#execute()
	 */
	private static final long serialVersionUID = 0L;

	

	private Integer itemId;
	private RunnerDashboardItem item;
	
	@Override
	public String execute() throws Exception {
		
		RunnerDashboardItem item = super.getDashboardService().getItem(itemId);		
		// noddy security check
		if (!doesUserHaveGroup(item.getGroup().getGroupName())) {
			return ERROR;
		}
		
		this.item=item;
	
		return SUCCESS;
	}


	public Integer getItemId() {
		return itemId;
	}


	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}


	public RunnerDashboardItem getItem() {
		return item;
	}


	public void setItem(RunnerDashboardItem item) {
		this.item = item;
	}





}
