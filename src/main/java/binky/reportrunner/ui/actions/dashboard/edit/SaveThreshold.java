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
 * Module: SaveAlert.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.dashboard.edit;

import binky.reportrunner.data.RunnerDashboardThreshold;
import binky.reportrunner.ui.actions.dashboard.base.BaseEditDashboardAction;

public class SaveThreshold extends BaseEditDashboardAction {

	private static final long serialVersionUID = 1L;

	private RunnerDashboardThreshold item;

	@Override
	public String execute() throws Exception {
		if (this.item.getLabelColumn().equals("-")) this.item.setLabelColumn(null);
		if (this.item.getValueColumn().equals("-")) this.item.setValueColumn(null);
		return super.saveItem(this.item);
	}

	public RunnerDashboardThreshold getItem() {
		return item;
	}

	public void setItem(RunnerDashboardThreshold item) {
		this.item = item;
	}

}
