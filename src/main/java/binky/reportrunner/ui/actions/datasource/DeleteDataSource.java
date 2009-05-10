package binky.reportrunner.ui.actions.datasource;

import binky.reportrunner.dao.RunnerDataSourceDao;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class DeleteDataSource extends AdminRunnerAction {

	private static final long serialVersionUID = 1L;

	private String dataSourceName;

	@Override
	public String execute() throws Exception {
		dataSourceDao.deleteDataSource(dataSourceName);
		return SUCCESS;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	private RunnerDataSourceDao dataSourceDao;

	public RunnerDataSourceDao getDataSourceDao() {
		return dataSourceDao;
	}

	public void setDataSourceDao(RunnerDataSourceDao dataSourceDao) {
		this.dataSourceDao = dataSourceDao;
	}
}
