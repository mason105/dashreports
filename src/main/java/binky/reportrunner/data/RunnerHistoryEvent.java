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

@Entity
@NamedQueries( {
		@NamedQuery(name = "getLatestForApplicationMetric", query = "from T_DATA d where d.application.applicationName = ? and d.metric.id.metricName=? and d.isLatest=true"),
		@NamedQuery(name = "getHistoryForApplicationMetric", query = "from T_DATA d where d.application.applicationName = ? and d.metric.id.metricName=? and d.isLatest=false order by d.date desc") })
public class RunnerHistoryEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long eventId;
	private Date timestamp;
	private String jobName;
	private String groupName;
	private String message;
	private Boolean success;
	private Long runTime;
	private String userName;
	private String module;

	public Long getRunTime() {
		return runTime;
	}

	public void setRunTime(Long runTime) {
		this.runTime = runTime;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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

	public String toString() {
		StringBuilder ret = new StringBuilder();

		ret.append("Job Name=");
		ret.append(jobName);
		ret.append(" ");
		ret.append("Group Name=");
		ret.append(groupName);
		ret.append(" ");
		ret.append("Timestamp=");
		ret.append(timestamp);
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

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

}
