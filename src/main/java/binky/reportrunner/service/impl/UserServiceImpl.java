package binky.reportrunner.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.service.UserService;
import binky.reportrunner.util.EncryptionUtil;

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
		RunnerUser user = userDao.get(userName);
		if (user != null) {
			user.setGroups(getGroupsForUser(userName));
		}
		return user;
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

	@Override
	public List<RunnerUser> getAll() {
		return userDao.getAll();
	}

	public void setUserDao(ReportRunnerDao<RunnerUser, String> userDao) {
		this.userDao = userDao;
	}

	public void setGroupDao(ReportRunnerDao<RunnerGroup, String> groupDao) {
		this.groupDao = groupDao;
	}

	@Override
	public boolean changePassword(String userName, String oldPassword,
			String newPassword) throws NoSuchAlgorithmException {
		RunnerUser u = userDao.get(userName);
		EncryptionUtil enc = new EncryptionUtil();
		if (u.getPassword().equals(enc.hashString(oldPassword))) {
			u.setPassword(enc.hashString(newPassword));
			userDao.saveOrUpdate(u);

			return true;
		} else {

			return false;
		}

	}

}
