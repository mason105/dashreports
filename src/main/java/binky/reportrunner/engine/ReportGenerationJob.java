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
 * Module: RunnerEngine.java
 ******************************************************************************/
package binky.reportrunner.engine;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import binky.reportrunner.service.ReportGenerationService;
import binky.reportrunner.util.ApplicationContextProvider;

/**
 * @author Daniel Grout
 */

public class ReportGenerationJob implements StatefulJob {


	private static final Logger logger = Logger.getLogger(ReportGenerationJob.class);


	public final void execute(JobExecutionContext context)
			throws JobExecutionException {
		
		ReportGenerationService generationService = (ReportGenerationService)ApplicationContextProvider.getApplicationContext().getBean("reportGenerationService");

		String jobName=(String)context.getJobDetail()
				.getJobDataMap().get("jobName");
		String groupName=(String)context.getJobDetail()
				.getJobDataMap().get("groupName");
		
		try {
			generationService.processReport(jobName, groupName);
		} catch (Exception e) {
			logger.fatal(e.getMessage(),e);
			throw new JobExecutionException(e);
		}
	}



}
