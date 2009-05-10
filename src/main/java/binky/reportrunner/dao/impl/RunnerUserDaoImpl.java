package binky.reportrunner.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import binky.reportrunner.dao.RunnerUserDao;
import binky.reportrunner.data.RunnerUser;

public class RunnerUserDaoImpl extends HibernateDaoSupport implements
		RunnerUserDao {
	private Logger logger = Logger.getLogger(RunnerUserDaoImpl.class);
	public void deleteUser(String userName) {
		logger.debug("delete user for: " + userName);
		getHibernateTemplate().delete(
				(RunnerUser) getHibernateTemplate().get(RunnerUser.class,
						userName));
	}

	public RunnerUser getUser(String userName) {
		logger.debug("get user for: " + userName);
		return (RunnerUser) getHibernateTemplate().get(RunnerUser.class,
				userName);
	}

	@SuppressWarnings("unchecked")
	public List<RunnerUser> listUsers() {
		logger.debug("list users");
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerUser.class);
		criteria=criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) ;
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public void saveUpdateUser(RunnerUser user) {
		logger.debug("save update user for: " + user.getUserName());
		getHibernateTemplate().saveOrUpdate(user);
	}

}
