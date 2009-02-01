package binky.reportrunner.ui.actions.base;

import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.ui.Statics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public abstract class StandardRunnerAction extends ActionSupport {

	private static final long serialVersionUID = -5701712982967708713L;

	public abstract String execute() throws Exception;

	public final RunnerUser getSessionUser() {

		RunnerUser user = (RunnerUser) ActionContext.getContext().getSession()
				.get(Statics.USER_HANDLE);
		return user;
	}

	protected final boolean doesUserHaveGroup(String groupName) {

		if (getSessionUser().getIsAdmin()) {
			return true;
		} else {

			for (RunnerGroup g : getSessionUser().getGroups()) {
				if (g.getGroupName().equals(groupName))
					return true;
			}

			return false;
		}
	}

}
