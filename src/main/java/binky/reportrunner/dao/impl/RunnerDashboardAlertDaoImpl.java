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

import binky.reportrunner.dao.RunnerDashboardAlertDao;
import binky.reportrunner.data.RunnerDashboardAlert;

public class RunnerDashboardAlertDaoImpl extends HibernateDaoSupport implements
		RunnerDashboardAlertDao {
	private Logger logger = Logger.getLogger(RunnerDashboardAlertDaoImpl.class);

	public void deleteAlert(Integer id) {
		logger.debug("delete: " + id);
		getHibernateTemplate().delete(
				(RunnerDashboardAlert) getHibernateTemplate().get(
						RunnerDashboardAlert.class, id));
	}

	public RunnerDashboardAlert getAlert(Integer id) {
		logger.debug("get alert: " + id);
		return (RunnerDashboardAlert) getHibernateTemplate().get(
				RunnerDashboardAlert.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<RunnerDashboardAlert> getAlertsForGroup(String groupName) {
		logger.debug("list alerts for goup");
		DetachedCriteria criteria = DetachedCriteria
				.forClass(RunnerDashboardAlert.class);
		criteria.add(Expression.eq("group.groupName", groupName));
		criteria.addOrder(Order.asc("displayRow"));
		criteria.addOrder(Order.asc("displayColumn"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<RunnerDashboardAlert> getAllAlerts() {
		logger.debug("list alerts");
		DetachedCriteria criteria = DetachedCriteria
				.forClass(RunnerDashboardAlert.class);
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public void saveUpdateAlert(RunnerDashboardAlert alert) {
		getHibernateTemplate().saveOrUpdate(alert);
		
	}

}
