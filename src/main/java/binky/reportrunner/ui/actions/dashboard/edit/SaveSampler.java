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

import binky.reportrunner.data.RunnerDashboardSampler;
import binky.reportrunner.ui.actions.dashboard.base.BaseEditDashboardAction;
import binky.reportrunner.ui.util.QuartzCronSchedule;

public class SaveSampler extends BaseEditDashboardAction {

	private static final long serialVersionUID = 1L;

	private RunnerDashboardSampler item;

	@Override
	public String execute() throws Exception {
		switch (item.getInterval()) {
		case DAY:
			super.simpleCron=new QuartzCronSchedule("0 0 0 ? * *");
		 break;
		case MINUTE:
			super.simpleCron=new QuartzCronSchedule("0 * * ? * *");
			break;
		case MONTH:
			super.simpleCron=new QuartzCronSchedule("0 0 0 1 * ?");
			break;
		case SECOND:
			super.simpleCron=new QuartzCronSchedule("* * * ? * *");
			break;
		case HOUR:
		default:
			super.simpleCron=new QuartzCronSchedule("0 0 * ? * *");
		}
		
		return super.saveItem(this.item);
	}

	public RunnerDashboardSampler getItem() {
		return item;
	}

	public void setItem(RunnerDashboardSampler item) {
		this.item = item;
	}



}
