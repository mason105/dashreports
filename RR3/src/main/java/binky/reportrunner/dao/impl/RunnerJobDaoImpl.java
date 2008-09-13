package binky.reportrunner.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import binky.reportrunner.dao.RunnerJobDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.data.RunnerJob_pk;

public class RunnerJobDaoImpl extends HibernateDaoSupport implements
		RunnerJobDao {

	@SuppressWarnings("unchecked")
	public void deleteJob(String jobName, String groupName) {
		
		RunnerGroup group = (RunnerGroup) getHibernateTemplate().get(
				RunnerGroup.class, groupName);
		
		RunnerJob_pk pk = new RunnerJob_pk();
		pk.setGroup(group);
		pk.setJobName(jobName);
		
		//delete any parameters first
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerJobParameter.class);
		criteria.add(Expression.eq("pk.runnerJob.pk.jobName", jobName));
		List<RunnerJobParameter> params= getHibernateTemplate().findByCriteria(criteria);
		if (params.size()>0) getHibernateTemplate().deleteAll(params);
		
		getHibernateTemplate().delete(
				(RunnerJob) getHibernateTemplate()
						.get(RunnerJob.class, pk));
		
		
	}

	public RunnerJob getJob(String jobName, String groupName) {
		RunnerGroup group = (RunnerGroup) getHibernateTemplate().get(
				RunnerGroup.class, groupName);
		
		RunnerJob_pk pk = new RunnerJob_pk();
		pk.setGroup(group);
		pk.setJobName(jobName);		
		return (RunnerJob) getHibernateTemplate().get(RunnerJob.class, pk);
	}

	@SuppressWarnings("unchecked")
	public List<RunnerJob> listJobs(String groupName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerJob.class);
		criteria.add(Expression.eq("pk.group.groupName", groupName));
		criteria.addOrder(Order.asc("pk.jobName"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public void saveUpdateJob(RunnerJob job) {
		getHibernateTemplate().saveOrUpdate(job);
	}

}
