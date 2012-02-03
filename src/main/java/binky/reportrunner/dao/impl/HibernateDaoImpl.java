package binky.reportrunner.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import binky.reportrunner.dao.NoSessionException;
import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.DatabaseObject;



public class HibernateDaoImpl<T extends DatabaseObject<ID>,ID extends Serializable>  implements ReportRunnerDao<T, ID> {

	private SessionFactory sessionFactory;
	
	private Class<T> clazz;
	
	public HibernateDaoImpl(Class<T> clazz) {
		this.clazz=clazz;
	}
	
	public void delete(ID id) {
		
		sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().get(clazz, id));
		
	}

	public T get(ID id) {
		T o = (T)sessionFactory.getCurrentSession().get(clazz, id);
		
		return	o;
	}

	public List<T> getAll() {
		return (List<T>)sessionFactory.getCurrentSession().createCriteria(clazz).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@SuppressWarnings("unchecked")
	public List<T> findByNamedQuery(String queryName, Object[] values) {
		Query q = sessionFactory.getCurrentSession().getNamedQuery(queryName);
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
			sessionFactory.getCurrentSession().merge(entity);
		} else {
			sessionFactory.getCurrentSession().saveOrUpdate(entity);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedQuery(String queryName, Object[] values,
			int maxResults) {
		
		Query q = sessionFactory.getCurrentSession().getNamedQuery(queryName);
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

	
	
	@Override
	public Session openSession() {		
		return sessionFactory.openSession();
	}

	@Override
	public T getInSession(ID id,Session session) throws NoSessionException {
		if (session!=null && session.isOpen()&& session.isConnected()) {
			return  (T)session.get(clazz, id);
		} else {
			throw new NoSessionException();
		}
	}


}
