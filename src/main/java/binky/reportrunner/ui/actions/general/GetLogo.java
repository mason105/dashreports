package binky.reportrunner.ui.actions.general;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import binky.reportrunner.data.Configuration.ConfigurationType;
import binky.reportrunner.service.ConfigurationService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class GetLogo extends StandardRunnerAction {


	private static final long serialVersionUID = 1L;


	private InputStream inputStream;
	@Override
	public String execute() throws Exception {
		byte[] imageData = configurationService.getConfigurationItem(ConfigurationType.LOGO).getBinaryValue();
		if (imageData!=null) {
			this.inputStream=new ByteArrayInputStream(imageData);
		}
		
		return SUCCESS;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	private ConfigurationService configurationService;
	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	
	
}
