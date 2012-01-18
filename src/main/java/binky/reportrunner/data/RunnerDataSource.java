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
 * Module: RunnerDataSource.java
 ******************************************************************************/
package binky.reportrunner.data;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

@Entity(name = "T_DATASOURCE")
@NamedQueries( {
	@NamedQuery(name = "findAllForGroup", query = "from T_DATASOURCE d join d.groups g where g.groupName = ?")
})	
public class RunnerDataSource extends DatabaseObject<String> {

	public String getId() {
		return dataSourceName;
	}

	private static final long serialVersionUID = 7230871954372088509L;

	@Id
	private String dataSourceName;

	public RunnerDataSource() {
	};

	public RunnerDataSource(String dataSourceName, String jndiName,
			String jdbcClass, String jdbcUrl, String username, String password,
			Integer initialPoolSize, Integer minPoolSize, Integer maxPoolSize) {
		super();
		this.dataSourceName = dataSourceName;
		this.jndiName = jndiName;
		this.jdbcClass = jdbcClass;
		this.jdbcUrl = jdbcUrl;
		this.username = username;
		this.password = password;
		this.initialPoolSize = initialPoolSize;
		this.minPoolSize = minPoolSize;
		this.maxPoolSize = maxPoolSize;
	}

	private String jndiName;

	private String jdbcClass;

	private String jdbcUrl;

	private String username;

	private String password;

	private Integer initialPoolSize=3;

	private Integer minPoolSize=2;

	private Integer maxPoolSize=5;

	@ManyToMany
	private List<RunnerGroup> groups;
	
	@RequiredStringValidator
	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJdbcClass() {
		return jdbcClass;
	}

	public void setJdbcClass(String jdbcClass) {
		this.jdbcClass = jdbcClass;
	}

	public String getJndiName() {
		return jndiName;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	public Integer getInitialPoolSize() {
		return initialPoolSize;
	}

	public void setInitialPoolSize(Integer intialPoolSize) {
		this.initialPoolSize = intialPoolSize;
	}

	public Integer getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(Integer minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public Integer getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(Integer maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	@Override
	public String toString() {
		return this.dataSourceName;
	}

	public List<RunnerGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<RunnerGroup> groups) {
		this.groups = groups;
	}
	
}
