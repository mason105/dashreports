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
 * Module: RunnerHistoryDaoImpl.java
 ******************************************************************************/
package binky.reportrunner.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import binky.reportrunner.dao.RunnerHistoryDao;
import binky.reportrunner.data.RunnerHistoryEvent;

public class RunnerHistoryDaoImpl extends HibernateDaoSupport implements RunnerHistoryDao {
	private Logger logger = Logger.getLogger(RunnerHistoryDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	public List<RunnerHistoryEvent> getEvents(String groupName, String jobName) {
		logger.debug("get events for: " + groupName + " " + jobName);
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerHistoryEvent.class)
		.add(Property.forName("groupName").eq(groupName))
		.add(Property.forName("jobName").eq(jobName))
		.addOrder(Order.desc("timestamp"));

		List<RunnerHistoryEvent> findByCriteria = getHibernateTemplate().findByCriteria(criteria);
		logger.debug("got " + findByCriteria.size());
		return findByCriteria;
	}

	@SuppressWarnings("unchecked")
	public List<RunnerHistoryEvent> getEvents(String groupName, String jobName,
			Date startTime, Date endTime) {
		logger.debug("get events for: " + groupName + " " + jobName + " " + startTime + " " + endTime);
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerHistoryEvent.class)
		.add(Property.forName("groupName").eq(groupName))
		.add(Property.forName("jobName").eq(jobName))
		.add(Property.forName("timestamp").ge(startTime))
		.add(Property.forName("timestamp").le(endTime))
		.addOrder(Order.desc("timestamp"));

		List<RunnerHistoryEvent> findByCriteria = getHibernateTemplate().findByCriteria(criteria);
		logger.debug("got " + findByCriteria.size());
		return findByCriteria;
	}

	public void saveEvent(RunnerHistoryEvent event) {
		logger.debug("save event");
		getHibernateTemplate().save(event);		
		logger.info("Event Recorded:" + event);
	}

	@SuppressWarnings("unchecked")
	public void deleteEvents(String groupName, String jobName) {
		logger.debug("delete events for: " + groupName + " " + jobName);
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerHistoryEvent.class)
		.add(Property.forName("groupName").eq(groupName))
		.add(Property.forName("jobName").eq(jobName));

		List<RunnerHistoryEvent> findByCriteria = getHibernateTemplate().findByCriteria(criteria);
		getHibernateTemplate().deleteAll(findByCriteria);
		
	}

	@SuppressWarnings("unchecked")
	public List<RunnerHistoryEvent> getSuccessEvents(int eventCount) {
		logger.debug("get success events");
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerHistoryEvent.class)
		.add(Property.forName("success").eq(true))
		.addOrder(Order.desc("timestamp"));
		List<RunnerHistoryEvent> findByCriteria = getHibernateTemplate().findByCriteria(criteria,0,eventCount);
		logger.debug("got " + findByCriteria.size());
		return findByCriteria;
	}
	@SuppressWarnings("unchecked")
	public List<RunnerHistoryEvent> getFailEvents(int eventCount) {
		logger.debug("get fail events");
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerHistoryEvent.class)
		.add(Property.forName("success").eq(false))
		.addOrder(Order.desc("timestamp"));
		List<RunnerHistoryEvent> findByCriteria = getHibernateTemplate().findByCriteria(criteria,0,eventCount);
		logger.debug("got " + findByCriteria.size());
		return findByCriteria;
	}
	@SuppressWarnings("unchecked")
	public List<RunnerHistoryEvent> getLongestRunningEvents(int eventCount) {
		logger.debug("get longest events");
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerHistoryEvent.class)
		.addOrder(Order.desc("runTime"));
		List<RunnerHistoryEvent> findByCriteria = getHibernateTemplate().findByCriteria(criteria,0,eventCount);
		logger.debug("got " + findByCriteria.size());
		return findByCriteria;
	}

	public void deleteRangeOfEvents(Date oldset) {
		logger.debug("delete events for: "  + oldset );
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerHistoryEvent.class)
		.add(Property.forName("timestamp").le(oldset));

		getHibernateTemplate().deleteAll(getHibernateTemplate().findByCriteria(criteria));	
	}	
	
}
