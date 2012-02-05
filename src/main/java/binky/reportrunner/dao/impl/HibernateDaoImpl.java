package binky.reportrunner.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.DatabaseObject;



public class HibernateDaoImpl<T extends DatabaseObject<ID>,ID extends Serializable>  implements ReportRunnerDao<T, ID> {

	private SessionFactory sessionFactory;
	
	private Session session;
	
	private Class<T> clazz;
	
	public HibernateDaoImpl(Class<T> clazz) {
		this.clazz=clazz;
	}
	
	public void delete(ID id) {
		T o = (T)getSession().get(clazz, id);
		if (o!=null)
		getSession().delete(o);
		
	}
	
	private Session getSession()  {
		if (this.session==null||!this.session.isOpen()) {
			session = sessionFactory.openSession();
		}
		return this.session;
	}

	public T get(ID id) {
		T o = (T)getSession().get(clazz, id);
		
		return	o;
	}

	public List<T> getAll() {
		return (List<T>)getSession().createCriteria(clazz).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@SuppressWarnings("unchecked")
	public List<T> findByNamedQuery(String queryName, Object[] values) {
		Query q = getSession().getNamedQuery(queryName);
		if (values!=null) {
			for (int i=0;i<values.length;i++) {
				q.setParameter(i, values[i]);
			}
		}

		return (List<T>)q.list();
	}

	public void saveOrUpdate(T entity) {
		//dealing with the caching while using the hibernate session in view filter
		if (entity.getId()!=null&&this.get(entity.getId()) != null) {
			getSession().merge(entity);
		} else {
			getSession().saveOrUpdate(entity);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedQuery(String queryName, Object[] values,
			int maxResults) {
		
		Query q = getSession().getNamedQuery(queryName);
		if (values!=null) {
			for (int i=0;i<values.length;i++) {
				q.setParameter(i, values[i]);
			}
		}
		q.setMaxResults(maxResults);
		return (List<T>)q.list();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


}
