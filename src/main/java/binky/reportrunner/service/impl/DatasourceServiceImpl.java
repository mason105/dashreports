/*******************************************************************************
 * Copyright (c) 2009 Daniel Grout.
 * 
 * GNU GENERAL PUBLIC LICENSE - Version 3
 * 
 * This file is part of Report Runner (http://code.google.com/p/reportrunner).
 * 
 * Report Runner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Runner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Runner. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Module: DatasourceServiceImpl.java
 ******************************************************************************/
package binky.reportrunner.service.impl;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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

	public void purgeConnections(String dataSourceName)  throws SQLException {
		ComboPooledDataSource ds = (ComboPooledDataSource)dataSources.get(dataSourceName);
		if (ds != null) {
			dumpLogInfo(ds,dataSourceName);
			//reset the datasource
			ds.hardReset();
		}
	}
	
	private void dumpLogInfo(ComboPooledDataSource ds, String dsName) throws SQLException {
		//dump out a shed load of info about the datasource
		logger.debug("Datasource info for " + dsName );
		Map<String, Object> info = getConnectionInfo(ds, dsName);
		for (String key:info.keySet()) {
			logger.debug("* " + key + ": " + info.get(key));
		}
	}
	
	public Map<String,Object> getConnectionInfo(ComboPooledDataSource ds, String dsName) throws SQLException {
		Map<String, Object> info = new HashMap<String, Object>();
	
		info.put("Num Connections" , ds.getNumConnections());
		info.put("Num Busy Connections" , ds.getNumBusyConnections());
		info.put("Num Idle Connections" , ds.getNumIdleConnections());
		info.put("Num Unclosed Orphaned Connections" , ds.getNumUnclosedOrphanedConnections());
		info.put("Thread Pool Num Active Threads" , ds.getThreadPoolNumActiveThreads());
		info.put("Thread Pool Num Tasks Pending" , ds.getThreadPoolNumTasksPending());
		info.put("Statement Cache Num Connections With Cached Statements All Users" , ds.getStatementCacheNumConnectionsWithCachedStatementsAllUsers());
		info.put("Statement Cache Num Statements All Users" , ds.getStatementCacheNumStatementsAllUsers());
		info.put("Num Helper Threads",ds.getNumHelperThreads());
	
		return info;
	}
	
	public DataSource getJDBCDataSource(RunnerDataSource runnerDs)  throws SQLException {

		DataSource ds = dataSources.get(runnerDs.getDataSourceName());
		if (ds == null) {
			try {
				logger.info("datasource not already loaded for: "
						+ runnerDs.getDataSourceName());
				ds = getDs(runnerDs);
				logger.info("Stored datasource: "
						+ runnerDs.getDataSourceName());
				dataSources.put(runnerDs.getDataSourceName(), ds);
				
				dumpLogInfo((ComboPooledDataSource)ds, runnerDs.getDataSourceName());
				
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

	public void deleteDataSource(String dataSourceName) {
		if (dataSources.get(dataSourceName)!=null) {		
			dataSources.remove(dataSourceName);
		}		
		dataSourceDao.deleteDataSource(dataSourceName);
		
	}

	public RunnerDataSource getDataSource(String dataSourceName) {
		return dataSourceDao.getDataSource(dataSourceName);
	}

	public List<RunnerDataSource> listDataSources() {
		return dataSourceDao.listDataSources();
	}

	public void saveUpdateDataSource(RunnerDataSource dataSource) {
		if (dataSources.get(dataSource.getDataSourceName())!=null) {		
			dataSources.remove(dataSource.getDataSourceName());
		}		
		dataSourceDao.saveUpdateDataSource(dataSource);
		
	}
}
