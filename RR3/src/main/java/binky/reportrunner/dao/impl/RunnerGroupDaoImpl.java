package binky.reportrunner.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerGroup;

public class RunnerGroupDaoImpl extends HibernateDaoSupport implements
		RunnerGroupDao {

	public void deleteGroup(String groupName) {
		getHibernateTemplate().delete((RunnerGroup)
				getHibernateTemplate().get(RunnerGroup.class,
						groupName));
	}

	public RunnerGroup getGroup(String groupName) {
		return (RunnerGroup) getHibernateTemplate().get(
				RunnerGroup.class, groupName);
	}

	@SuppressWarnings("unchecked")
	public List<RunnerGroup> listGroups() {
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerGroup.class);
		criteria.addOrder(Order.asc("groupName"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public void saveUpdateGroup(RunnerGroup group) {
		getHibernateTemplate().saveOrUpdate(group);
	}

}
