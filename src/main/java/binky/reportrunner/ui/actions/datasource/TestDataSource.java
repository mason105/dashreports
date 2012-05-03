package binky.reportrunner.ui.actions.datasource;

import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class TestDataSource extends StandardRunnerAction {

	private static final long serialVersionUID = -3337853936350424682L;
	private static final Logger logger = Logger.getLogger(TestDataSource.class);
	
	private DatasourceService dataSourceService;
	private RunnerDataSource dataSource;
	private String information;
	@Override
	public String execute() throws Exception {
		
		logger.debug("testing datasource  "+ dataSource.getDataSourceName());

		this.information=dataSourceService.testDataSource(dataSource);
		
		return SUCCESS;
	}

	public void setDataSource(RunnerDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public RunnerDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSourceService(DatasourceService dataSourceService) {
		this.dataSourceService = dataSourceService;
	}

	public String getInformation() {
		return information;
	}

}
