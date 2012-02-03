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
 * Module: SaveDataSource.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.datasource;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;

import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.service.GroupService;
import binky.reportrunner.service.misc.JDBCDriverDefinition;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

import com.opensymphony.xwork2.Preparable;

public class SaveDataSource extends StandardRunnerAction implements Preparable {

	private static final long serialVersionUID = 1L;

	private RunnerDataSource dataSource;
	private Collection<JDBCDriverDefinition> drivers;
	private List<String> dataSourceGroups;
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String execute() throws Exception {
		
		if (StringUtils.isBlank(dataSource.getDataSourceName())) {
			super.addActionError("Please complete the datasource name field");
			return INPUT;
		}
		
		try {
			
			if (dataSourceGroups!=null) {
				List<RunnerGroup> groups = new LinkedList<RunnerGroup>();
				for (String g: dataSourceGroups) {
					RunnerGroup group = groupService.getGroup(g);
					groups.add(group);
				}
				dataSource.setGroups(groups);
			}
			
			dataSourceService.saveUpdateDataSource(dataSource);
		} catch (Exception e) {
			super.addActionError(e.getMessage());
			return INPUT;
		}
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

	private DatasourceService dataSourceService;



	public DatasourceService getDataSourceService() {
		return dataSourceService;
	}

	public void setDataSourceService(DatasourceService dataSourceService) {
		this.dataSourceService = dataSourceService;
	}

	public RunnerDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(RunnerDataSource dataSource) {
		this.dataSource = dataSource;
	}
	public Collection<JDBCDriverDefinition> getDrivers() {
		return drivers;
	}
	public void setDrivers(Collection<JDBCDriverDefinition> drivers) {
		this.drivers = drivers;
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
