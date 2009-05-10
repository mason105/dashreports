package binky.reportrunner.ui.actions.user;

import java.util.List;

import binky.reportrunner.dao.RunnerUserDao;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class ListUsers extends AdminRunnerAction{

	private static final long serialVersionUID = 1L;
	private List<RunnerUser> users;
	@Override
	public String execute() throws Exception {
		this.users=userDao.listUsers();
		return SUCCESS;
	}

	private RunnerUserDao userDao;

	public RunnerUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(RunnerUserDao userDao) {
		this.userDao = userDao;
	}

	public List<RunnerUser> getUsers() {
		return users;
	}

	public void setUsers(List<RunnerUser> users) {
		this.users = users;
	}
	
	

}
