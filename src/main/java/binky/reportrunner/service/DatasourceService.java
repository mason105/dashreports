package binky.reportrunner.service;

import javax.sql.DataSource;

import binky.reportrunner.data.RunnerDataSource;

public interface DatasourceService {
	public DataSource getDataSource(RunnerDataSource runnerDs);
}