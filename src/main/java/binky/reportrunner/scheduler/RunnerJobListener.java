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
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import binky.reportrunner.data.RunnerHistoryEvent.Module;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.engine.RunnerEngine;
import binky.reportrunner.engine.dashboard.AlertProcessor;
import binky.reportrunner.engine.utils.EmailHandler;
import binky.reportrunner.engine.utils.impl.EmailHandlerImpl;
import binky.reportrunner.service.AuditService;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.service.RunnerJobService;

public class RunnerJobListener implements JobListener {

	private static final Logger logger = Logger
			.getLogger(RunnerJobListener.class);

	private String smtpServer;

	private String fromAddress;

	private AuditService auditService;

	private RunnerJobService jobService;

	private DatasourceService datasourceService;

	private DashboardService dashboardService;
	
	public void jobExecutionVetoed(JobExecutionContext ctx) {
		auditService.logAuditEvent(Module.CORE_SCHEDULER, "Job Execution Vetoed", "", false, 0, ctx.getJobDetail().getName(), ctx.getJobDetail().getGroup());
		logger.warn("Job Execution Vetoed: " + ctx.getJobDetail().getName()
				+ "/" + ctx.getJobDetail().getGroup());
	}

	public void jobToBeExecuted(JobExecutionContext ctx) {

		if (ctx.getJobDetail().getJobClass().equals(RunnerEngine.class)) {

			String jobName = ctx.getJobDetail().getName();
			String groupName = ctx.getJobDetail().getGroup();

			RunnerJob job = jobService.getJob(jobName, groupName);
			ctx.getJobDetail().getJobDataMap().put("runnerJob", job);

			ctx.getJobDetail().getJobDataMap().put("smtpServer",
					this.smtpServer);
			ctx.getJobDetail().getJobDataMap().put("fromAddress",
					this.fromAddress);
			DataSource ds;
			try {
				ds = datasourceService
						.getJDBCDataSource(job.getDatasource());
				ctx.getJobDetail().getJobDataMap().put("dataSource", ds);
			} catch (SQLException e) {
				logger.error(e.getMessage(),e);
			}
			
		} else if (ctx.getJobDetail().getJobClass()
				.equals(AlertProcessor.class)) {
			// stuff for the dashboards
			Integer itemId = Integer.parseInt(ctx.getJobDetail().getName());
			ctx.getJobDetail().getJobDataMap().put("itemId", itemId);	
			ctx.getJobDetail().getJobDataMap().put("dashboardService", dashboardService);
		}
		logger.trace("Scheduled task to be executed: " + ctx.getJobDetail().getName()
				+ "/" + ctx.getJobDetail().getGroup());
	}

	public void jobWasExecuted(JobExecutionContext ctx, JobExecutionException ex) {
	
		Boolean success = (ex == null);
		String jobName = ctx.getJobDetail().getName();
		String groupName = ctx.getJobDetail().getGroup();
		
		String message=success ? "Scheduler Execution Success"
				: "Scheduler Execution Failure: " + getCustomStackTrace(ex);
		
		if (ctx.getJobDetail().getJobClass().equals(RunnerEngine.class)) {
			
			auditService.logAuditEvent(Module.REPORT_SCHEDULER, message, "", success, ctx.getJobRunTime(), jobName, groupName);
		} else if (ctx.getJobDetail().getJobClass()
				.equals(AlertProcessor.class)) {
			auditService.logAuditEvent(Module.DASHBOARD_SCHEDULER, message, "", success, ctx.getJobRunTime(), jobName, groupName);			
		}
		

		
		if (ctx.getJobDetail().getJobClass().equals(RunnerEngine.class)) {

			RunnerJob job =jobService.getJob(jobName, groupName);

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
			logger.error("Job Failed : " + ctx.getJobDetail().getName()
					+ "/" + ctx.getJobDetail().getGroup(), ex);
		}
	}

	private void sendEmailAlert(String jobName, String groupName,
			String targetEmail, Date finishTime, boolean success) {
		EmailHandler email = new EmailHandlerImpl();
		try {
			email.sendAlertEmail(targetEmail, fromAddress, smtpServer, jobName,
					groupName, success, finishTime);
		} catch (EmailException e) {
			logger.error("Failed to send alert email!", e);
		} catch (IOException e) {
			logger.error("Failed to send alert email!", e);
		}
	}

	/**
	 * Defines a custom format for the stack trace as String.
	 */
	private String getCustomStackTrace(Throwable aThrowable) {
		//temp
		aThrowable.printStackTrace();
		// add the class name and any message passed to constructor
		final StringBuilder result = new StringBuilder();
		result.append(aThrowable.toString());
		final String NEW_LINE = System.getProperty("line.separator");
		// add each element of the stack trace
		for (StackTraceElement element : aThrowable.getStackTrace()) {
			result.append(element);
			result.append(NEW_LINE);
		}
		return result.toString();
	}


	public void setAuditService(AuditService auditService) {
		this.auditService = auditService;
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}


	public String getName() {
		return "ReportRunnerCoreJobListener";
	}

	public DatasourceService getDatasourceService() {
		return datasourceService;
	}

	public void setDatasourceService(DatasourceService datasourceService) {
		this.datasourceService = datasourceService;
	}

	public void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}

	public void setDashboardService(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}



}
