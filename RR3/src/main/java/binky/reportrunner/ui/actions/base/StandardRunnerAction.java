package binky.reportrunner.ui.actions.base;

import binky.reportrunner.data.RunnerUser;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public abstract class StandardRunnerAction extends ActionSupport {

	private static final long serialVersionUID = -5701712982967708713L;
	private static final String USER_HANDLE="userHandle";
	
	public abstract String execute() throws Exception;

	public final RunnerUser getUser() {
	    
		RunnerUser user = (RunnerUser) ActionContext.getContext().getSession().get(USER_HANDLE);
		return user;
	}

	
	
}
