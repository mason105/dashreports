package binky.reportrunner.ui.actions.datasource;

import java.util.List;

import binky.reportrunner.dao.RunnerDataSourceDao;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class SetupEditDataSource extends AdminRunnerAction{

	private static final long serialVersionUID = 1L;
	private String dataSourceName;
	private RunnerDataSource dataSource;
	private List<String> jdbcDriverNames;
	private DatasourceService dataSourceService;
	@Override
	public String execute() throws Exception {
		if ((dataSourceName !=null) && (!dataSourceName.isEmpty())){
			dataSource=dataSourceDao.getDataSource(dataSourceName);
		} else {
			dataSource=new RunnerDataSource();
		}
		jdbcDriverNames = dataSourceService.getAvailableDriverNames();
		return SUCCESS;
	}
	
	private RunnerDataSourceDao dataSourceDao;

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
	public RunnerDataSource getDataSource() {
		return dataSource;
	}
	public RunnerDataSourceDao getDataSourceDao() {
		return dataSourceDao;
	}
	public void setDataSourceDao(RunnerDataSourceDao dataSourceDao) {
		this.dataSourceDao = dataSourceDao;
	}
	
	public List<String> getJDBCDriverNames() {
		return this.jdbcDriverNames;
	}
}
