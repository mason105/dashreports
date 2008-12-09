package binky.reportrunner.ui.actions.group;

import java.util.LinkedList;
import java.util.List;

import binky.reportrunner.dao.RunnerDataSourceDao;
import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class SaveGroup extends AdminRunnerAction {

	private RunnerGroupDao groupDao;
	private RunnerDataSourceDao dataSourceDao;

	private String groupName;
	private String groupDescription;

	private List<String> dataSources;

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		if (super.getUser().getGroups().contains(groupName)
				|| super.getUser().getIsAdmin()) {

			List<RunnerDataSource> dsList = new LinkedList<RunnerDataSource>();
			for (String dataSourceName : dataSources) {
				RunnerDataSource ds = dataSourceDao
						.getDataSource(dataSourceName);
				dsList.add(ds);
			}
			RunnerGroup group = groupDao.getGroup(groupName);
			if (group != null) {
				group.setGroupDescription(groupDescription);
				group.setDataSources(dsList);
			} else {
				List<RunnerJob> runnerJobs = new LinkedList<RunnerJob>();
				group = new RunnerGroup(groupName, groupDescription,
						runnerJobs, dsList);
			}
			groupDao.saveUpdateGroup(group);
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

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public void setDataSources(List<String> dataSources) {
		this.dataSources = dataSources;
	}

}
