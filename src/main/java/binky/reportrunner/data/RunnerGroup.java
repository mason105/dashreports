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
 * Module: RunnerGroup.java
 ******************************************************************************/
package binky.reportrunner.data;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

@Entity(name = "T_GROUP")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RunnerGroup extends DatabaseObject<String> {

	public String getId() {
		return groupName;
	}

	private static final long serialVersionUID = -5727491198024680384L;

	public RunnerGroup(String groupName, String groupDescription,
			List<RunnerJob> runnerJobs) {
		super();
		this.groupName = groupName;
		this.groupDescription = groupDescription;
		this.runnerJobs = runnerJobs;
	}


	public RunnerGroup(String groupName) {
		this.groupName=groupName;
	};
	public RunnerGroup() {
	};

	@Id
	private String groupName;
	private String groupDescription;

	
	@OneToMany
	private Collection<RunnerDashboardItem> dashboardItems;

	@OneToMany()
	private Collection<RunnerJob> runnerJobs;

	@RequiredStringValidator
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public Collection<RunnerJob> getRunnerJobs() {
		return runnerJobs;
	}

	public void setRunnerJobs(Collection<RunnerJob> runnerJobs) {
		this.runnerJobs = runnerJobs;
	}

	public String toString() {
		return this.groupName;
	}

	public Collection<RunnerDashboardItem> getDashboardItems() {
		return dashboardItems;
	}

	public void setDashboardItems(Collection<RunnerDashboardItem> dashboardItems) {
		this.dashboardItems = dashboardItems;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dashboardItems == null) ? 0 : dashboardItems.hashCode());
		result = prime
				* result
				+ ((groupDescription == null) ? 0 : groupDescription.hashCode());
		result = prime * result
				+ ((groupName == null) ? 0 : groupName.hashCode());
		result = prime * result
				+ ((runnerJobs == null) ? 0 : runnerJobs.hashCode());
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
		RunnerGroup other = (RunnerGroup) obj;
		if (dashboardItems == null) {
			if (other.dashboardItems != null)
				return false;
		} else if (!dashboardItems.equals(other.dashboardItems))
			return false;
		if (groupDescription == null) {
			if (other.groupDescription != null)
				return false;
		} else if (!groupDescription.equals(other.groupDescription))
			return false;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		if (runnerJobs == null) {
			if (other.runnerJobs != null)
				return false;
		} else if (!runnerJobs.equals(other.runnerJobs))
			return false;
		return true;
	}

}
