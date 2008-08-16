package binky.reportrunner.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import binky.reportrunner.dao.RunnerJobDao;
import binky.reportrunner.data.RunnerJob;

public class RunnerJobDaoImpl extends HibernateDaoSupport implements RunnerJobDao {

	public void deleteJob(String jobName, String groupName) {
		// TODO Auto-generated method stub
		
	}

	public RunnerJob getJob(String jobName, String groupName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<RunnerJob> listJobs(String groupName) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveUpdateJob(RunnerJob job) {
		// TODO Auto-generated method stub
		
	}

}
