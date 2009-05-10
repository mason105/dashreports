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
