package binky.reportrunner.ui.actions.group;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class SetupEditGroup extends AdminRunnerAction {

	private RunnerGroupDao groupDao;
	private String groupName;
	private RunnerGroup group;

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {

		if (super.getSessionUser().getGroups().contains(groupName)
				|| super.getSessionUser().getIsAdmin()) {

			if (groupName != null) {
				this.group = groupDao.getGroup(groupName);
			} else {
				this.group = new RunnerGroup();
			}
				
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

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public RunnerGroup getGroup() {
		return group;
	}

}
