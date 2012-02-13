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

import java.util.Arrays;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.springframework.security.access.prepost.PreAuthorize;

import binky.reportrunner.data.RunnerHistoryEvent.Module;
import binky.reportrunner.service.AuditService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class GetAuditChartAction extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;
	private AuditService auditService;
	
	private JFreeChart chart;
	
	private Module module;
	

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String execute() throws Exception {
		
		//do some stuff and get a chart going
		
		return SUCCESS;
	}

	public void setAuditService(AuditService auditService) {
		this.auditService = auditService;
	}


	public JFreeChart getChart() {
		return chart;
	}

	public void setModule(Module module) {
		this.module = module;
	}
	public final List<Module> getModules() {
		return Arrays.asList(Module.values());
	}
}
