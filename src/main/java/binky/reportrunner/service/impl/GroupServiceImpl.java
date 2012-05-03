package binky.reportrunner.service.impl;

import java.util.List;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerDashboardItem;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.service.GroupService;
import binky.reportrunner.service.ReportService;
import binky.reportrunner.service.UserService;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;

public class GroupServiceImpl implements GroupService {

	private ReportRunnerDao<RunnerGroup, String> groupDao;
	private ReportService reportService;
	private DashboardService dashboardService;
	private UserService userService;
	private DatasourceService datasourceService;
	@Override
	@TriggersRemove(cacheName = "groupCache")
	public void delete(String groupName) throws Exception {
		List<RunnerJob> js = reportService.listJobs(groupName);
		if (js!=null) {
			for (RunnerJob j: js) {
				reportService.deleteJob(j.getPk().getJobName(), groupName);
			}
		}
		
		List<RunnerDashboardItem> is = dashboardService.getItemsForGroup(groupName);
		if (is!=null) {
			for (RunnerDashboardItem i:is) {
				dashboardService.deleteItem(i.getId());
			}
		}
		RunnerGroup g = groupDao.get(groupName);
		for (RunnerUser u: userService.getAll()) {
			if (u.getGroups()!=null && u.getGroups().contains(g)) {
				u.getGroups().remove(g);
				userService.saveOrUpdate(u);
			}
		}
		
		for (RunnerDataSource d:datasourceService.listDataSources()) {
			if (d.getGroups()!=null && d.getGroups().contains(g)) {
				d.getGroups().remove(g);
				datasourceService.saveUpdateDataSource(d);
			}
		}
		
		groupDao.delete(groupName);
	}

	@Override
	public List<RunnerGroup> getAll() {
		return groupDao.getAll();
	}

	@Override
	@Cacheable(cacheName = "groupCache")
	public void saveOrUpdate(RunnerGroup group) {
		
		groupDao.saveOrUpdate(group);
	}

	@Override
	@Cacheable(cacheName = "groupCache")
	public RunnerGroup getGroup(String groupName) {
		return groupDao.get(groupName);
	}

	public void setGroupDao(ReportRunnerDao<RunnerGroup, String> groupDao) {
		this.groupDao = groupDao;
		List<RunnerGroup> groups = groupDao.getAll();
		if (groups==null || groups.size()<1) {
			RunnerGroup group = new RunnerGroup();
			group.setGroupName("Example Group");
			group.setGroupDescription("Sample group, please delete");
			groupDao.saveOrUpdate(group);
		}
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public void setDashboardService(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setDatasourceService(DatasourceService datasourceService) {
		this.datasourceService = datasourceService;
	}

}
