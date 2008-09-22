package binky.reportrunner.ui.actions;

import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class ListGroupsAction extends StandardRunnerAction {

	private static final long serialVersionUID = -1335751757190312426L;
	private static final Logger logger = Logger.getLogger(ListGroupsAction.class);
	private RunnerGroupDao groupDao;
	private List<RunnerGroup> groups;
	@Override
	public String execute() throws Exception{
		if (super.getUser().getIsAdmin()) {
			logger.debug("is admin - allowing all groups");
			 this.groups=groupDao.listGroups();
		} else {
			logger.debug("is not admin - restricting groups");
			this.groups = super.getUser().getGroups();
		}
		return SUCCESS;		
	}

	public List<RunnerGroup> getGroups() {
		return groups;
	}

	public final RunnerGroupDao getGroupDao() {
		return groupDao;
	}

	public final void setGroupDao(RunnerGroupDao groupDao) {
		this.groupDao = groupDao;
	}	
	
	
}