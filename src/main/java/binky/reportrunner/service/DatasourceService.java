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

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.sql.DataSource;

import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.service.impl.DatasourceServiceImpl.JDBCDriverDefinition;

public interface DatasourceService {

	public DataSource getJDBCDataSource(RunnerDataSource runnerDs)
			throws SQLException;

	public void saveUpdateDataSource(RunnerDataSource dataSource) throws SecurityException,InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException;

	public void deleteDataSource(String dataSourceName);

	public RunnerDataSource getDataSource(String dataSourceName);

	public List<RunnerDataSource> listDataSources();

	public void purgeConnections(String dataSourceName) throws SQLException;
	public String testDataSource(RunnerDataSource runnerDs) ;
	public List<JDBCDriverDefinition> getJDBCDriverDefinitions();
}
