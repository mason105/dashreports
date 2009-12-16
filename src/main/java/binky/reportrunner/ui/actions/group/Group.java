package binky.reportrunner.ui.actions.group;

import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class Group extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;
	
	private String groupName;
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
