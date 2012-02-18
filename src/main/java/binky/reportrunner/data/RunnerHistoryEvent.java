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
@NamedQueries({
		@NamedQuery(name = "getFailedEvents", query = "from T_EVENT e where e.module = ? and timeStamp > ? and timeStamp < ? and e.success=false order by timeStamp desc"),
		@NamedQuery(name = "getLongestRunningEvents", query = "from T_EVENT e where e.module = ?  and timeStamp > ? and timeStamp < ? order by runTime desc"),
		@NamedQuery(name = "getSuccessEvents", query = "from T_EVENT e where e.module = ?  and timeStamp > ? and timeStamp < ? and e.success=true order by timeStamp desc"),
		@NamedQuery(name = "getOldEvents", query = "from T_EVENT e where e.timeStamp < ?") })
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RunnerHistoryEvent extends DatabaseObject<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8583408359341993933L;

	public Long getId() {
		return eventId;
	}

	public RunnerHistoryEvent() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long eventId;
	private Date timeStamp;
	private boolean success;
	private long runTime;
	private String userName;
	private String module;
	private String arguments;
	private String method;
	private String errorText;

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

		ret.append("Arguments=");
		ret.append(arguments);
		ret.append(" ");
		ret.append("Timestamp=");
		ret.append(timeStamp);
		ret.append(" ");
		ret.append("Elapsed Time=");
		ret.append(runTime);
		ret.append(" ");
		ret.append("Method=");
		ret.append(method);
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

	public String getArguments() {
		return arguments;
	}

	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public RunnerHistoryEvent(Date timeStamp, boolean success, long runTime,
			String userName, String module, String arguments, String method,String errorText) {
		super();
		this.timeStamp = timeStamp;
		this.success = success;
		this.runTime = runTime;
		this.userName = userName;
		this.module = module;
		this.arguments = arguments;
		this.method = method;
		this.errorText=errorText;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

}
