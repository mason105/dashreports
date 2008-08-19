package binky.reportrunner.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import binky.reportrunner.dao.RunnerUserDao;
import binky.reportrunner.data.RunnerUser;

public class RunnerUserDaoImpl extends HibernateDaoSupport implements
		RunnerUserDao {

	public void deleteUser(String userName) {
		getHibernateTemplate().delete(
				(RunnerUser) getHibernateTemplate().get(RunnerUser.class,
						userName));
	}

	public RunnerUser getUser(String userName) {
		return (RunnerUser) getHibernateTemplate().get(RunnerUser.class,
				userName);
	}

	@SuppressWarnings("unchecked")
	public List<RunnerUser> listUsers() {
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerUser.class);
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public void saveUpdateUser(RunnerUser user) {
		getHibernateTemplate().saveOrUpdate(user);
	}

}
