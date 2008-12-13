package binky.reportrunner.engine;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JasperReport;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.engine.renderers.AbstractRenderer;
import binky.reportrunner.engine.renderers.JasperRenderer;
import binky.reportrunner.engine.renderers.StandardRenderer;
import binky.reportrunner.exceptions.RenderException;

/**
 * @author Daniel Grout
 **/

public class RunnerEngine implements Job {

	SQLProcessor sqlProcessor;
	FileSystemHandler fs;
	String fromAddress;
	String smtpServer;
	DataSource ds;

	private static final Logger logger = Logger.getLogger(RunnerEngine.class);
	
	public RunnerEngine() throws IOException {
		this.sqlProcessor = new SQLProcessor();
		this.fs = new FileSystemHandler();
	}

	public final void execute(JobExecutionContext context)
			throws JobExecutionException {

		// Grab the elements of the job from the context to pass on
		RunnerJob job = (RunnerJob) context.getJobDetail().getJobDataMap().get(
				"runnerJob");
		this.smtpServer = (String) context.getJobDetail().getJobDataMap().get(
				"smtpServer");
		this.fromAddress = (String) context.getJobDetail().getJobDataMap().get(
				"fromAddress");
		this.ds = (DataSource) context.getJobDetail().getJobDataMap().get(
				"dataSource");
		try {

			if (job.getIsBurst()) {
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
			NamingException {
		List<String> fileUrls = new LinkedList<String>();
		String groupName = job.getPk().getGroup().getGroupName();
		String jobName = job.getPk().getJobName();
		Connection conn;

		conn = ds.getConnection();
		ResultSet burstResults = sqlProcessor.getResults(conn, job
				.getBurstQuery());

		List<RunnerJobParameter> params = job.getParameters();
		while (burstResults.next()) {
			// populate the parameters
			List<RunnerJobParameter> populatedParams = new LinkedList<RunnerJobParameter>();
			for (RunnerJobParameter param : params) {
				param.setParameterValue(""
						+ burstResults.getObject(param
								.getParameterBurstColumn()));
				populatedParams.add(param);
				logger.debug("added populated param" + param.getPk().getParameterIdx() + " - value - " + param.getParameterValue());
			}
			String fileNameValue = ""
					+ burstResults.getObject(job
							.getBurstFileNameParameterName());

			// process the query with the results in

			ResultSet results = sqlProcessor.getResults(conn, job.getQuery(),
					populatedParams);

			// if we are not outputting this anywhere (must be emailing) then
			// dump this as a temp file
			String outUrl = fs.getFinalUrl(job.getOutputUrl(), jobName,
					groupName, job.getFileFormat().toString().toLowerCase());

			
			// insert the bursted filename value into the url - probably a
			// better way to do this.
			outUrl = outUrl + "_" + fileNameValue;				
			logger.debug("bursted file being output to: " + outUrl);
			doReport(results, outUrl, job.getJasperReport(), job
					.getFileFormat().toString());

			fileUrls.add(outUrl);

		}
		conn.close();

		// send email if need be
		if ((job.getTargetEmailAddress() != null)
				&& (!job.getTargetEmailAddress().isEmpty())) {
			EmailHandler email = new EmailHandler();
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
			NamingException {
		String groupName = job.getPk().getGroup().getGroupName();
		String jobName = job.getPk().getJobName();
		Connection conn = ds.getConnection();

		ResultSet results = sqlProcessor.getResults(conn, job.getQuery(), job
				.getParameters());
		// if we are not outputting this anywhere (must be emailing) then
		// dump this as a temp file
		String outUrl = fs.getFinalUrl(job.getOutputUrl(), jobName, groupName,
				job.getFileFormat().toString().toLowerCase());
		doReport(results, outUrl, job.getJasperReport(), job.getFileFormat()
				.toString());
		conn.close();

		// send email if need be
		if ((job.getTargetEmailAddress() != null)
				&& (!job.getTargetEmailAddress().isEmpty())) {
			EmailHandler email = new EmailHandler();
			email.sendEmail(job.getTargetEmailAddress(), fromAddress,
					smtpServer, outUrl, jobName, groupName);
		}

		// clean up any temp files
		if ((job.getOutputUrl() == null) || (job.getOutputUrl().isEmpty())) {
			fs.deleteFile(outUrl);
		}
		return outUrl;
	}

	private void doReport(ResultSet results, String url, JasperReport jReport,
			String fileFormat) throws RenderException, IOException {
		OutputStream os = fs.getOutputStreamForUrl(url);
		AbstractRenderer renderer;
		if (jReport != null) {
			renderer = new JasperRenderer(jReport);
		} else {
			renderer = new StandardRenderer();
		}
		renderer.generateReport(results, os, fileFormat);
		os.close();
	}

}
