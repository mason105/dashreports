package binky.reportrunner.ui.actions.datasource;

import java.util.List;

import binky.reportrunner.dao.RunnerDataSourceDao;
import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class SetupEditDataSource extends AdminRunnerAction{

	private static final long serialVersionUID = 1L;
	private String dataSourceName;
	private RunnerDataSource dataSource;
	private List<RunnerGroup> groups;
	@Override
	public String execute() throws Exception {
		if ((dataSourceName !=null) && (!dataSourceName.isEmpty())){
			dataSource=dataSourceDao.getDataSource(dataSourceName);
		} else {
			dataSource=new RunnerDataSource("","","","","","");
		}
		groups=groupDao.listGroups();
		return SUCCESS;
	}
	
	private RunnerDataSourceDao dataSourceDao;
	private RunnerGroupDao groupDao;

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
	public RunnerDataSource getDataSource() {
		return dataSource;
	}
	public List<RunnerGroup> getGroups() {
		return groups;
	}
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
