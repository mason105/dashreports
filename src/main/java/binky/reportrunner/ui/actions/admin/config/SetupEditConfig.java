package binky.reportrunner.ui.actions.admin.config;

import java.util.Collection;

import org.springframework.security.access.prepost.PreAuthorize;

import binky.reportrunner.data.Configuration;
import binky.reportrunner.service.ConfigurationService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class SetupEditConfig extends StandardRunnerAction {

	private static final long serialVersionUID = 2776295299243323538L;
	private Collection<Configuration> configurations;
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String execute() throws Exception {
		super.clearActionErrors();
		super.clearMessages();
		super.clearErrors();
		this.configurations=configurationService.getConfigurationItems();
		
		return SUCCESS;
	}
	private ConfigurationService configurationService;
	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}
	public Collection<Configuration> getConfigurations() {
		return configurations;
	}
	
	
}
