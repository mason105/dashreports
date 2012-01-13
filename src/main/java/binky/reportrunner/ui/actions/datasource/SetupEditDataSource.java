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
 * Module: SetupEditDataSource.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.datasource;

import java.util.Collection;

import org.springframework.security.access.prepost.PreAuthorize;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.service.JDBCDriverDefinition;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

import com.opensymphony.xwork2.Preparable;

public class SetupEditDataSource extends StandardRunnerAction implements Preparable{

	private static final long serialVersionUID = 1L;
	private String dataSourceName;
	private RunnerDataSource dataSource;
	private Collection<JDBCDriverDefinition> drivers;
	private DatasourceService dataSourceService;
	private String currentDriver;
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String execute() throws Exception {	
		
		if ((dataSourceName !=null) && (!dataSourceName.isEmpty())){
			dataSource=dataSourceDao.get(dataSourceName);
			dataSource.setPassword(null);			
			//hacky as hacky mchackerson of the clan mchackerson
			for (JDBCDriverDefinition def : dataSourceService.getJDBCDriverDefinitions().getDefinitions().values()) {
				if (def.getDriverName().equalsIgnoreCase(dataSource.getJdbcClass())) {
					this.currentDriver=def.getLabel();
				}
			}
			
		} else {
			dataSource=new RunnerDataSource();
		}
		this.drivers=dataSourceService.getJDBCDriverDefinitions().getDefinitions().values();
		return SUCCESS;
	}
	
	@Override
	public void prepare() throws Exception {

		this.drivers=dataSourceService.getJDBCDriverDefinitions().getDefinitions().values();
		
	}
	
	
	private  ReportRunnerDao<RunnerDataSource,String> dataSourceDao;

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
	public RunnerDataSource getDataSource() {
		return dataSource;
	}
	public void setDataSourceDao( ReportRunnerDao<RunnerDataSource,String> dataSourceDao) {
		this.dataSourceDao = dataSourceDao;
	}

	public Collection<JDBCDriverDefinition> getDrivers() {
		return drivers;
	}

	public void setDrivers(Collection<JDBCDriverDefinition> drivers) {
		this.drivers = drivers;
	}

	public void setDataSourceService(DatasourceService dataSourceService) {
		this.dataSourceService = dataSourceService;
	}

	public String getCurrentDriver() {
		return currentDriver;
	}
	

}
