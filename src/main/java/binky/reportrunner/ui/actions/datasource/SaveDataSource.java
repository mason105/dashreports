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

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

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
	private boolean jndi;
	private RunnerDataSource dataSource;
	private Collection<JDBCDriverDefinition> drivers;
	private List<String> dataSourceGroups;
	private List<String> jndiNames;
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String execute() throws Exception {
		populateJNDINames();
		if (StringUtils.isBlank(dataSource.getDataSourceName())) {
			super.addActionError("Please complete the datasource name field");
			if (jndi) {
				return INPUT+"JNDI";
			} else {
				return INPUT;
			}
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
			if (jndi) {
				return INPUT+"JNDI";
			} else {
				return INPUT;
			}
		}
		return SUCCESS;
	}
	
	private void populateJNDINames() throws NamingException {
		String ident="";
		Context ctx = (Context)new InitialContext().lookup("java:comp/env");
		this.jndiNames=this.listJNDINames(ctx,ident);		
	}
	
	//http://denistek.blogspot.com/2008/08/list-jndi-names.html
	private List<String> listJNDINames(Context ctx,String ident) throws NamingException {
		List<String> names = new LinkedList<String>();
	
		 NamingEnumeration<Binding> list = ctx.listBindings("");
		   while (list.hasMore()) {
		       Binding item = (Binding) list.next();
		  
		       String name = item.getName();		  
     
		       Object o = item.getObject();
		       if (o instanceof javax.naming.Context) {
		    	   names.addAll(listJNDINames((Context) o, name));
		       } else {
		    	   names.add(ident+"/" +name);   
		       }
		   }
		
		return names;
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
	public void setJndi(boolean jndi) {
		this.jndi = jndi;
	}

	public List<String> getJndiNames() {
		return jndiNames;
	}

}
