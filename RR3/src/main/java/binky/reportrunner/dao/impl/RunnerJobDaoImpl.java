package binky.reportrunner.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import binky.reportrunner.dao.RunnerJobDao;
import binky.reportrunner.data.RunnerJob;

public class RunnerJobDaoImpl extends HibernateDaoSupport implements
		RunnerJobDao {

	public void deleteJob(String jobName, String groupName) {
		getHibernateTemplate().delete(
				(RunnerJob) getHibernateTemplate()
						.get(RunnerJob.class, jobName));
	}

	public RunnerJob getJob(String jobName, String groupName) {
		return (RunnerJob) getHibernateTemplate().get(RunnerJob.class, jobName);
	}

	@SuppressWarnings("unchecked")
	public List<RunnerJob> listJobs(String groupName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerJob.class);
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public void saveUpdateJob(RunnerJob job) {
		getHibernateTemplate().saveOrUpdate(job);
	}

}
