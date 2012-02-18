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

import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;

import binky.reportrunner.data.RunnerHistoryEvent;
import binky.reportrunner.service.AuditService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class GetAuditHistoryAction extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;
	private AuditService auditService;
	private List<RunnerHistoryEvent> longestEvents;
	private List<RunnerHistoryEvent> latestSuccessEvents;
	private List<RunnerHistoryEvent> latestFailEvents;
	private String module;
	private int returnCount;
	private boolean showLongest;
	private static final Logger logger = Logger
			.getLogger(GetAuditHistoryAction.class);

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String execute() throws Exception {

		logger.trace("return count: " + returnCount);
		logger.trace("module: " + module);

		this.longestEvents = auditService.getLongestRunningEvents(module,
				returnCount);
		this.latestSuccessEvents = auditService.getSuccessEvents(module,
				returnCount);

		this.latestFailEvents = auditService.getFailedEvents(module,
				returnCount);
		showLongest = true;

		return SUCCESS;
	}

	public void setAuditService(AuditService auditService) {
		this.auditService = auditService;
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

	public void setLatestSuccessEvents(
			List<RunnerHistoryEvent> latestSuccessEvents) {
		this.latestSuccessEvents = latestSuccessEvents;
	}

	public void setLongestEvents(List<RunnerHistoryEvent> longestEvents) {
		this.longestEvents = longestEvents;
	}

	public void setReturnCount(int returnCount) {
		this.returnCount = returnCount;
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

	public int getReturnCount() {
		return returnCount;
	}

	public boolean isShowLongest() {
		return showLongest;
	}

}
