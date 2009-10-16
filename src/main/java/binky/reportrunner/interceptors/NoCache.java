package binky.reportrunner.interceptors;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class NoCache extends AbstractInterceptor {

	/**
	 * Hack to stop certain pages caching with certain browsers that I don't like very much at the moment 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
	
		ActionContext context = invocation.getInvocationContext();
		HttpServletResponse response = (HttpServletResponse)context.get(StrutsStatics.HTTP_RESPONSE);
		
		if (response != null) {
		
			response.setHeader("Cache-control",  "no-cache, no-store");
			response.setHeader("Pragma" ,"no-cache");
			response.setHeader("Expires", "-1");
			
		}
		
		return invocation.invoke();
	}

}
