package binky.reportrunner.ui.actions.datasource;

import org.springframework.security.access.prepost.PreAuthorize;

import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class PurgeConnections extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;
	private DatasourceService dataSourceService;
	private String dataSourceName;
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String execute() throws Exception {
	
		dataSourceService.purgeConnections(dataSourceName);
		
		return SUCCESS;
		
	}



	public DatasourceService getDataSourceService() {
		return dataSourceService;
	}



	public void setDataSourceService(DatasourceService dataSourceService) {
		this.dataSourceService = dataSourceService;
	}



	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

}
