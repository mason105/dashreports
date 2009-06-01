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
 * Module: DisplayJob.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.job.beans;


public class DisplayJob {
	
	private String jobName;
	private String groupName;
	private String nextRunTime;
	private String previousRunTime;
	private Boolean isScheduled;
	private Boolean isScheduleActive;
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Boolean getIsScheduled() {
		return isScheduled;
	}

	public void setIsScheduled(Boolean isScheduled) {
		this.isScheduled = isScheduled;
	}

	public Boolean getIsScheduleActive() {
		return isScheduleActive;
	}

	public void setIsScheduleActive(Boolean isScheduleActive) {
		this.isScheduleActive = isScheduleActive;
	}

	public String getNextRunTime() {
		return nextRunTime;
	}

	public void setNextRunTime(String nextRunTime) {
		this.nextRunTime = nextRunTime;
	}

	public String getPreviousRunTime() {
		return previousRunTime;
	}

	public void setPreviousRunTime(String previousRunTime) {
		this.previousRunTime = previousRunTime;
	}

}
