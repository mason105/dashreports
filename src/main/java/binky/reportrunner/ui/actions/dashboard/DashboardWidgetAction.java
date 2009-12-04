/**
 * 
 */
package binky.reportrunner.ui.actions.dashboard;

import binky.reportrunner.data.RunnerDashboardItem;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

/**
 * @author Daniel Grout
 *
 */
public class DashboardWidgetAction extends StandardRunnerAction {

	/* (non-Javadoc)
	 * @see binky.reportrunner.ui.actions.base.StandardRunnerAction#execute()
	 */
	private static final long serialVersionUID = 0L;

	private DashboardService dashboardService;

	private Integer alertId;
	private RunnerDashboardItem item;
	
	@Override
	public String execute() throws Exception {
		
		RunnerDashboardItem item = dashboardService.getItem(alertId);		
		// noddy security check
		if (!doesUserHaveGroup(item.getGroup().getGroupName())) {
			return ERROR;
		}
		
		this.item=item;
	
		return SUCCESS;
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


	public RunnerDashboardItem getItem() {
		return item;
	}


	public void setItem(RunnerDashboardItem item) {
		this.item = item;
	}





}
