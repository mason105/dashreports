package binky.reportrunner.ui.actions.group;

import java.util.LinkedList;
import java.util.List;

import binky.reportrunner.dao.RunnerDataSourceDao;
import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class SetupEditGroup extends AdminRunnerAction {

	private RunnerGroupDao groupDao;
	private RunnerDataSourceDao dataSourceDao;
	private String groupName;
	private RunnerGroup group;

	private List<String> dataSources;

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {

		if (super.getUser().getGroups().contains(groupName)
				|| super.getUser().getIsAdmin()) {

			RunnerGroup group = groupDao.getGroup(groupName);
			if (group != null) {
				dataSources = new LinkedList<String>();
				for (RunnerDataSource ds : group.getDataSources()) {
					dataSources.add(ds.getDataSourceName());
				}
			} else {
				dataSources = new LinkedList<String>();
			}
		} else {
			SecurityException se = new SecurityException("Group " + groupName
					+ " not valid for user " + super.getUser().getUserName());
			throw se;
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

	public RunnerGroup getGroup() {
		return group;
	}

	public List<String> getDataSources() {
		return dataSources;
	}

}
