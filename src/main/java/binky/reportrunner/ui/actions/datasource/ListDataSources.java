package binky.reportrunner.ui.actions.datasource;

import java.util.List;

import binky.reportrunner.dao.RunnerDataSourceDao;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class ListDataSources extends AdminRunnerAction {

	private static final long serialVersionUID = 1L;
	private List<RunnerDataSource> dataSources;

	@Override
	public String execute() throws Exception {
		dataSources = dataSourceDao.listDataSources();
		return SUCCESS;
	}

	public List<RunnerDataSource> getDataSources() {
		return dataSources;
	}


	private RunnerDataSourceDao dataSourceDao;

	public RunnerDataSourceDao getDataSourceDao() {
		return dataSourceDao;
	}

	public void setDataSourceDao(RunnerDataSourceDao dataSourceDao) {
		this.dataSourceDao = dataSourceDao;
	}

}
