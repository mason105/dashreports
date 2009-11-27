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

import java.io.IOException;
import java.util.List;

import binky.reportrunner.data.RunnerDashboardAlert;
import binky.reportrunner.scheduler.SchedulerException;

public interface DashboardService {

	public List<RunnerDashboardAlert> getAlertsForGroup(String groupName);
	public List<RunnerDashboardAlert> getAllAlerts();
	public void saveUpdateAlert(RunnerDashboardAlert alert) throws SchedulerException;
	public void deleteAlert(Integer id) throws SchedulerException;
	public RunnerDashboardAlert getAlert(Integer id);
	public String getChartForAlert(Integer id) throws  NumberFormatException, IOException;
	public List<RunnerDashboardAlert> getRunningAlerts() throws SchedulerException;
	public void interruptRunningDashboardAlert(Integer alertId) throws SchedulerException;
	public void invokeDashboardAlert(Integer alertId) throws SchedulerException;
}
