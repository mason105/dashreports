package binky.reportrunner.ui.actions.datasource;

import binky.reportrunner.dao.RunnerDataSourceDao;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class SaveDataSource extends AdminRunnerAction {

	private static final long serialVersionUID = 1L;

	private RunnerDataSource dataSource;
	@Override
	public String execute() throws Exception {
		
		dataSourceDao.saveUpdateDataSource(dataSource);

		return SUCCESS;
	}

	private RunnerDataSourceDao dataSourceDao;

	public RunnerDataSourceDao getDataSourceDao() {
		return dataSourceDao;
	}

	public void setDataSourceDao(RunnerDataSourceDao dataSourceDao) {
		this.dataSourceDao = dataSourceDao;
	}

	public RunnerDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(RunnerDataSource dataSource) {
		this.dataSource = dataSource;
	}

	
	
}
