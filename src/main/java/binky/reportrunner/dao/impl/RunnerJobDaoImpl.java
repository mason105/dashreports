/*******************************************************************************
 * Copyright (c) 2009 Daniel Grout.
 * 
 * GNU GENERAL PUBLIC LICENSE - Version 3
 * 
 * This file is part of Report Runner (http://code.google.com/p/reportrunner).
 * 
 * Report Runner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Runner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Runner. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Module: RunnerJobDaoImpl.java
 ******************************************************************************/
package binky.reportrunner.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
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
	private Logger logger = Logger.getLogger(RunnerJobDaoImpl.class);

	@SuppressWarnings("unchecked")
	public void deleteJob(String jobName, String groupName) {
		logger.debug("delete job for: " + jobName + " " + groupName);
		RunnerGroup group = (RunnerGroup) getHibernateTemplate().get(
				RunnerGroup.class, groupName);

		RunnerJob_pk pk = new RunnerJob_pk();
		pk.setGroup(group);
		pk.setJobName(jobName);

		// delete any parameters first
		DetachedCriteria criteria = DetachedCriteria
				.forClass(RunnerJobParameter.class);
		criteria.add(Expression.eq("pk.runnerJob_pk.jobName", jobName)).add(				
				Expression.eq("pk.runnerJob_pk.group.groupName", groupName));
		List<RunnerJobParameter> params = getHibernateTemplate()
				.findByCriteria(criteria);
		if (params.size() > 0)
			getHibernateTemplate().deleteAll(params);
		getHibernateTemplate().flush();
		getHibernateTemplate().delete(
				(RunnerJob) getHibernateTemplate().get(RunnerJob.class, pk));

	}

	public RunnerJob getJob(String jobName, String groupName) {
		logger.debug("get job for: " + jobName + " " + groupName);
		RunnerGroup group = (RunnerGroup) getHibernateTemplate().get(
				RunnerGroup.class, groupName);

		RunnerJob_pk pk = new RunnerJob_pk();
		pk.setGroup(group);
		pk.setJobName(jobName);
		return (RunnerJob) getHibernateTemplate().get(RunnerJob.class, pk);
	}

	@SuppressWarnings("unchecked")
	public List<RunnerJob> listJobs(String groupName) {
		logger.debug("list jobs");
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerJob.class);
		criteria.add(Expression.eq("pk.group.groupName", groupName));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.asc("pk.jobName"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public void saveUpdateJob(RunnerJob job) {
		logger.debug("save or update job for: " + job.getPk().getJobName()
				+ " " + job.getPk().getGroup().getGroupName());
		getHibernateTemplate().saveOrUpdate(job);
	}

}
