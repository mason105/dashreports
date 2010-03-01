package binky.reportrunner.ui.actions.group;

import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class Group extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;
	private String activeTab;

	@Override
	public String execute() throws Exception {

		return SUCCESS;
	}


	public String getActiveTab() {
		if (!super.isStringPopulated(activeTab)) {
			return "dashboard";
		} else {
			return activeTab;
		}
	}

	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}

}
