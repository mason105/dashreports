package binky.reportrunner.ui.actions.base;

import java.util.Map;

import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.ui.Statics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public abstract class StandardRunnerAction extends ActionSupport {

	private static final long serialVersionUID = -5701712982967708713L;
	private static final Logger logger = Logger.getLogger(StandardRunnerAction.class);
	public abstract String execute() throws Exception;

	public final RunnerUser getSessionUser() {

		RunnerUser user = (RunnerUser) ActionContext.getContext().getSession()
				.get(Statics.USER_HANDLE);
		return user;
	}
	
	public final String getActionName() {
		return this.getClass().getName();
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
	
	protected final boolean isUserReadOnly() {
		if (getSessionUser().getIsAdmin()) {
			return true;
		} else {
			return getSessionUser().getIsReadOnly();
		}	
	}

	protected void listAllVars(String className) {
		Map<String, Object> params = ActionContext.getContext().getContextMap();
		logger.debug("dumping context map for action class: " + className);
		for (String key : params.keySet()) {			 
			logger.debug(key + " - " + params.get(key));
		}
	
		
	}
}
