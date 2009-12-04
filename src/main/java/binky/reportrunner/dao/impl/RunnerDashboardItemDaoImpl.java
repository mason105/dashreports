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
 * Module: RunnerDashboardAlertDaoImpl.java
 ******************************************************************************/
package binky.reportrunner.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import binky.reportrunner.dao.RunnerDashboardItemDao;
import binky.reportrunner.data.RunnerDashboardItem;

public class RunnerDashboardItemDaoImpl extends HibernateDaoSupport implements
		RunnerDashboardItemDao {
	private Logger logger = Logger.getLogger(RunnerDashboardItemDaoImpl.class);

	public void deleteItem(Integer id) {
		logger.debug("delete: " + id);
		getHibernateTemplate().delete(
				(RunnerDashboardItem) getHibernateTemplate().get(
						RunnerDashboardItem.class, id));
	}

	public RunnerDashboardItem getItem(Integer id) {
		logger.debug("get alert: " + id);
		return (RunnerDashboardItem) getHibernateTemplate().get(
				RunnerDashboardItem.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<RunnerDashboardItem> getItemsForGroup(String groupName) {
		logger.debug("list alerts for goup");
		DetachedCriteria criteria = DetachedCriteria
				.forClass(RunnerDashboardItem.class);
		criteria.add(Expression.eq("group.groupName", groupName));
		criteria.addOrder(Order.asc("displayRow"));
		criteria.addOrder(Order.asc("displayColumn"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<RunnerDashboardItem> getAllItems() {
		logger.debug("list alerts");
		DetachedCriteria criteria = DetachedCriteria
				.forClass(RunnerDashboardItem.class);
		criteria.addOrder(Order.asc("group.groupName"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public void saveUpdateItem(RunnerDashboardItem alert) {
		getHibernateTemplate().saveOrUpdate(alert);
		
	}

}
