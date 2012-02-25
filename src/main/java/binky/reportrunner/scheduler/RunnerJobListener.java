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
 * Module: RunnerJobListener.java
 ******************************************************************************/
package binky.reportrunner.scheduler;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import binky.reportrunner.data.Configuration.ConfigurationType;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.engine.RunnerEngine;
import binky.reportrunner.engine.dashboard.AlertProcessor;
import binky.reportrunner.engine.utils.EmailHandler;
import binky.reportrunner.engine.utils.impl.EmailHandlerImpl;
import binky.reportrunner.service.ConfigurationService;
import binky.reportrunner.service.ReportService;

public class RunnerJobListener implements JobListener {

	private static final Logger logger = Logger
			.getLogger(RunnerJobListener.class);


	private ReportService jobService;

	private ConfigurationService configurationService;

	public void jobExecutionVetoed(JobExecutionContext ctx) {
			logger.warn("Job Execution Vetoed: " + ctx.getJobDetail().getName()
				+ "/" + ctx.getJobDetail().getGroup());
	}

	public void jobToBeExecuted(JobExecutionContext ctx) {

		if (ctx.getJobDetail().getJobClass().equals(RunnerEngine.class)) {

			String jobName = ctx.getJobDetail().getName();
			String groupName = ctx.getJobDetail().getGroup();

			ctx.getJobDetail().getJobDataMap().put("jobName", jobName);
			ctx.getJobDetail().getJobDataMap().put("groupName", groupName);
			ctx.getJobDetail().getJobDataMap()
					.put("smtpServer", configurationService.getConfigurationItem(ConfigurationType.EMAIL_SERVER).getValue());
			ctx.getJobDetail().getJobDataMap()
					.put("fromAddress", configurationService.getConfigurationItem(ConfigurationType.EMAIL_FROM_ADDRESS).getValue());

		} else if (ctx.getJobDetail().getJobClass()
				.equals(AlertProcessor.class)) {
			// stuff for the dashboards
			Integer itemId = Integer.parseInt(ctx.getJobDetail().getName());
			ctx.getJobDetail().getJobDataMap().put("itemId", itemId);

		}
		logger.trace("Scheduled task to be executed: "
				+ ctx.getJobDetail().getName() + "/"
				+ ctx.getJobDetail().getGroup());
	}

	public void jobWasExecuted(JobExecutionContext ctx, JobExecutionException ex) {

		Boolean success = (ex == null);
		String jobName = ctx.getJobDetail().getName();
		String groupName = ctx.getJobDetail().getGroup();
	
		
		if (ctx.getJobDetail().getJobClass().equals(RunnerEngine.class)) {

			RunnerJob job = jobService.getJob(jobName, groupName);

			if ((job.getAlertEmailAddress() != null)
					&& !job.getAlertEmailAddress().isEmpty()) {
				sendEmailAlert(jobName, groupName, job.getAlertEmailAddress(),
						Calendar.getInstance().getTime(), success);
			}
		}

		if (success) {
			logger.trace("Job was executed: " + ctx.getJobDetail().getName()
					+ "/" + ctx.getJobDetail().getGroup());
		} else {
			logger.error("Job Failed : " + ctx.getJobDetail().getName() + "/"
					+ ctx.getJobDetail().getGroup(), ex);
		}
	}

	private void sendEmailAlert(String jobName, String groupName,
			String targetEmail, Date finishTime, boolean success) {
		EmailHandler email = new EmailHandlerImpl();
		try {
			email.sendAlertEmail(targetEmail, configurationService.getConfigurationItem(ConfigurationType.EMAIL_FROM_ADDRESS).getValue(), configurationService.getConfigurationItem(ConfigurationType.EMAIL_SERVER).getValue(), jobName,
					groupName, success, finishTime);
		} catch (EmailException e) {
			logger.error("Failed to send alert email!", e);
		} catch (IOException e) {
			logger.error("Failed to send alert email!", e);
		}
	}


	public String getName() {
		return "ReportRunnerCoreJobListener";
	}



	public void setJobService(ReportService jobService) {
		this.jobService = jobService;
	}

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}



}
