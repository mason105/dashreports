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
 * Module: DatasourceService.java
 ******************************************************************************/
package binky.reportrunner.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.xml.sax.SAXException;

import binky.dan.utils.encryption.EncryptionException;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.service.misc.JDBCDrivers;

public interface DatasourceService extends Auditable {

	public DataSource getJDBCDataSource(RunnerDataSource runnerDs)
			throws SQLException;

	public void saveUpdateDataSource(RunnerDataSource dataSource) throws EncryptionException;

	public void deleteDataSource(String dataSourceName);

	public RunnerDataSource getDataSource(String dataSourceName);

	public List<RunnerDataSource> listDataSources();
	public List<RunnerDataSource> getDataSourcesForGroup(String groupName);
	public void purgeConnections(String dataSourceName) throws SQLException;
	public String testDataSource(RunnerDataSource runnerDs) ;
	public JDBCDrivers getJDBCDriverDefinitions() throws IOException, SAXException;
	
	public void reEncryptPasswords(String newKey) throws EncryptionException;
	public void setSecureKey(String secureKey);

}
