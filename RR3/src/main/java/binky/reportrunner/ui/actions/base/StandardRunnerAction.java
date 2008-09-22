package binky.reportrunner.ui.actions.base;

import binky.reportrunner.data.RunnerUser;

import com.opensymphony.xwork2.ActionSupport;

public abstract class StandardRunnerAction extends ActionSupport {

	private static final long serialVersionUID = -5701712982967708713L;

	private RunnerUser user;
	
	public abstract String execute() throws Exception;

	public final RunnerUser getUser() {
		return user;
	}

	public final void setUser(RunnerUser user) {
		this.user = user;
	}
	
	
}
