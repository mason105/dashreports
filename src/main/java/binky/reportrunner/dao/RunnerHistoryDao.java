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
 * Module: RunnerHistoryDao.java
 ******************************************************************************/
package binky.reportrunner.dao;

import java.util.Date;
import java.util.List;

import binky.reportrunner.data.RunnerHistoryEvent;

public interface RunnerHistoryDao {

	public List<RunnerHistoryEvent> getEvents(String groupName, String jobName);
	public List<RunnerHistoryEvent> getEvents(String groupName, String jobName, 
			Date startTime, Date endTime);
	public void saveEvent(RunnerHistoryEvent event);
	public List<RunnerHistoryEvent> getLongestRunningEvents(int eventCount);
	public List<RunnerHistoryEvent> getSuccessEvents(int eventCount);
	public List<RunnerHistoryEvent> getFailEvents(int eventCount);
	public void deleteRangeOfEvents(Date oldest);
	/**
	 * 
	 * Only really used to clean up after the test
	 * 
	 * @param eventId
	 */
	public void deleteEvents(String groupName, String jobName);
}
