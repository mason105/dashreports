package binky.reportrunner.ui.actions.group;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class SaveGroup extends AdminRunnerAction {

	private RunnerGroupDao groupDao;
	private RunnerGroup group;

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		String groupName = group.getGroupName();
		if (super.getSessionUser().getGroups().contains(groupName)
				|| super.getSessionUser().getIsAdmin()) {								
			groupDao.saveUpdateGroup(group);
		} else {
			SecurityException se = new SecurityException("Group " + groupName
					+ " not valid for user " + super.getSessionUser().getUserName());
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

	public RunnerGroup getGroup() {
		return group;
	}

	public void setGroup(RunnerGroup group) {
		this.group = group;
	}


}
