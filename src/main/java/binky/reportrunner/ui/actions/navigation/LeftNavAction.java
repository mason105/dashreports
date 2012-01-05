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
 * Module: LeftNavAction.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.navigation;

import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class LeftNavAction extends StandardRunnerAction {

	private static final Logger logger = Logger.getLogger(LeftNavAction.class);
	private static final long serialVersionUID = -2321083106251542716L;
	private ReportRunnerDao<RunnerGroup, String> groupDao;
	private List<RunnerGroup> groups;

	@Override
	public String execute() throws Exception {

		if (super.getSessionUser().getIsAdmin()) {
			groups = groupDao.getAll();
		} else {
			super.getSessionUser().getGroups();
		}

		return SUCCESS;
	}

	public void setGroupDao(ReportRunnerDao<RunnerGroup, String> groupDao) {
		this.groupDao = groupDao;
	}

	public List<RunnerGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<RunnerGroup> groups) {
		this.groups = groups;
	}

}
