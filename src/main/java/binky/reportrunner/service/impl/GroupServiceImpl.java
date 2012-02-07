package binky.reportrunner.service.impl;

import java.util.List;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.service.GroupService;

public class GroupServiceImpl implements GroupService {

	private ReportRunnerDao<RunnerGroup, String> groupDao;
	
	@Override
	@TriggersRemove(cacheName="groupCache")
	public void delete(String groupName) {
		groupDao.delete(groupName);
	}

	@Override
	public List<RunnerGroup> getAll() {
		return groupDao.getAll();
	}

	@Override
	public void saveOrUpdate(RunnerGroup group) {
		groupDao.saveOrUpdate(group);
	}
	@Override
	@Cacheable(cacheName="groupCache")
	public RunnerGroup getGroup(String groupName) {
		return groupDao.get(groupName);
	}

	public void setGroupDao(ReportRunnerDao<RunnerGroup, String> groupDao) {
		this.groupDao = groupDao;
	}

	
}
