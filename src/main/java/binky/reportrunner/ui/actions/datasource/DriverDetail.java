package binky.reportrunner.ui.actions.datasource;

import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.service.JDBCDriverDefinition;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class DriverDetail extends StandardRunnerAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6471170147490083690L;

	private DatasourceService dataSourceService;
	private String jdbcUrl;
	private String jdbcClass;
	private String jdbcDriver;
	
	@Override
	public String execute() throws Exception {

		JDBCDriverDefinition def= dataSourceService.getJDBCDriverDefinitions().getDefinitions().get(jdbcDriver);
		
		this.jdbcClass=def.getDriverName();
		this.jdbcUrl=def.getUrl();
		
		return SUCCESS;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public String getJdbcClass() {
		return jdbcClass;
	}

	public void setDataSourceService(DatasourceService dataSourceService) {
		this.dataSourceService = dataSourceService;
	}

	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

}
