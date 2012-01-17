package binky.reportrunner.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.DatabaseObject;

public class HibernateDaoImpl<T extends DatabaseObject<ID>,ID extends Serializable> extends HibernateDaoSupport implements ReportRunnerDao<T, ID> {

	private Class<T> clazz;
	
	public HibernateDaoImpl(Class<T> clazz) {
		this.clazz=clazz;
	}
	
	public void delete(ID id) {

		super.getHibernateTemplate().delete(super.getHibernateTemplate().get(clazz, id));
		
	}

	public T get(ID id) {
		return	(T)super.getHibernateTemplate().get(clazz, id);
	}

	public List<T> getAll() {
		return (List<T>)super.getHibernateTemplate().loadAll(clazz);
	}

	public List<T> findByNamedQuery(String queryName, Object[] values) {
		return (List<T>)super.getHibernateTemplate().findByNamedQuery(queryName, values);
	}

	public void saveOrUpdate(T entity) {
		//dealing with the caching while using the hibernate session in view filter
		if (this.get(entity.getId()) != null) {
			super.getHibernateTemplate().merge(entity);
		} else {
			super.getHibernateTemplate().saveOrUpdate(entity);
		}
	}

	@Override
	public List<T> findByNamedQuery(String queryName, Object[] values,
			int maxResults) {
		super.getHibernateTemplate().setMaxResults(maxResults);
		return (List<T>)super.getHibernateTemplate().findByNamedQuery(queryName, values);
	}

}
