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
 * Module: DashboardService.java
 ******************************************************************************/
package binky.reportrunner.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import binky.reportrunner.data.RunnerDashboardItem;
import binky.reportrunner.scheduler.SchedulerException;

public interface DashboardService extends Auditable, Serializable {

	public List<RunnerDashboardItem> getItemsForGroup(String groupName);
	public List<RunnerDashboardItem> getAllItems();
	public Integer saveUpdateItem(RunnerDashboardItem item) throws SchedulerException;
	public void deleteItem(Integer id) throws SchedulerException;
	public RunnerDashboardItem getItem(Integer id);
	public List<RunnerDashboardItem> getRunningItems() throws SchedulerException;
	public void interruptRunningDashboardItem(Integer alertId) throws SchedulerException;
	public void invokeDashboardItem(Integer alertId) throws SchedulerException;
	public void processDashboardItem(int itemId) throws SQLException;
	public void clearTrendData(int itemId);
}
