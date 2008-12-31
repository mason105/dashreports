package binky.reportrunner.service.impl;

import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.RunnerDataSourceDao;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.service.DatasourceService;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DatasourceServiceImpl implements DatasourceService {

	/**
	 * @return a connection to the datasource for the job
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws NamingException
	 */
	private RunnerDataSourceDao dataSourceDao;
	private Map<String, DataSource> dataSources = new HashMap<String, DataSource>();;

	private Logger logger = Logger.getLogger(DatasourceServiceImpl.class);

	private DataSource getDs(RunnerDataSource runnerDs)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, PropertyVetoException, NamingException {
		final String jndiDataSource = runnerDs.getJndiName();
		if ((jndiDataSource == null)||jndiDataSource.isEmpty()) {
			logger.info("using c3p0 pooled connection for: "
					+ runnerDs.getDataSourceName());
			String jdbcUser = runnerDs.getUsername();
			String jdbcPassword = runnerDs.getPassword();
			String jdbcUrl = runnerDs.getJdbcUrl();
			String databaseDriver = runnerDs.getJdbcClass();

			Class.forName(databaseDriver).newInstance();

			ComboPooledDataSource ds = new ComboPooledDataSource();
			ds.setDriverClass(databaseDriver);
			ds.setJdbcUrl(jdbcUrl);
			ds.setUser(jdbcUser);
			ds.setPassword(jdbcPassword);
			ds.setInitialPoolSize(runnerDs.getInitialPoolSize());
			ds.setMaxPoolSize(runnerDs.getMaxPoolSize());
			ds.setMinPoolSize(runnerDs.getMinPoolSize());
			ds.setDescription(runnerDs.getDataSourceName());
			ds.setIdleConnectionTestPeriod(600);
			ds.setNumHelperThreads(5);
			ds.setMaxStatements(5);			
			return ds;
		} else {
			logger.info("getting datasource from JNDI url: " + jndiDataSource
					+ " for " + runnerDs.getDataSourceName());
			Context initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/"
					+ jndiDataSource);
			return ds;
		}
	}

	public DataSource getDataSource(RunnerDataSource runnerDs) {

		DataSource ds = dataSources.get(runnerDs.getDataSourceName());
		if (ds == null) {
			try {
				logger.info("datasource not already loaded for: "
						+ runnerDs.getDataSourceName());
				ds = getDs(runnerDs);
				logger.info("Stored datasource: "
						+ runnerDs.getDataSourceName());
				dataSources.put(runnerDs.getDataSourceName(), ds);
			} catch (Exception e) {
				logger.fatal("Unable to create datasource: "
						+ runnerDs.getDataSourceName(), e);
			}
		}
		return ds;
	}

	public RunnerDataSourceDao getDataSourceDao() {
		return dataSourceDao;
	}

	public void setDataSourceDao(RunnerDataSourceDao dataSourceDao) {
		this.dataSourceDao = dataSourceDao;
	}
}
