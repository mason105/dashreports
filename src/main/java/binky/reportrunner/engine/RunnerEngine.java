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

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.engine.impl.RunnerResultGeneratorImpl;
import binky.reportrunner.engine.utils.EmailHandler;
import binky.reportrunner.engine.utils.FileSystemHandler;
import binky.reportrunner.engine.utils.SQLProcessor;
import binky.reportrunner.engine.utils.impl.EmailHandlerImpl;
import binky.reportrunner.engine.utils.impl.FileSystemHandlerImpl;
import binky.reportrunner.engine.utils.impl.SQLProcessorImpl;
import binky.reportrunner.exceptions.RenderException;
import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.service.ReportService;
import binky.reportrunner.util.ApplicationContextProvider;

/**
 * @author Daniel Grout
 */

public class RunnerEngine implements StatefulJob {

	SQLProcessor sqlProcessor;

	FileSystemHandler fs;

	String fromAddress;

	String smtpServer;

	DataSource ds;
	Connection conn;
	private static final Logger logger = Logger.getLogger(RunnerEngine.class);

	public RunnerEngine() throws IOException {
		this.sqlProcessor = new SQLProcessorImpl();
		this.fs = new FileSystemHandlerImpl();
	}

	public final void execute(JobExecutionContext context)
			throws JobExecutionException {
		
		ApplicationContext ctx =ApplicationContextProvider.getApplicationContext();
		ReportService jobService = (ReportService)ctx.getBean("runnerJobService");
		DatasourceService dsService = (DatasourceService)ctx.getBean("runnerDatasourceService");
		String jobName=(String)context.getJobDetail()
				.getJobDataMap().get("jobName");
		String groupName=(String)context.getJobDetail()
				.getJobDataMap().get("groupName");
		// Grab the elements of the job from the context to pass on
		RunnerJob job = jobService.getJob(jobName, groupName);
		this.smtpServer = (String) context.getJobDetail().getJobDataMap().get(
				"smtpServer");
		this.fromAddress = (String) context.getJobDetail().getJobDataMap().get(
				"fromAddress");
		try {
			this.ds = dsService.getJDBCDataSource(job.getDatasource());
		} catch (SQLException e1) {
			throw new JobExecutionException(e1);
		}
		try {
			if (ds == null) {
				logger.fatal("datasource is null!");
				throw new Exception("datasource is null!");
			}

			if ((job.getIsBurst() != null) && job.getIsBurst()) {
				processBurstedReport(job);
			} else {
				processSingleReport(job);
			}

		} catch (Exception e) {
			throw new JobExecutionException(e);
		}
	}

	List<String> processBurstedReport(RunnerJob job) throws IOException,
			RenderException, EmailException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException,
			NamingException, NumberFormatException, ParseException {
		List<String> fileUrls = new LinkedList<String>();
		String groupName = job.getPk().getGroup().getGroupName();
		String jobName = job.getPk().getJobName();

		conn = ds.getConnection();

		RunnerResultGenerator resultGenerator = new RunnerResultGeneratorImpl(
				conn);

		Map<String, ResultSet> results = new HashMap<String, ResultSet>();

		resultGenerator.getResultsForJob(job, results);

		for (String fileNameValue : results.keySet()) {
			ResultSet rs = results.get(fileNameValue);
			// if we are not outputting this anywhere (must be emailing) then
			// dump this as a temp file
			String outUrl = fs.getFinalUrl(job.getOutputUrl(), jobName,
					groupName, job.getFileFormat().toString().toLowerCase(),
					fileNameValue);

			logger.info("bursted file being output to: " + outUrl);

			resultGenerator.renderReport(rs, outUrl, job.getTemplateFile(), job
					.getTemplateType(), job.getFileFormat().toString());

			fileUrls.add(outUrl);

		}
		conn.close();

		// send email if need be
		if ((job.getTargetEmailAddress() != null)
				&& (!job.getTargetEmailAddress().isEmpty())) {
			EmailHandler email = new EmailHandlerImpl();
			email.sendEmail(job.getTargetEmailAddress(), fromAddress,
					smtpServer, fileUrls, jobName, groupName);
		}

		// clean up any temp files
		if ((job.getOutputUrl() == null) || (job.getOutputUrl().isEmpty())) {
			for (String url : fileUrls) {
				fs.deleteFile(url);
			}
		}

		return fileUrls;
	}

	String processSingleReport(RunnerJob job) throws IOException,
			RenderException, EmailException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException,
			NamingException, NumberFormatException, ParseException {
		String groupName = job.getPk().getGroup().getGroupName();
		String jobName = job.getPk().getJobName();
		Connection conn = ds.getConnection();
		logger.debug("running single report for:" + groupName + "." + jobName);
		RunnerResultGenerator resultGenerator = new RunnerResultGeneratorImpl(
				conn);

		Map<String, ResultSet> results = new HashMap<String, ResultSet>();
		resultGenerator.getResultsForJob(job, results);

		// if we are not outputting this anywhere (must be emailing) then
		// dump this as a temp file
		String outUrl = fs.getFinalUrl(job.getOutputUrl(), jobName, groupName,
				job.getFileFormat().toString().toLowerCase());
		resultGenerator.renderReport(results.get("Results"), outUrl, job
				.getTemplateFile(), job.getTemplateType(), job.getFileFormat()
				.toString());
		conn.close();
		logger.info("writing report to: " + outUrl);
		// send email if need be
		if ((job.getTargetEmailAddress() != null)
				&& (!job.getTargetEmailAddress().isEmpty())) {
			EmailHandler email = new EmailHandlerImpl();
			email.sendEmail(job.getTargetEmailAddress(), fromAddress,
					smtpServer, outUrl, jobName, groupName);
		}

		// clean up any temp files
		if ((job.getOutputUrl() == null) || (job.getOutputUrl().isEmpty())) {
			fs.deleteFile(outUrl);
		}
		return outUrl;
	}


}
