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

import java.util.LinkedList;
import java.util.List;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

import com.opensymphony.xwork2.Preparable;

public class SetupEditJNDIDataSource extends StandardRunnerAction implements Preparable{

	private static final long serialVersionUID = 1L;
	private String dataSourceName;
	private RunnerDataSource dataSource;
	private DatasourceService dataSourceService;
	private List<String> dataSourceGroups;
	private List<String> jndiNames;
	private static final Logger logger = Logger.getLogger(SetupEditJNDIDataSource.class);
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String execute() throws Exception {
		this.dataSourceGroups=new LinkedList<String>();
		if ((dataSourceName !=null) && (!dataSourceName.isEmpty())){
			dataSource=dataSourceService.getDataSource(dataSourceName);
		
			if (dataSource!=null) {
				for (RunnerGroup g: dataSource.getGroups()) {
					this.dataSourceGroups.add(g.getGroupName());
				}
				
			} else {
				dataSource=new RunnerDataSource();	
			}
		} else {
			dataSource=new RunnerDataSource();
		}
		
		populateJNDINames();
		
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
		
		String indent="";
		
		 NamingEnumeration<Binding> list = ctx.listBindings("");
		   while (list.hasMore()) {
		       Binding item = (Binding) list.next();
		       String className = item.getClassName();
		       String name = item.getName();		  
		       logger.debug(indent + className + " " + name);		       
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
		populateJNDINames();
		
		this.groups = groupDao.getAll();
	}
	private List<RunnerGroup> groups;
	private ReportRunnerDao<RunnerGroup,String> groupDao;
	
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
	public RunnerDataSource getDataSource() {
		return dataSource;
	}

	public List<String> getJndiNames() {
		return jndiNames;
	}
	public void setJndiNames(List<String> jndiNames) {
		this.jndiNames = jndiNames;
	}

	public List<RunnerGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<RunnerGroup> groups) {
		this.groups = groups;
	}

	public ReportRunnerDao<RunnerGroup, String> getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(ReportRunnerDao<RunnerGroup, String> groupDao) {
		this.groupDao = groupDao;
	}

	public List<String> getDataSourceGroups() {
		return dataSourceGroups;
	}

	public void setDataSourceGroups(List<String> dataSourceGroups) {
		this.dataSourceGroups = dataSourceGroups;
	}

	public DatasourceService getDataSourceService() {
		return dataSourceService;
	}

	public void setDataSourceService(DatasourceService dataSourceService) {
		this.dataSourceService = dataSourceService;
	}

	

}
