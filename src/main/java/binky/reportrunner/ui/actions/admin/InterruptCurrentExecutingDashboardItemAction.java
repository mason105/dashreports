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
 * Module: InterruptCurrentExecutingJobAction.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.admin;

import binky.reportrunner.service.DashboardService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class InterruptCurrentExecutingDashboardItemAction extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;
	private DashboardService dashboardService;
	private Integer itemId;
	@Override
	public String execute() throws Exception {
		dashboardService.interruptRunningDashboardItem(itemId);
		return SUCCESS;
	}
	public DashboardService getDashboardService() {
		return dashboardService;
	}
	public void setDashboardService(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

}
