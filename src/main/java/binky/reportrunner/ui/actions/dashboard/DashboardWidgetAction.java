/**
 * 
 */
package binky.reportrunner.ui.actions.dashboard;

import binky.reportrunner.data.RunnerDashboardAlert;
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
	private RunnerDashboardAlert alert;
	
	@Override
	public String execute() throws Exception {
		
		RunnerDashboardAlert alert = dashboardService.getAlert(alertId);		
		// noddy security check
		if (!doesUserHaveGroup(alert.getGroup().getGroupName())) {
			return ERROR;
		}
		
		this.alert=alert;
	
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


	public RunnerDashboardAlert getAlert() {
		return alert;
	}


	public void setAlert(RunnerDashboardAlert alert) {
		this.alert = alert;
	}


}
