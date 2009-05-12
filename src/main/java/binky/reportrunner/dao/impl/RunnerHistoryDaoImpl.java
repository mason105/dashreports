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
	
	
}