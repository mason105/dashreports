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
 * Module: AlertProcessor.java
 ******************************************************************************/
package binky.reportrunner.engine.dashboard;

import org.apache.log4j.Logger;
import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

import binky.reportrunner.service.DashboardService;

public class AlertProcessor implements Job, InterruptableJob {

	

	private DashboardService dashboardService; 
	
	private static final Logger logger = Logger.getLogger(AlertProcessor.class);

	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		// Grab the elements of the job from the context to pass on
		Integer itemId = (Integer)context.getJobDetail()
				.getJobDataMap().get("itemId");
	
		this.dashboardService = (DashboardService)context.getJobDetail().getJobDataMap().get("dashboardService");
		
		
		try {
			if (itemId == null) {
				logger.fatal("item is null!");
				throw new Exception("item is null!");
			}
			if (dashboardService == null) {
				logger.fatal("service is null!");
				throw new Exception("service is null!");
			}
			//in the land of the hack here - all to do with collections, lazy initialising and 

			dashboardService.processDashboardItem(itemId);
			
		} catch (Exception e) {
			throw new JobExecutionException(e);
		} 

	}

	

	public void interrupt() throws UnableToInterruptJobException {
		throw new UnableToInterruptJobException("not implemented");
	}

}
