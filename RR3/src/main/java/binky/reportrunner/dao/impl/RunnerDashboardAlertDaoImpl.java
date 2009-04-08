package binky.reportrunner.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import binky.reportrunner.dao.RunnerDashboardAlertDao;
import binky.reportrunner.data.DashboardAlertData;
import binky.reportrunner.data.RunnerDashboardAlert;
import binky.reportrunner.data.RunnerDataSource;

public class RunnerDashboardAlertDaoImpl extends HibernateDaoSupport implements
		RunnerDashboardAlertDao {
	private Logger logger = Logger.getLogger(RunnerDashboardAlertDaoImpl.class);

	public void deleteAlert(Integer id) {
		logger.debug("delete: " + id);
		getHibernateTemplate().delete(
				(RunnerDataSource) getHibernateTemplate().get(
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
		criteria.addOrder(Order.asc("subGroupName"));
		criteria.addOrder(Order.asc("id"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<RunnerDashboardAlert> getAllAlerts() {
		logger.debug("list alerts");
		DetachedCriteria criteria = DetachedCriteria
				.forClass(RunnerDashboardAlert.class);
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public void saveAlertData(DashboardAlertData data) {
		logger.debug("save or update alert data for: "
				+ data.getPk().getParentAlert().getAlertName());
		getHibernateTemplate().saveOrUpdate(data);
	}

	public void saveUpdateAlert(RunnerDashboardAlert alert) {
		logger.debug("save or update for: " + alert.getAlertName());
		getHibernateTemplate().saveOrUpdate(alert);
	}

	@SuppressWarnings("unchecked")
	public List<DashboardAlertData> getAlertDataForRange(Integer alertId,
			Date startDateTime, Date endDateTime) {
		logger.debug("get alert data for range " + startDateTime + " -> " + endDateTime);
		DetachedCriteria criteria = DetachedCriteria
				.forClass(RunnerDashboardAlert.class);
		criteria.add(Expression.eq("id", alertId));
		criteria.add(Expression.between("timeDataCollected", startDateTime, endDateTime));
		criteria.addOrder(Order.asc("timeDataCollected"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public DashboardAlertData getLatestAlertData(Integer alertId) {
		logger.debug("get latest alert data");
		DetachedCriteria criteria = DetachedCriteria
				.forClass(RunnerDashboardAlert.class);
		criteria.add(Expression.eq("id", alertId));
		criteria.addOrder(Order.desc("timeDataCollected"));
		return (DashboardAlertData) getHibernateTemplate().findByCriteria(
				criteria, 0, 1).get(0);
	}

	@SuppressWarnings("unchecked")
	public List<DashboardAlertData> getLatestAlertData(Integer alertId,
			int count) {
		logger.debug("get latest alert data - and more " + count);
		DetachedCriteria criteria = DetachedCriteria
				.forClass(RunnerDashboardAlert.class);
		criteria.add(Expression.eq("id", alertId));
		criteria.addOrder(Order.desc("timeDataCollected"));
		return getHibernateTemplate().findByCriteria(criteria, 0, count);
	}

}
