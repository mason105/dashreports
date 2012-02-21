package binky.reportrunner.ui.actions.admin.encryption;

import org.springframework.security.access.prepost.PreAuthorize;

import binky.reportrunner.ui.actions.base.StandardRunnerAction;
import binky.reportrunner.util.EncryptionUtil;

public class GenerateEncryptionKey extends StandardRunnerAction {


	private static final long serialVersionUID = 5978078192826994716L;
	private String encryptionKey;
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String execute() throws Exception {


		
		EncryptionUtil enc = new EncryptionUtil();
		this.encryptionKey=enc.generateKey();

		return SUCCESS;
	}
	public String getEncryptionKey() {
		return encryptionKey;
	}

}
