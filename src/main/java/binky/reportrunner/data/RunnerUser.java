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
 * Module: RunnerUser.java
 ******************************************************************************/
package binky.reportrunner.data;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

@Entity(name = "T_USER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RunnerUser extends DatabaseObject<String> implements  UserDetails {

	public String getId() {
		return userName;
	}


	private static final long serialVersionUID = 8376600609942516518L;

	@Id
	private String userName;

	private String password;

	private String fullName;

	private Boolean isAdmin = false;

	private Boolean isLocked = false;
	
	private Boolean isReadOnly = false;

	public Boolean getIsReadOnly() {
		return isReadOnly;
	}

	public void setIsReadOnly(Boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	public RunnerUser() {
	}

	public RunnerUser(String userName, String password, String fullName,
			Boolean isAdmin, List<RunnerGroup> groups) {
		super();
		this.userName = userName;
		this.password = password;
		this.fullName = fullName;

		if (isAdmin == null) {
			this.isAdmin = false;
		} else {
			this.isAdmin = isAdmin;
		}
		this.groups = groups;
		this.isLocked = false;
	}

	public Boolean getIsAdmin() {
		return isAdmin==null?false:isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	private List<RunnerGroup> groups;

	@RequiredStringValidator
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@RequiredStringValidator
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public List<RunnerGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<RunnerGroup> groups) {
		this.groups = groups;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}
	public Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new LinkedList<GrantedAuthority>();
 			if (this.isAdmin) {
				authorities.add(new GrantedAuthorityImpl("ROLE_ADMIN"));				
			}
 			authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
 			return authorities;
	}
	public boolean isAccountNonExpired() {
		return  !isLocked;
	}
	public boolean isAccountNonLocked() {		// 
		return !isLocked;
	}
	public boolean isCredentialsNonExpired() {
		return  !isLocked;
	}
	public boolean isEnabled() {
		return !isLocked;
	}

	@Override
	public String getUsername() {
		return getUserName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fullName == null) ? 0 : fullName.hashCode());

		result = prime * result + ((isAdmin == null) ? 0 : isAdmin.hashCode());
		result = prime * result
				+ ((isLocked == null) ? 0 : isLocked.hashCode());
		result = prime * result
				+ ((isReadOnly == null) ? 0 : isReadOnly.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
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
		RunnerUser other = (RunnerUser) obj;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if (groups == null) {
			if (other.groups != null)
				return false;
		} else if (!groups.equals(other.groups))
			return false;
		if (isAdmin == null) {
			if (other.isAdmin != null)
				return false;
		} else if (!isAdmin.equals(other.isAdmin))
			return false;
		if (isLocked == null) {
			if (other.isLocked != null)
				return false;
		} else if (!isLocked.equals(other.isLocked))
			return false;
		if (isReadOnly == null) {
			if (other.isReadOnly != null)
				return false;
		} else if (!isReadOnly.equals(other.isReadOnly))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
}
