package binky.reportrunner.ui.actions.group;

import java.util.LinkedList;
import java.util.List;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class SaveGroup extends AdminRunnerAction {

	private RunnerGroupDao groupDao;

	private String groupName;
	private String groupDescription;


	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		if (super.getUser().getGroups().contains(groupName)
				|| super.getUser().getIsAdmin()) {

			RunnerGroup group = groupDao.getGroup(groupName);
				List<RunnerJob> runnerJobs = new LinkedList<RunnerJob>();
				group = new RunnerGroup(groupName, groupDescription,
						runnerJobs);
			
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

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

}
