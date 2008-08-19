package binky.reportrunner.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import binky.reportrunner.dao.RunnerDataSourceDao;
import binky.reportrunner.data.RunnerDataSource;

public class RunnerDataSourceDaoImpl extends HibernateDaoSupport implements
		RunnerDataSourceDao {

	public void saveUpdateDataSource(RunnerDataSource dataSource) {
		getHibernateTemplate().saveOrUpdate(dataSource);
	}

	public void deleteDataSource(String dataSourceName) {
		getHibernateTemplate().delete((RunnerDataSource)
				getHibernateTemplate().get(RunnerDataSource.class,
						dataSourceName));
	}

	public RunnerDataSource getDataSource(String dataSourceName) {
		return (RunnerDataSource) getHibernateTemplate().get(
				RunnerDataSource.class, dataSourceName);
	}

	@SuppressWarnings("unchecked")
	public List<RunnerDataSource> listDataSources() {
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerDataSource.class);
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
