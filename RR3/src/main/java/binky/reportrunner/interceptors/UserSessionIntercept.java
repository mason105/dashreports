package binky.reportrunner.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.StrutsStatics;

import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class UserSessionIntercept implements Interceptor,StrutsStatics {

	private static final long serialVersionUID = 1L;
	private static final String USER_HANDLE="userHandle";
	public void destroy() {
		
	}

	public void init() {

	}

	public String intercept(ActionInvocation invocation) throws Exception {
		StandardRunnerAction action = (StandardRunnerAction)invocation.getAction();
		
	    final ActionContext context = invocation.getInvocationContext ();
	    HttpServletRequest request = (HttpServletRequest) context.get(HTTP_REQUEST);
	    HttpSession session =  request.getSession (true);
		RunnerUser user=(RunnerUser)session.getAttribute(USER_HANDLE);
		action.setUser(user);
		return invocation.invoke();
	}



}