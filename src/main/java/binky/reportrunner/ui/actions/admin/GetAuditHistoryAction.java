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

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;

import binky.reportrunner.data.RunnerHistoryEvent;
import binky.reportrunner.service.AuditService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class GetAuditHistoryAction extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;
	private AuditService auditService;
	private List<RunnerHistoryEvent> longestEvents;
	private List<RunnerHistoryEvent> latestFailEvents;
	private String module;
	private Date fromDate;
	private Date toDate;
	private boolean showLongest;
	private long random;
	private static final Logger logger = Logger
			.getLogger(GetAuditHistoryAction.class);

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String execute() throws Exception {

		try {
		logger.trace("module: " + module);

		this.longestEvents = auditService.getLongestRunningEvents(module,
				fromDate,toDate);


		this.latestFailEvents = auditService.getFailedEvents(module,
				fromDate,toDate);
		showLongest = true;
		random = Math.round(Math.random()*1000000);
		} catch (Throwable t) {
			//effing struts eating up exceptions
			logger.fatal("error in ajax call to get log data",t);
			if (t instanceof Exception) throw (Exception)t;
		}
		return SUCCESS;
	}

	public void setAuditService(AuditService auditService) {
		this.auditService = auditService;
	}

	public List<RunnerHistoryEvent> getLatestFailEvents() {
		return latestFailEvents;
	}



	public List<RunnerHistoryEvent> getLongestEvents() {
		return longestEvents;
	}

	public void setLatestFailEvents(List<RunnerHistoryEvent> latestFailEvents) {
		this.latestFailEvents = latestFailEvents;
	}

	public void setLongestEvents(List<RunnerHistoryEvent> longestEvents) {
		this.longestEvents = longestEvents;
	}


	public void setModule(String module) {
		this.module = module;
	}

	public final List<String> getModules() {
		return auditService.getModuleNames();
	}

	public String getModule() {
		return module;
	}


	public boolean isShowLongest() {
		return showLongest;
	}

	public long getRandom() {
		return random;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}


}
