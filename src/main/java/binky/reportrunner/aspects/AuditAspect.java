package binky.reportrunner.aspects;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import binky.reportrunner.service.AuditService;

@Aspect
public class AuditAspect {

	private AuditService auditService;
	
	private static final Logger logger = Logger.getLogger(AuditAspect.class);
	
	//@Around("execution(* binky.reportrunner.service.Auditable.*(..))")
	//@Around("execution(* binky.reportrunner.service.*.*(..))")
	@Around("this(binky.reportrunner.service.Auditable)")
	public Object logExecution(ProceedingJoinPoint pjp) throws Throwable{
		
		long start = Calendar.getInstance().getTimeInMillis();
		String module=pjp.getSignature().getDeclaringType().getName();
		Object[] args = pjp.getArgs();
		String method = pjp.getSignature().getName();
		StringBuilder arguments = new StringBuilder();
		boolean first=true;
		for (Object o : args) {
			if (!first) {arguments.append(",");}
			else { first =false; }
			arguments.append('{');
			arguments.append(o);
			arguments.append('}');
		}
		//bit of a hack to prevent exposing password
		if (module.equals(" binky.reportrunner.service.UserService") && method.equals("changePassword")) {
			arguments=new StringBuilder("blocked");
		}
		
		boolean success=false;
		String errorText="No Error";
		try {
			Object ret=	pjp.proceed();
			success=true;
			return ret;
		}
		catch (Throwable t) {
			errorText=t.getMessage();
			throw t;
		} finally {
			long runTime = Calendar.getInstance().getTimeInMillis()-start;
			if (logger.isTraceEnabled()) logger.trace("logging a message for:" + module);
			
			auditService.logAuditEvent(module, success, runTime, arguments.toString(), method,errorText);
		}
	}
	
	public void setAuditService(AuditService auditService) {
		this.auditService = auditService;
	}
	
}
