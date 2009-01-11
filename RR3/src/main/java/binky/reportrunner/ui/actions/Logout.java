package binky.reportrunner.ui.actions;

import binky.reportrunner.ui.Statics;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

import com.opensymphony.xwork2.ActionContext;

public class Logout extends StandardRunnerAction {

	private static final long serialVersionUID = 0l;

	public String execute() throws Exception {	
	    ActionContext.getContext().getSession().put(Statics.USER_HANDLE, null);
		return "login";
	}
}
