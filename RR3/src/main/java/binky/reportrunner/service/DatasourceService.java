package binky.reportrunner.service;

import java.util.List;

import javax.sql.DataSource;

import binky.reportrunner.data.RunnerDataSource;

public interface DatasourceService {
	public DataSource getDataSource(RunnerDataSource runnerDs);
	public List<String> getAvailableDriverNames();
}