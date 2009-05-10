package binky.reportrunner.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.ui.Statics;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class UserSessionIntercept implements Interceptor, StrutsStatics {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UserSessionIntercept.class);
	public void destroy() {

	}

	public void init() {

	}

	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext context = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) context
				.get(HTTP_REQUEST);
		HttpSession session = request.getSession(true);
		RunnerUser user = (RunnerUser) session.getAttribute(Statics.USER_HANDLE);
		// if this is an admin function action then check all is well
		logger.debug("class name is: " + invocation.getAction().getClass().getName());
		logger.debug("(invocation.getAction() instanceof AdminRunnerAction)" + (invocation.getAction() instanceof AdminRunnerAction));
		if ((invocation.getAction() instanceof AdminRunnerAction) && !user.getIsAdmin() ) {
			logger.warn("access denied to " + invocation.getAction().getClass() + " for user: " + user.getUserName()) ;
			return "securityError";			
		} else {
			return invocation.invoke();
		}
		
	}
}
