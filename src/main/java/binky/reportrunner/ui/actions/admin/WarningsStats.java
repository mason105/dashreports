package binky.reportrunner.ui.actions.admin;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import binky.reportrunner.service.AuditService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class WarningsStats extends StandardRunnerAction {

	private AuditService auditService;
	private static final long serialVersionUID = -7476318388046063816L;
	private Date fromDate;
	private Date toDate;
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String execute() throws Exception {
		
		Calendar c = Calendar.getInstance();
		toDate=c.getTime();
		c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, -1);
		fromDate=c.getTime();		
		return SUCCESS;
	}

	public final List<String> getModules() {
		return auditService.getModuleNames();
	}

	public void setAuditService(AuditService auditService) {
		this.auditService = auditService;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public Date getToDate() {
		return toDate;
	}


	
}
