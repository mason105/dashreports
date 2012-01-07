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
 * Module: SetupJobStatsAction.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.admin;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerHistoryEvent;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class SetupJobStatsAction extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;
	private ReportRunnerDao<RunnerHistoryEvent,Long> historyDao;
	private List<RunnerHistoryEvent> longestEvents;
	private List<RunnerHistoryEvent> latestSuccessEvents;
	private List<RunnerHistoryEvent> latestFailEvents;
	
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String execute() throws Exception {

		this.longestEvents = historyDao.findByNamedQuery("getLongestRunningEvents",new String[]{} ,20);
		this.latestSuccessEvents = historyDao.findByNamedQuery("getSuccessEvents",new String[]{} ,20);
		this.latestFailEvents = historyDao.findByNamedQuery("getFailedEvents",new String[]{} ,20);
		
		return SUCCESS;
	}

	public void setHistoryDao(ReportRunnerDao<RunnerHistoryEvent,Long> historyDao) {
		this.historyDao = historyDao;
	}
	public List<RunnerHistoryEvent> getLatestFailEvents() {
		return latestFailEvents;
	}
	public List<RunnerHistoryEvent> getLatestSuccessEvents() {
		return latestSuccessEvents;
	}
	public List<RunnerHistoryEvent> getLongestEvents() {
		return longestEvents;
	}
	public void setLatestFailEvents(List<RunnerHistoryEvent> latestFailEvents) {
		this.latestFailEvents = latestFailEvents;
	}
	public void setLatestSuccessEvents(List<RunnerHistoryEvent> latestSuccessEvents) {
		this.latestSuccessEvents = latestSuccessEvents;
	}
	public void setLongestEvents(List<RunnerHistoryEvent> longestEvents) {
		this.longestEvents = longestEvents;
	}

}
