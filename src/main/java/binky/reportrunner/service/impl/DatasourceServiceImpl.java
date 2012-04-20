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
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import binky.dan.utils.encryption.EncryptionException;
import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.service.misc.JDBCDriverDefinition;
import binky.reportrunner.service.misc.JDBCDrivers;
import binky.reportrunner.util.EncryptionUtil;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;

public class DatasourceServiceImpl implements DatasourceService {

	private ReportRunnerDao<RunnerDataSource, String> dataSourceDao;
	private ReportRunnerDao<RunnerGroup, String> groupDao;
	private Map<String, DataSource> dataSources = new HashMap<String, DataSource>();;
	private String secureKey;

	private Logger logger = Logger.getLogger(DatasourceServiceImpl.class);

	private DataSource getDs(RunnerDataSource runnerDs)
			throws SecurityException, InstantiationException,
			IllegalAccessException, ClassNotFoundException,
			PropertyVetoException, NamingException, EncryptionException {

		final String jndiDataSource = runnerDs.getJndiName();

		if (StringUtils.isBlank(jndiDataSource)) {
			EncryptionUtil enc = new EncryptionUtil();
			logger.info("using dbcp pooled connection for: "
					+ runnerDs.getDataSourceName());

			String jdbcUser = runnerDs.getUsername();
			if (StringUtils.isBlank(runnerDs.getPassword()))
				throw new SecurityException("password is empty");
			String jdbcPassword = enc
					.decrpyt(secureKey, runnerDs.getPassword());

			String jdbcUrl = runnerDs.getJdbcUrl();
			String databaseDriver = runnerDs.getJdbcClass();

			Class.forName(databaseDriver).newInstance();

			BasicDataSource ds1 = new BasicDataSource();
			ds1.setDriverClassName(databaseDriver);
			ds1.setUrl(jdbcUrl);
			ds1.setUsername(jdbcUser);
			ds1.setPassword(jdbcPassword);
			ds1.setInitialSize(runnerDs.getInitialPoolSize());
			ds1.setMaxActive(runnerDs.getMaxPoolSize());

			ds1.setRemoveAbandoned(true);
			ds1.setRemoveAbandonedTimeout(600);

			// do not want anything updating anything
			ds1.setDefaultReadOnly(true);

			ds1.setLogAbandoned(true);
			ds1.setTestOnBorrow(true);
			ds1.setTestOnReturn(true);
			ds1.setTestWhileIdle(true);

			// does this work across all RBMS?
			ds1.setValidationQuery("select 1");
			ds1.setValidationQueryTimeout(300);

			return ds1;
		} else {
			logger.info("getting datasource from JNDI url: " + jndiDataSource
					+ " for " + runnerDs.getDataSourceName());
			Context initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/"
					+ jndiDataSource);
			return ds;
		}
	}

	public void purgeConnections(String dataSourceName) throws SQLException {
		DataSource ds = dataSources.get(dataSourceName);
		if (ds != null && ds instanceof BasicDataSource) {
			dumpLogInfo(dataSourceName);
			// reset the datasource
			((BasicDataSource) ds).close();
		}
	}

	private void dumpLogInfo(String dsName) throws SQLException {
		// dump out a shed load of info about the datasource
		logger.debug("Datasource info for " + dsName);
	}

	public String testDataSource(RunnerDataSource runnerDs) {

		try {
			if (StringUtils.isBlank(runnerDs.getPassword())) {
				// see if ds already exists but we are hiding the password
				RunnerDataSource pwget = this.dataSourceDao.get(runnerDs
						.getDataSourceName());
				if (pwget != null) {
					logger.debug("supplied password was blank - using stored password (if any)");
					runnerDs.setPassword(pwget.getPassword());
				}
			} else {
				EncryptionUtil enc = new EncryptionUtil();
				runnerDs.setPassword(enc.encrpyt(this.secureKey,
						runnerDs.getPassword()));
			}

			DataSource ds = this.getDs(runnerDs);
			Connection conn = ds.getConnection();
			DatabaseMetaData meta = conn.getMetaData();
			String information = meta.getDatabaseProductName() + ", "
					+ meta.getDatabaseProductVersion();
			conn.close();
			if (ds instanceof BasicDataSource) {
				((BasicDataSource) ds).close();
			}
			return information;
		} catch (Exception e) {
			if (e instanceof NullPointerException) {
				logger.fatal(e.getMessage(), e);
			}
			logger.debug(e.getMessage());
			return "ERROR - " + e.getClass().getSimpleName() + ": "
					+ e.getMessage();
		}
	}

	public DataSource getJDBCDataSource(RunnerDataSource runnerDs)
			throws SQLException {
		if (dataSources == null)
			dataSources = new HashMap<String, DataSource>();
		DataSource ds = dataSources.get(runnerDs.getDataSourceName());
		if (ds == null) {
			try {
				logger.info("datasource not already loaded for: "
						+ runnerDs.getDataSourceName());
				ds = getDs(runnerDs);
				logger.info("Stored datasource: "
						+ runnerDs.getDataSourceName());
				dataSources.put(runnerDs.getDataSourceName(), ds);

				dumpLogInfo(runnerDs.getDataSourceName());

			} catch (Exception e) {
				logger.fatal(
						"Unable to create datasource: "
								+ runnerDs.getDataSourceName(), e);
			}
		}

		return ds;
	}

	public void setDataSourceDao(
			ReportRunnerDao<RunnerDataSource, String> dataSourceDao) {
		this.dataSourceDao = dataSourceDao;
	}

	@TriggersRemove(cacheName = "dataSourceCache")
	public void deleteDataSource(String dataSourceName) {
		if (dataSources.get(dataSourceName) != null) {
			dataSources.remove(dataSourceName);
		}
		dataSourceDao.delete(dataSourceName);

	}

	public RunnerDataSource getDataSource(String dataSourceName) {
		RunnerDataSource ds = dataSourceDao.get(dataSourceName);
		return ds;
	}
	@Cacheable(cacheName = "dataSourceCache")
	public List<RunnerDataSource> listDataSources() {
		return dataSourceDao.getAll();
	}
	
	
	@TriggersRemove(cacheName="dataSourceCache", removeAll=true)
	public void saveUpdateDataSource(RunnerDataSource dataSource)
			throws EncryptionException {
		if (dataSources.get(dataSource.getDataSourceName()) != null) {
			dataSources.remove(dataSource.getDataSourceName());
		}
		EncryptionUtil enc = new EncryptionUtil();
		if (StringUtils.isNotBlank(dataSource.getPassword())) {
			dataSource.setPassword(enc.encrpyt(secureKey,
					dataSource.getPassword()));
		} else {
			RunnerDataSource ds = dataSourceDao.get(dataSource
					.getDataSourceName());
			if (ds != null)
				dataSource.setPassword(ds.getPassword());
		}

		dataSourceDao.saveOrUpdate(dataSource);
	}

	public void setSecureKey(String secureKey) {
		this.secureKey = secureKey;
	}

	public JDBCDrivers getJDBCDriverDefinitions() throws IOException,
			SAXException {

		InputStream in = DatasourceServiceImpl.class
				.getResourceAsStream("/jdbcDrivers.xml");

		Digester digester = new Digester();
		digester.setValidating(false);
		digester.addObjectCreate("jdbcDrivers", JDBCDrivers.class);
		digester.addObjectCreate("jdbcDrivers/driver",
				JDBCDriverDefinition.class);
		digester.addBeanPropertySetter("jdbcDrivers/driver/label", "label");
		digester.addBeanPropertySetter("jdbcDrivers/driver/url", "url");
		digester.addBeanPropertySetter("jdbcDrivers/driver/driverName",
				"driverName");
		digester.addSetNext("jdbcDrivers/driver", "addDefinition");

		JDBCDrivers drivers = (JDBCDrivers) digester.parse(in);

		return drivers;
	}

	@Override
	public List<RunnerDataSource> getDataSourcesForGroup(String groupName) {
		logger.debug("getting datasources for group: " + groupName);
		// List<RunnerDataSource> dsList =
		// groupDao.get(groupName).getDataSources();

		// some crazy ass shit going down here - the join returns an array for
		// each row that would be returned by the db
		// if i try prefixing the names query with select d (which makes sense
		// to me) then it won't run
		List<RunnerDataSource> dsList = new LinkedList<RunnerDataSource>();
		Object holder = dataSourceDao.findByNamedQuery("findAllForGroup",
				new String[] { groupName });
		for (Object[] o : (ArrayList<Object[]>) holder) {
			RunnerDataSource d = (RunnerDataSource) o[0];
			logger.debug(d.getDataSourceName());
			dsList.add(d);
		}
		logger.debug("got : " + dsList.size() + " data sources for group: "
				+ groupName);
		return dsList;
	}

	public ReportRunnerDao<RunnerGroup, String> getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(ReportRunnerDao<RunnerGroup, String> groupDao) {
		this.groupDao = groupDao;
	}

	@Override
	public void reEncryptPasswords(String newKey) throws EncryptionException {
		EncryptionUtil enc = new EncryptionUtil();

		logger.warn("re-encrypting the datasource passwords.  I hope you copied the key from the UI as instructed!!");

		for (RunnerDataSource ds : dataSourceDao.getAll()) {
			if (!StringUtils.isEmpty(ds.getPassword())) {
				String pw = enc.decrpyt(secureKey, ds.getPassword());
				ds.setPassword(enc.encrpyt(newKey, pw));
				dataSourceDao.saveOrUpdate(ds);
				logger.debug("updated ds: " + ds.getDataSourceName());
			}
		}

		logger.warn("re-encryption complete.  Please ensure you update the properties file with the new key and restart the server");
	}



}
