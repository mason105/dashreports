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
 * Module: RunnerJobService.java
 ******************************************************************************/
package binky.reportrunner.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.engine.ViewerResults;
import binky.reportrunner.exceptions.RenderException;
import binky.reportrunner.scheduler.SchedulerException;

public interface RunnerJobService {
	
	public void addUpdateJob(RunnerJob job) throws SchedulerException;
	public void deleteJob(String jobName, String groupName) throws SchedulerException;
	public List<RunnerJob> listJobs(String groupName);
	public RunnerJob getJob(String jobName, String groupName);
	public void pauseJob(String jobName, String groupName) throws SchedulerException;
	public void resumeJob(String jobName, String groupName) throws SchedulerException;	
	public void pauseGroup(String groupName) throws SchedulerException;
	public void resumeGroup(String groupName) throws SchedulerException;	
	
	public Boolean isJobActive(String jobName, String groupName) throws SchedulerException;
	
	public List<RunnerJob> getRunningJobs() throws SchedulerException;
	public void interruptRunningJob(String jobName, String groupName)
			throws SchedulerException;

	public void invokeJob(String jobName, String groupName) throws SchedulerException;
	public Date getNextRunTime(String jobName, String groupName) throws SchedulerException;
	public Date getPreviousRunTime(String jobName, String groupName) throws SchedulerException;
	
	public Map<String, ViewerResults> getResultsForJob(String jobName, String groupName, List<RunnerJobParameter> parameters) throws SQLException, NumberFormatException, ParseException,RenderException, IOException;
	public Map<String, ViewerResults> getResultsForJob(String jobName, String groupName) throws SQLException, NumberFormatException, ParseException,RenderException, IOException;
	
	public Map<RunnerJobParameter, List<Object>> getPossibleParameterValues(String jobName, String groupName) throws SQLException,NumberFormatException, ParseException;
	
}
