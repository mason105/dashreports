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
 * Module: RunnerJobParameterDaoImpl.java
 ******************************************************************************/
package binky.reportrunner.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import binky.reportrunner.dao.RunnerJobParameterDao;
import binky.reportrunner.data.RunnerJobParameter;

public class RunnerJobParameterDaoImpl extends HibernateDaoSupport implements RunnerJobParameterDao {
	private static final Logger logger = Logger.getLogger(RunnerJobParameterDaoImpl.class);
	@SuppressWarnings("unchecked")
	public void updateParametersForJob(String jobName, String groupName,
			List<RunnerJobParameter> parameters) {		
		logger.debug("updating parameters for job/group:" + jobName + "/" + groupName);
		//delete any parameters first
		
		logger.debug("deleting existing parameters");
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerJobParameter.class);
		criteria.add(Expression.eq("pk.runnerJob_pk.jobName", jobName)).add(Expression.eq("pk.runnerJob_pk.group.groupName", groupName));
		List<RunnerJobParameter> params= getHibernateTemplate().findByCriteria(criteria);
		if (params.size()>0) getHibernateTemplate().deleteAll(params);
		getHibernateTemplate().flush();
		for (RunnerJobParameter p:parameters) {
			logger.debug("saving parameter idx:" + p.getPk());
			getHibernateTemplate().saveOrUpdate(p);
		}
		
	}

}
