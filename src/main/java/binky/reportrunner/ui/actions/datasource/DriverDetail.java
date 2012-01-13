package binky.reportrunner.ui.actions.datasource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerDataSource;
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
	private RunnerDataSource dataSource;
	private static final Logger logger = Logger.getLogger(DriverDetail.class);
	@Override
	public String execute() throws Exception {

		if (dataSource!=null) {
			logger.debug("datasource name: " + dataSource.getDataSourceName());
		} else {
			logger.debug("dataSource is null");
		}
		JDBCDriverDefinition def= dataSourceService.getJDBCDriverDefinitions().getDefinitions().get(jdbcDriver);
		this.jdbcClass=def.getDriverName();
		this.jdbcUrl=def.getUrl();
		RunnerDataSource ds =StringUtils.isNotBlank(dataSource.getDataSourceName())? dataSourceService.getDataSource(dataSource.getDataSourceName()):null;
		if (ds!=null) {
			logger.debug("got an existing datasource here - what to do: " + ds.getDataSourceName() );
			String currentDriver="";
			//hacky as hacky mchackerson of the clan mchackerson
			for (JDBCDriverDefinition d : dataSourceService.getJDBCDriverDefinitions().getDefinitions().values()) {
				if (d.getDriverName().equalsIgnoreCase(ds.getJdbcClass())) {
					currentDriver=d.getLabel();
				}
			}
			logger.debug("current driver: "  + ds.getJdbcClass() + " selected driver " + this.jdbcDriver);
			if (currentDriver.equals(this.jdbcDriver)) {
				this.jdbcClass=ds.getJdbcClass();
				this.jdbcUrl=ds.getJdbcUrl();

			}

		}
		

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

	public void setDataSource(RunnerDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DatasourceService getDataSourceService() {
		return dataSourceService;
	}

	public String getJdbcDriver() {
		return jdbcDriver;
	}

	public RunnerDataSource getDataSource() {
		return dataSource;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public void setJdbcClass(String jdbcClass) {
		this.jdbcClass = jdbcClass;
	}

}
