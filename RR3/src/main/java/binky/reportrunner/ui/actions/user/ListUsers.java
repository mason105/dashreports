package binky.reportrunner.ui.actions.user;

import binky.reportrunner.dao.RunnerUserDao;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class ListUsers extends AdminRunnerAction{

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	private RunnerUserDao userDao;

	public RunnerUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(RunnerUserDao userDao) {
		this.userDao = userDao;
	}

}
