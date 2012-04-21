package binky.reportrunner.dao;

import java.io.Serializable;
import java.util.List;

import binky.reportrunner.data.DatabaseObject;

/**
 * 
 * Rather nifty Generic DAO, if I do say so myself
 *
 **/
public interface ReportRunnerDao<T extends DatabaseObject<ID>, ID extends Serializable> extends Serializable  {

	public void delete(ID id);
	public T get(ID id);

	public List<T> getAll();
	public List<T> findByNamedQuery(String queryName, Object[] values);
	public List<T> findByNamedQuery(String queryName, Object[] values,int maxResults);
	public ID saveOrUpdate(T entity);
	
}
