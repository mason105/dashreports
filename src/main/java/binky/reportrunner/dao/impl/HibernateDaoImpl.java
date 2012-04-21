package binky.reportrunner.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.DatabaseObject;

public class HibernateDaoImpl<T extends DatabaseObject<ID>, ID extends Serializable>
		implements ReportRunnerDao<T, ID> {

	private SessionFactory sessionFactory;

	private Class<T> clazz;
	
	private static Logger logger = Logger.getLogger(HibernateDaoImpl.class);
	
	public HibernateDaoImpl(Class<T> clazz) {
		this.clazz = clazz;
		logger.info("creating generic DAO for class " + clazz.getName());
	}

	
	
	public void delete(ID id) {
		logger.trace("deleting object with ID: " + id+ " for class " + clazz.getName());
		Session session = getSession();
		T o = (T) getSession().get(clazz, id);
		if (o != null)
			session.delete(o);
		logger.trace("done deleting object with ID: " + id+ " for class " + clazz.getName());
	}

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public T get(ID id) {
		logger.trace("getting object with ID: " + id + " for class " + clazz.getName());
		return (T) getSession().get(clazz, id);
	}

	public List<T> getAll() {
		logger.trace("getting all for class " + clazz.getName());
		return (List<T>) getSession().createCriteria(clazz)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@SuppressWarnings("unchecked")
	public List<T> findByNamedQuery(String queryName, Object[] values) {
		logger.trace("find by query: " + queryName+ " for class " + clazz.getName() + " param count " + values.length);
		Query q = getSession().getNamedQuery(queryName);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				q.setParameter(i, values[i]);
			}
		}

		return (List<T>) q.list();
	}

	public ID saveOrUpdate(T entity) {
		Session session = getSession();
		ID ret;
		logger.trace("saving object with ID: " +entity.getId()+ " for class " + clazz.getName());
		// dealing with the caching while using the hibernate session in view
		// filter
		if (entity.getId() != null && this.get(entity.getId()) != null) {
			session.merge(entity);
			ret=entity.getId();
		} else {
			//session.saveOrUpdate(entity);
			ret=(ID)session.save(entity);
		}
		logger.trace("done saving object with ID: " +entity.getId()+ " for class " + clazz.getName());
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedQuery(String queryName, Object[] values,
			int maxResults) {
		logger.trace("find by query: " + queryName+ " for class " + clazz.getName() + " param count " + values.length + " max results " + maxResults);
		Query q = getSession().getNamedQuery(queryName);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				q.setParameter(i, values[i]);
			}
		}
		q.setMaxResults(maxResults);
		return (List<T>) q.list();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
