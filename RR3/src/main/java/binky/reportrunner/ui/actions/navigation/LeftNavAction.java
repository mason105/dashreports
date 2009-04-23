package binky.reportrunner.ui.actions.navigation;

import java.util.List;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class LeftNavAction extends StandardRunnerAction {

	private static final long serialVersionUID = -2321083106251542716L;
	private RunnerGroupDao groupDao;
	private List<RunnerGroup> groups;
	@Override
	public String execute() throws Exception {

		if (super.getSessionUser().getIsAdmin()) {
			groups = groupDao.listGroups();
		} else {
			groups = super.getSessionUser().getGroups();
		}
		
		return SUCCESS;
	}
	public RunnerGroupDao getGroupDao() {
		return groupDao;
	}
	public void setGroupDao(RunnerGroupDao groupDao) {
		this.groupDao = groupDao;
	}
	public List<RunnerGroup> getGroups() {
		return groups;
	}
	public void setGroups(List<RunnerGroup> groups) {
		this.groups = groups;
	}

}
