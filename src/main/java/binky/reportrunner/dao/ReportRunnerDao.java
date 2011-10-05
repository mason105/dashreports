package binky.reportrunner.dao;

import java.io.Serializable;
import java.util.List;

public interface ReportRunnerDao<T, ID extends Serializable> {

	public void delete(ID id);
	public T get(ID id);
	public List<T> getAll();
	public List<T> findByNamedQuery(String queryName, Object[] values);
	public void saveOrUpdate(T entity);
	
}
