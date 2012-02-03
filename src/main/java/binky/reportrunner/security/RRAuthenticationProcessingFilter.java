package binky.reportrunner.security;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.data.RunnerHistoryEvent.Module;
import binky.reportrunner.service.AuditService;
import binky.reportrunner.service.UserService;
import binky.reportrunner.ui.Statics;



public class RRAuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter  {

	private static Logger logger = Logger.getLogger(RRAuthenticationProcessingFilter.class);
	private UserService userService;
	private AuditService auditService;
	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain,Authentication authResult)
			throws IOException, ServletException {		
		super.successfulAuthentication(request, response, chain, authResult);
		logger.info("logged in: " + authResult.getName());
		RunnerUser userObject = userService.getUser(authResult.getName());
		logger.debug("enumerating group memberships");
		List<RunnerGroup> groups = new LinkedList<RunnerGroup>();
		for (RunnerGroup g: userService.getGroupsForUser(userObject.getUsername())) {
			groups.add(g);
		}
		logger.debug("storing user in session");
		request.getSession().setAttribute(Statics.USER_HANDLE, userObject);
		logger.debug("storing groups in session");
		request.getSession().setAttribute(Statics.GROUPS_HANDLE, groups);
		auditService.logAuditEvent(Module.SECURITY, "Login", userObject.getUsername(), true, 0, "", "");
	}
	

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {		
		super.unsuccessfulAuthentication(request, response, failed);
		logger.warn("login failed: " + failed.getMessage(),failed);
		auditService.logAuditEvent(Module.SECURITY, "Login Failed: " + failed.getMessage(), "", false, 0, "", "");
	}


	public void setAuditService(AuditService auditService) {
		this.auditService = auditService;
	}
	
	

}
