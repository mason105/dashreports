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
 * Module: RunnerHistoryEvent.java
 ******************************************************************************/
package binky.reportrunner.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "T_EVENT")
@NamedQueries( {
		@NamedQuery(name = "getEventsByJob", query = "from T_EVENT e where e.jobName = ? and e.groupName = ?"),		
		@NamedQuery(name = "getEventsByModule", query = "from T_EVENT e where e.module = ?"),
		@NamedQuery(name = "getEventsByUserName", query = "from T_EVENT e where e.userName = ?"),
		@NamedQuery(name = "getFailedEvents", query = "from T_EVENT e where e.module = ? and e.success=false order by timeStamp desc"),
		@NamedQuery(name = "getLongestRunningEvents", query = "from T_EVENT e where e.module = ? order by runTime desc"),
		@NamedQuery(name = "getSuccessEvents", query = "from T_EVENT e where e.module = ? and e.success=true order by timeStamp desc"),
		@NamedQuery(name = "getOldEvents", query = "from T_EVENT e where e.timeStamp < ?")		
})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RunnerHistoryEvent extends DatabaseObject<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8583408359341993933L;

	public Long getId() {
		return eventId;
	}

	public RunnerHistoryEvent() {}
	
	public enum Module {

		REPORT_VIEWER("Report Viewer"),DASHBOARD_SCHEDULER("Dashboard Scheduler"),SECURITY("Security"),REPORT_SCHEDULER("Report Scheduler"),DATASOURCE_SERVICE("Datasource Service"),CORE_SCHEDULER("Core Scheduler");
		private String displayName;

		Module(String displayName) {
			this.displayName = displayName;
		}

		public String getName() {
			return name();
		}

		public String getDisplayName() {
			return displayName;
		}

	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long eventId;
	private Date timeStamp;
	private String jobName;
	private String groupName;
	private String message;
	private boolean success;
	private long runTime;
	private String userName;
	private Module module;


	
	
	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public long getRunTime() {
		return runTime;
	}

	public void setRunTime(long runTime) {
		this.runTime = runTime;
	}

	public String toString() {
		StringBuilder ret = new StringBuilder();

		ret.append("Job Name=");
		ret.append(jobName);
		ret.append(" ");
		ret.append("Group Name=");
		ret.append(groupName);
		ret.append(" ");
		ret.append("Timestamp=");
		ret.append(timeStamp);
		ret.append(" ");
		ret.append("Elapsed Time=");
		ret.append(runTime);
		ret.append(" ");
		ret.append("Message=");
		ret.append(message);
		ret.append(" ");
		ret.append("User Name=");
		ret.append(userName);
		ret.append(" ");
		ret.append("Module=");
		ret.append(module);
		return ret.toString();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public RunnerHistoryEvent(Date timestamp, String jobName,
			String groupName, String message, Boolean success, Long runTime,
			String userName, Module module) {		
		this.timeStamp = timestamp;
		this.jobName = jobName;
		this.groupName = groupName;
		this.message = message;
		this.success = success;
		this.runTime = runTime;
		this.userName = userName;
		this.module = module;
	}

	
	
}
