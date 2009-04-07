package binky.reportrunner.scheduler;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import binky.reportrunner.dao.RunnerDashboardAlertDao;
import binky.reportrunner.dao.RunnerHistoryDao;
import binky.reportrunner.dao.RunnerJobDao;
import binky.reportrunner.data.RunnerDashboardAlert;
import binky.reportrunner.data.RunnerHistoryEvent;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.engine.EmailHandler;
import binky.reportrunner.engine.RunnerEngine;
import binky.reportrunner.engine.dashboard.AlertProcessor;
import binky.reportrunner.service.DatasourceService;

public class RunnerJobListener implements JobListener {

	private static final Logger logger = Logger
			.getLogger(RunnerJobListener.class);

	private String smtpServer;

	private String fromAddress;

	private RunnerHistoryDao runnerHistoryDao;

	private RunnerJobDao runnerJobDao;

	private DatasourceService datasourceService;

	private RunnerDashboardAlertDao dashboardDao;
	
	public void jobExecutionVetoed(JobExecutionContext ctx) {
		RunnerHistoryEvent event = new RunnerHistoryEvent();
		event.setGroupName(ctx.getJobDetail().getGroup());
		event.setJobName(ctx.getJobDetail().getName());
		event.setMessage("Job Execution Vetoed");
		event.setTimestamp(Calendar.getInstance().getTime());

		runnerHistoryDao.saveEvent(event);
		logger.warn("Job Execution Vetoed: " + ctx.getJobDetail().getName()
				+ "/" + ctx.getJobDetail().getGroup());
	}

	public void jobToBeExecuted(JobExecutionContext ctx) {

		if (ctx.getJobDetail().getJobClass().equals(RunnerEngine.class)) {

			String jobName = ctx.getJobDetail().getName();
			String groupName = ctx.getJobDetail().getGroup();

			RunnerJob job = runnerJobDao.getJob(jobName, groupName);
			ctx.getJobDetail().getJobDataMap().put("runnerJob", job);

			ctx.getJobDetail().getJobDataMap().put("smtpServer",
					this.smtpServer);
			ctx.getJobDetail().getJobDataMap().put("fromAddress",
					this.fromAddress);
			DataSource ds = datasourceService
					.getDataSource(job.getDatasource());
			ctx.getJobDetail().getJobDataMap().put("dataSource", ds);
		} else if (ctx.getJobDetail().getJobClass()
				.equals(AlertProcessor.class)) {
			// stuff for the dashboards
			Integer alertId = Integer.parseInt(ctx.getJobDetail().getName());
			RunnerDashboardAlert alert = dashboardDao.getAlert(alertId);
			ctx.getJobDetail().getJobDataMap().put("alert", alert);
			DataSource ds = datasourceService.getDataSource(alert.getDataSource());
			ctx.getJobDetail().getJobDataMap().put("dataSource", ds);
			ctx.getJobDetail().getJobDataMap().put("dashboardDao", dashboardDao);
		}
		logger.info("Scheduled task to be executed: " + ctx.getJobDetail().getName()
				+ "/" + ctx.getJobDetail().getGroup());
	}

	public void jobWasExecuted(JobExecutionContext ctx, JobExecutionException ex) {
	
		Boolean success = (ex == null);
		String jobName = ctx.getJobDetail().getName();
		String groupName = ctx.getJobDetail().getGroup();
		RunnerHistoryEvent event = new RunnerHistoryEvent();
		event.setGroupName(groupName);
		event.setJobName(jobName);
		
		event.setMessage(success ? "Scheduler Execution Success"
				: "Scheduler Execution Failure: " + getCustomStackTrace(ex));
		
		Date finishTime = Calendar.getInstance().getTime();
		event.setTimestamp(finishTime);
		event.setSuccess(success);
		event.setRunTime(ctx.getJobRunTime());
		runnerHistoryDao.saveEvent(event);

		
		if (ctx.getJobDetail().getJobClass().equals(RunnerEngine.class)) {

			RunnerJob job = runnerJobDao.getJob(jobName, groupName);		

			if ((job.getAlertEmailAddress() != null)
					&& !job.getAlertEmailAddress().isEmpty()) {
				sendEmailAlert(jobName, groupName, job.getAlertEmailAddress(),
						finishTime, success);
			}
		}

		if (success) {
			logger.info("Job was executed: " + ctx.getJobDetail().getName()
					+ "/" + ctx.getJobDetail().getGroup());
		} else {
			logger.error("Job Failed : " + ctx.getJobDetail().getName()
					+ "/" + ctx.getJobDetail().getGroup(), ex);
		}
	}

	private void sendEmailAlert(String jobName, String groupName,
			String targetEmail, Date finishTime, boolean success) {
		EmailHandler email = new EmailHandler();
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

	public RunnerHistoryDao getRunnerHistoryDao() {
		return runnerHistoryDao;
	}

	public void setRunnerHistoryDao(RunnerHistoryDao runnerHistoryDao) {
		this.runnerHistoryDao = runnerHistoryDao;
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

	public RunnerJobDao getRunnerJobDao() {
		return runnerJobDao;
	}

	public void setRunnerJobDao(RunnerJobDao runnerJobDao) {
		this.runnerJobDao = runnerJobDao;
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

	public RunnerDashboardAlertDao getDashboardDao() {
		return dashboardDao;
	}

	public void setDashboardDao(RunnerDashboardAlertDao dashboardDao) {
		this.dashboardDao = dashboardDao;
	}

}
