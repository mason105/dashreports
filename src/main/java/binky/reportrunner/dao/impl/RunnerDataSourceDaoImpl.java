package binky.reportrunner.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import binky.reportrunner.dao.RunnerDataSourceDao;
import binky.reportrunner.data.RunnerDataSource;

public class RunnerDataSourceDaoImpl extends HibernateDaoSupport implements
		RunnerDataSourceDao {
	private Logger logger = Logger.getLogger(RunnerDataSourceDaoImpl.class);
	public void saveUpdateDataSource(RunnerDataSource dataSource) {
		logger.debug("save or update for: " + dataSource.getDataSourceName());
		getHibernateTemplate().saveOrUpdate(dataSource);
	}

	public void deleteDataSource(String dataSourceName) {
		logger.debug("delete: " + dataSourceName); 
		getHibernateTemplate().delete((RunnerDataSource)
				getHibernateTemplate().get(RunnerDataSource.class,
						dataSourceName));
	}

	public RunnerDataSource getDataSource(String dataSourceName) {
		logger.debug("get datasource: "+dataSourceName);
		return (RunnerDataSource) getHibernateTemplate().get(
				RunnerDataSource.class, dataSourceName);
	}

	@SuppressWarnings("unchecked")
	public List<RunnerDataSource> listDataSources() {
		logger.debug("list data sources");
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerDataSource.class);
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
