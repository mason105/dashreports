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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

@Entity(name = "T_DATASOURCE")
@NamedQueries( {
	@NamedQuery(name = "findAllForGroup", query = "from T_DATASOURCE d join d.groups g where g.groupName = ?")
})	
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

	@ManyToMany(fetch=FetchType.EAGER)
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataSourceName == null) ? 0 : dataSourceName.hashCode());
		
		result = prime * result
				+ ((initialPoolSize == null) ? 0 : initialPoolSize.hashCode());
		result = prime * result
				+ ((jdbcClass == null) ? 0 : jdbcClass.hashCode());
		result = prime * result + ((jdbcUrl == null) ? 0 : jdbcUrl.hashCode());
		result = prime * result
				+ ((jndiName == null) ? 0 : jndiName.hashCode());
		result = prime * result
				+ ((maxPoolSize == null) ? 0 : maxPoolSize.hashCode());
		result = prime * result
				+ ((minPoolSize == null) ? 0 : minPoolSize.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RunnerDataSource other = (RunnerDataSource) obj;
		if (dataSourceName == null) {
			if (other.dataSourceName != null)
				return false;
		} else if (!dataSourceName.equals(other.dataSourceName))
			return false;
		if (groups == null) {
			if (other.groups != null)
				return false;
		} else if (!groups.equals(other.groups))
			return false;
		if (initialPoolSize == null) {
			if (other.initialPoolSize != null)
				return false;
		} else if (!initialPoolSize.equals(other.initialPoolSize))
			return false;
		if (jdbcClass == null) {
			if (other.jdbcClass != null)
				return false;
		} else if (!jdbcClass.equals(other.jdbcClass))
			return false;
		if (jdbcUrl == null) {
			if (other.jdbcUrl != null)
				return false;
		} else if (!jdbcUrl.equals(other.jdbcUrl))
			return false;
		if (jndiName == null) {
			if (other.jndiName != null)
				return false;
		} else if (!jndiName.equals(other.jndiName))
			return false;
		if (maxPoolSize == null) {
			if (other.maxPoolSize != null)
				return false;
		} else if (!maxPoolSize.equals(other.maxPoolSize))
			return false;
		if (minPoolSize == null) {
			if (other.minPoolSize != null)
				return false;
		} else if (!minPoolSize.equals(other.minPoolSize))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
}
