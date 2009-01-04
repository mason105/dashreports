package binky.reportrunner.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsStatics;

import binky.reportrunner.service.AuthenticationService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class AuthIntercept implements Interceptor,StrutsStatics {

	private static final long serialVersionUID = -6151350585810759841L;
	private static final String USERNAME="userName";
	private static final String PASSWORD="password";
	private static final String USER_HANDLE="user";
	private static final String LOGIN_ATTEMPT="loginAttempt";
	
	private AuthenticationService authService;
	

	/**
	*http://www.vitarara.org/cms/struts_2_cookbook/creating_a_login_interceptor
	**/
	public String intercept (ActionInvocation invocation) throws Exception {
	    // Get the action context from the invocation so we can access the
	    // HttpServletRequest and HttpSession objects.
	    final ActionContext context = invocation.getInvocationContext ();
	    HttpServletRequest request = (HttpServletRequest) context.get(HTTP_REQUEST);
	    HttpSession session =  request.getSession (true);

	    // Is there a "user" object stored in the user's HttpSession?
	    Object user = session.getAttribute (USER_HANDLE);
	    if (user == null) {
	        // The user has not logged in yet.

	        // Is the user attempting to log in right now?
	        String loginAttempt = request.getParameter (LOGIN_ATTEMPT);
	        if (! StringUtils.isBlank (loginAttempt) ) { // The user is attempting to log in.

	            // Process the user's login attempt.
	            if (processLoginAttempt (request, session) ) {
	                // The login succeeded send them the login-success page.
	                return "login-success";
	            } else {
	                // The login failed. Set an error if we can on the action.
	                Object action = invocation.getAction ();
	                if (action instanceof ValidationAware) {
	                    ((ValidationAware) action).addActionError ("Username or password incorrect.");
	                }
	            }
	        }

	        // Either the login attempt failed or the user hasn't tried to login yet, 
	        // and we need to send the login form.
	        return "login";
	    } else {
	        return invocation.invoke ();
	    }
	}

	/**
	 * Attempt to process the user's login attempt delegating the work to the 
	 * SecurityManager.
	 */
	public boolean processLoginAttempt (HttpServletRequest request, HttpSession session) {
	    // Get the username and password submitted by the user from the HttpRequest.
	    String username = request.getParameter (USERNAME);
	    String password = request.getParameter (PASSWORD);

	    // Use the security manager to validate the user's username and password.
	    Object user = this.authService.authUser(username, password);

	    if (user != null) {
	        // The user has successfully logged in. Store their user object in 
	        // their HttpSession. Then return true.
	        session.setAttribute (USER_HANDLE, user);
	        return true;
	    } else {
	        // The user did not successfully log in. Return false.
	        return false;
	    }
	}



	public AuthenticationService getAuthService() {
		return authService;
	}

	public void setAuthService(AuthenticationService authService) {
		this.authService = authService;
	}

	public void destroy() {

	}

	public void init() {

	}
	
}
