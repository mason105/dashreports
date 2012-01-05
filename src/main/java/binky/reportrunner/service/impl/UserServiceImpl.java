package binky.reportrunner.service.impl;

import java.util.List;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.service.UserService;

public class UserServiceImpl implements UserService {

	private ReportRunnerDao<RunnerUser, String> userDao;

	private ReportRunnerDao<RunnerGroup, String> groupDao;

	@Override
	public void saveOrUpdate(RunnerUser user) {
		userDao.saveOrUpdate(user);
	}

	@Override
	public void deleteUser(String userName) {
		userDao.delete(userName);
	}

	@Override
	public RunnerUser getUser(String userName) {
		return userDao.get(userName);
	}
	@Override
	public List<RunnerGroup> getGroupsForUser(String userName) {

		RunnerUser user = userDao.get(userName);
		if (user.getIsAdmin()) {
			return groupDao.getAll();
		} else {
			return user.getGroups();
		}
		
	}
	
	public void setUserDao(ReportRunnerDao<RunnerUser, String> userDao) {
		this.userDao = userDao;
	}

	public void setGroupDao(ReportRunnerDao<RunnerGroup, String> groupDao) {
		this.groupDao = groupDao;
	}


}
