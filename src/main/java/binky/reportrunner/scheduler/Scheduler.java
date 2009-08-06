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
 * Module: Scheduler.java
 ******************************************************************************/
package binky.reportrunner.scheduler;

import java.util.Date;
import java.util.List;

public interface Scheduler {
	public static final String dashboardSchedulerGroup="RR3DASHBOARDS";
	public void startScheduler() throws SchedulerException;

	public void stopScheduler() throws SchedulerException;
	
	public Boolean isSchedulerActive()  throws SchedulerException;

	public void addJob(String jobName, String groupName,String cronString,
			Date startDate, Date endDate) throws SchedulerException;
	
	public void removeJob(String jobName, String groupName) throws SchedulerException;
	
	public void pauseJob(String jobName, String groupName) throws SchedulerException;
	
	public void resumeJob(String jobName, String groupName) throws SchedulerException;

	public void invokeJob(String jobName, String groupName) throws SchedulerException;
	
	public Date getNextRunTime(String jobName, String groupName)  throws SchedulerException;
	
	public Date getPreviousRunTime(String jobName, String groupName)  throws SchedulerException;
	
	public Boolean isJobActive(String jobName, String groupName)  throws SchedulerException;
		
	public void interruptRunningJob(String jobName, String groupName)  throws SchedulerException;
	public void interruptRunningDashboardAlert(Integer alertId)  throws SchedulerException;
	
	public List<String> getCurrentRunningJobs();
	
	public Boolean isScheduled(String jobName, String groupName)
	throws SchedulerException;
	
	public void pauseGroup(String groupName) throws SchedulerException;
	public void resumeGroup(String groupName) throws SchedulerException;	

	public void addDashboardAlert(Integer alertId, String cronTab) throws SchedulerException;
	public void removedDashboardAlert(Integer alertId)throws SchedulerException;
}
