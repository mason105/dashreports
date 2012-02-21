package binky.reportrunner.ui.actions.admin.encryption;

import org.springframework.security.access.prepost.PreAuthorize;

import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class SetupChangeEncryptionKey extends StandardRunnerAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6784610934086578448L;

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String execute() throws Exception {
		return SUCCESS;
	}

}
