package binky.reportrunner.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.service.UserService;
import binky.reportrunner.util.EncryptionUtil;

public class UserServiceImpl implements UserService {

	private ReportRunnerDao<RunnerUser, String> userDao;

	private ReportRunnerDao<RunnerGroup, String> groupDao;

	private static Logger logger = Logger.getLogger(UserServiceImpl.class);
	

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
		//if no admin user then create one
		checkForAnyUsers();
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

	@Override
	public RunnerUser createUser(String userName, String password,
			String fullName, boolean readOnly, boolean administrator, boolean locked,
			List<RunnerGroup> groups)  throws NoSuchAlgorithmException{
		EncryptionUtil e = new EncryptionUtil();
		RunnerUser user = new RunnerUser();
		user.setUserName(userName);
		user.setFullName(fullName);
		user.setPassword(e.hashString(password));
		user.setIsReadOnly(readOnly);
		user.setIsLocked(locked);
		user.setIsAdmin(administrator);
		user.setGroups(groups);
		userDao.saveOrUpdate(user);
		return user;
	}

	private void checkForAnyUsers() {
		List<RunnerUser> us = this.userDao.getAll();
		if (us==null || us.size()<1) {
			try {
				logger.warn("creating admin user as there are no users!");
				createUser("admin","password","Administrator",false,true,false,new LinkedList<RunnerGroup>());
			} catch (NoSuchAlgorithmException e) {
				logger.fatal(e,e);
			}
		}
	}
	
}
