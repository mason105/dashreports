package binky.reportrunner.ui.actions.admin;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import binky.reportrunner.service.AuditService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class WarningsStats extends StandardRunnerAction {

	private AuditService auditService;
	private static final long serialVersionUID = -7476318388046063816L;

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String execute() throws Exception {
		return SUCCESS;
	}

	public final List<String> getModules() {
		return auditService.getModuleNames();
	}

	public void setAuditService(AuditService auditService) {
		this.auditService = auditService;
	}
	
	
}
