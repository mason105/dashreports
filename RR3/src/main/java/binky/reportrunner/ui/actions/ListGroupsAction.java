package binky.reportrunner.ui.actions;

import java.util.List;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerGroup;

import com.opensymphony.xwork2.ActionSupport;

public class ListGroupsAction extends ActionSupport {

	private static final long serialVersionUID = -1335751757190312426L;
	

	private RunnerGroupDao runnerGroupDao;

	private List<RunnerGroup> groups;
	@Override
	public String execute() throws Exception{
		this.groups = runnerGroupDao.listGroups();
		return SUCCESS;		
	}


	public RunnerGroupDao getRunnerGroupDao() {
		return runnerGroupDao;
	}

	public void setRunnerGroupDao(RunnerGroupDao runnerGroupDao) {
		this.runnerGroupDao = runnerGroupDao;
	}

	public List<RunnerGroup> getGroups() {
		return groups;
	}
	
	
}
