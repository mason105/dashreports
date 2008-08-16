package binky.reportrunner.dao;

import java.util.List;

import binky.reportrunner.data.RunnerDataSource;

public interface RunnerDataSourceDao {

	public void addUpdateDataSource(RunnerDataSource dataSource);
	public void deleteDataSource(String dataSourceName);
	public RunnerDataSource getDataSource(String dataSourceName);
	public List<RunnerDataSource> listDataSources(); 
	
}
