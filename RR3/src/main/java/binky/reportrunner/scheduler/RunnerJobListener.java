package binky.reportrunner.scheduler;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import binky.reportrunner.dao.RunnerHistoryDao;
import binky.reportrunner.dao.RunnerJobDao;
import binky.reportrunner.data.RunnerHistoryEvent;
import binky.reportrunner.data.RunnerJob;

public class RunnerJobListener implements JobListener {

	private static final Logger logger = Logger.getLogger(RunnerJobListener.class);

	private String smtpServer;
	private String fromAddress;
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
	private RunnerHistoryDao runnerHistoryDao;
	private RunnerJobDao runnerJobDao;

	public RunnerJobDao getRunnerJobDao() {
		return runnerJobDao;
	}

	public void setRunnerJobDao(RunnerJobDao runnerJobDao) {
		this.runnerJobDao = runnerJobDao;
	}

	public String getName() {
		return "ReportRunnerCoreJobListener";
	}

	public void jobExecutionVetoed(JobExecutionContext ctx) {
		RunnerHistoryEvent event = new RunnerHistoryEvent();
		event.setGroupName(ctx.getJobDetail().getGroup());
		event.setJobName(ctx.getJobDetail().getName());
		event.setMessage("Job Execution Vetoed");
		event.setTimestamp(Calendar.getInstance().getTime());

		runnerHistoryDao.saveEvent(event);
		logger.warn("Job Execution Vetoed: " + ctx.getJobDetail().getName() + "/" + ctx.getJobDetail().getGroup());
	}

	public void jobToBeExecuted(JobExecutionContext ctx) {
		String jobName = ctx.getJobDetail().getName();
		String groupName = ctx.getJobDetail().getGroup();

		RunnerJob job = runnerJobDao.getJob(jobName, groupName);
		ctx.put("runnerJob", job);
		
		ctx.put("smtpServer", this.smtpServer);
		ctx.put("fromAddress", this.fromAddress);
		
		logger.info("Job to be executed: " + ctx.getJobDetail().getName() + "/" + ctx.getJobDetail().getGroup());
		
	}

	public void jobWasExecuted(JobExecutionContext ctx, JobExecutionException ex) {
		Boolean success = (ex == null);

		RunnerHistoryEvent event = new RunnerHistoryEvent();
		event.setGroupName(ctx.getJobDetail().getGroup());
		event.setJobName(ctx.getJobDetail().getName());
		event.setMessage(success ? "Job Execution Success"
				: "Job Execution Failure: " + ex.getMessage());
		event.setTimestamp(Calendar.getInstance().getTime());

		runnerHistoryDao.saveEvent(event);
		
		if (success){
			logger.info("Job was executed: " + ctx.getJobDetail().getName() + "/" + ctx.getJobDetail().getGroup());
		} else{
			logger.error("Job Failed : " + ctx.getJobDetail().getName() + "/" + ctx.getJobDetail().getGroup(),ex);
		}
	}

	public RunnerHistoryDao getRunnerHistoryDao() {
		return runnerHistoryDao;
	}

	public void setRunnerHistoryDao(RunnerHistoryDao runnerHistoryDao) {
		this.runnerHistoryDao = runnerHistoryDao;
	}

}
