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
 * Module: RunnerGroupDaoImpl.java
 ******************************************************************************/
package binky.reportrunner.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerGroup;

public class RunnerGroupDaoImpl extends HibernateDaoSupport implements
		RunnerGroupDao {
	private Logger logger = Logger.getLogger(RunnerGroupDaoImpl.class);
	public void deleteGroup(String groupName) {
		logger.debug("delete group: " + groupName);
		getHibernateTemplate().delete((RunnerGroup)
				getHibernateTemplate().get(RunnerGroup.class,
						groupName));
	}

	public RunnerGroup getGroup(String groupName) {
		logger.debug("get group: " + groupName);
		return (RunnerGroup) getHibernateTemplate().get(
				RunnerGroup.class, groupName);
	}

	@SuppressWarnings("unchecked")
	public List<RunnerGroup> listGroups() {
		logger.debug("list groups");
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerGroup.class);
		criteria.addOrder(Order.asc("groupName"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public void saveUpdateGroup(RunnerGroup group) {
		logger.debug("save or update for: " + group.getGroupName());
		getHibernateTemplate().saveOrUpdate(group);
	}


}
