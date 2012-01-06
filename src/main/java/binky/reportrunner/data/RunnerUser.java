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

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

@Entity
public class RunnerUser implements  UserDetails,Serializable {

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
	
}
