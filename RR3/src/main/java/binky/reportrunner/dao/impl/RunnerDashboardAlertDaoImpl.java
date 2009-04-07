package binky.reportrunner.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import binky.reportrunner.dao.RunnerDashboardAlertDao;
import binky.reportrunner.data.DashboardAlertData;
import binky.reportrunner.data.RunnerDashboardAlert;
import binky.reportrunner.data.RunnerDataSource;

public class RunnerDashboardAlertDaoImpl extends HibernateDaoSupport implements RunnerDashboardAlertDao {
	private Logger logger = Logger.getLogger(RunnerDashboardAlertDaoImpl.class);
	public void deleteAlert(Integer id) {
		logger.debug("delete: " + id); 
		getHibernateTemplate().delete((RunnerDataSource)
				getHibernateTemplate().get(RunnerDashboardAlert.class,
						id));		
	}

	public RunnerDashboardAlert getAlert(Integer id) {
		logger.debug("get alert: "+id);
		return (RunnerDashboardAlert) getHibernateTemplate().get(
				RunnerDashboardAlert.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<RunnerDashboardAlert> getAlertsForGroup(String groupName) {
		logger.debug("list alerts for goup");
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerDashboardAlert.class);
		criteria.add(Expression.eq("group.groupName", groupName));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<RunnerDashboardAlert> getAllAlerts() {
		logger.debug("list alerts");
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerDashboardAlert.class);
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public void saveAlertData(DashboardAlertData data) {
		logger.debug("save or update alert data for: " + data.getPk().getParentAlert().getAlertName());
		getHibernateTemplate().saveOrUpdate(data);	
	}

	public void saveUpdateAlert(RunnerDashboardAlert alert) {
		logger.debug("save or update for: " + alert.getAlertName());
		getHibernateTemplate().saveOrUpdate(alert);	
	}

}
