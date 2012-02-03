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
import java.util.LinkedList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.service.GroupService;
import binky.reportrunner.service.misc.JDBCDriverDefinition;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

import com.opensymphony.xwork2.Preparable;

public class SetupEditDataSource extends StandardRunnerAction implements Preparable{

	private static final long serialVersionUID = 1L;
	private String dataSourceName;
	private RunnerDataSource dataSource;
	private Collection<JDBCDriverDefinition> drivers;
	private DatasourceService dataSourceService;
	private String currentDriver;
	private List<String> dataSourceGroups;
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String execute() throws Exception {	
		this.dataSourceGroups=new LinkedList<String>();
		if ((dataSourceName !=null) && (!dataSourceName.isEmpty())){
			dataSource=dataSourceService.getDataSource(dataSourceName);
			if (dataSource!=null) {
				dataSource.setPassword(null);			
				//hacky as hacky mchackerson of the clan mchackerson
				for (JDBCDriverDefinition def : dataSourceService.getJDBCDriverDefinitions().getDefinitions().values()) {
					if (def.getDriverName().equalsIgnoreCase(dataSource.getJdbcClass())) {
						this.currentDriver=def.getLabel();
					}
				}
				
				for (RunnerGroup g: dataSource.getGroups()) {
					this.dataSourceGroups.add(g.getGroupName());
				}
				
			} else {
				dataSource=new RunnerDataSource();	
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
		this.groups = groupService.getAll();
	}
	private List<RunnerGroup> groups;
	private GroupService groupService;
	

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
	public RunnerDataSource getDataSource() {
		return dataSource;
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

	public List<RunnerGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<RunnerGroup> groups) {
		this.groups = groups;
	}



	public List<String> getDataSourceGroups() {
		return dataSourceGroups;
	}

	public void setDataSourceGroups(List<String> dataSourceGroups) {
		this.dataSourceGroups = dataSourceGroups;
	}
	

}
