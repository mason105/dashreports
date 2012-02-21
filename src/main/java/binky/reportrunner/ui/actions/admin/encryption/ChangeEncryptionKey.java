package binky.reportrunner.ui.actions.admin.encryption;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;

import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class ChangeEncryptionKey extends StandardRunnerAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3111915388412898947L;

	private DatasourceService datasourceService;
	private String newKey;
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String execute() throws Exception {
		if (StringUtils.isEmpty(newKey)) {
			super.addActionError("Please generate a key");
			return INPUT;
		} else {
			datasourceService.reEncryptPasswords(newKey);		
			return SUCCESS;
		}
	}

	public void setDatasourceService(DatasourceService datasourceService) {
		this.datasourceService = datasourceService;
	}

	public void setNewKey(String newKey) {
		this.newKey = newKey;
	}

	public String getNewKey() {
		return newKey;
	}

	
	
}
