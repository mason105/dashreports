package binky.reportrunner.ui.actions.group;

import java.util.LinkedList;
import java.util.List;

import binky.reportrunner.dao.RunnerDataSourceDao;
import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class SetupEditGroup extends AdminRunnerAction {

	private RunnerGroupDao groupDao;
	private RunnerDataSourceDao dataSourceDao;
	private String groupName;
	private String groupDescription;

	private List<String> dataSources;

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		RunnerGroup group = groupDao.getGroup(groupName);
		if (group != null) {
			groupName = group.getGroupName();
			groupDescription = group.getGroupDescription();

			dataSources = new LinkedList<String>();
			for (RunnerDataSource ds : group.getDataSources()) {
				dataSources.add(ds.getDataSourceName());
			}
		} else {
			dataSources = new LinkedList<String>();
		}
		return SUCCESS;
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public List<String> getDataSources() {
		return dataSources;
	}

	public void setDataSources(List<String> dataSources) {
		this.dataSources = dataSources;
	}

}