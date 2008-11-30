package binky.reportrunner.ui.actions.datasource;

import binky.reportrunner.dao.RunnerDataSourceDao;
import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class SetupEditDataSource extends AdminRunnerAction{

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	private RunnerDataSourceDao dataSourceDao;
	private RunnerGroupDao groupDao;

	
	public RunnerGroupDao getGroupDao() {
		return groupDao;
	}
	public void setGroupDao(RunnerGroupDao groupDao) {
		this.groupDao = groupDao;
	}
	
	public RunnerDataSourceDao getDataSourceDao() {
		return dataSourceDao;
	}
	public void setDataSourceDao(RunnerDataSourceDao dataSourceDao) {
		this.dataSourceDao = dataSourceDao;
	}
	
	
}
