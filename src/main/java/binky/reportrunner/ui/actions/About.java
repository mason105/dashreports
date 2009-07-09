package binky.reportrunner.ui.actions;

import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class About extends StandardRunnerAction {

	private String versionId;
	
	private static final long serialVersionUID = -2445708209232186033L;

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

}
