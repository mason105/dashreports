package binky.reportrunner.engine;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.commons.mail.EmailException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;

/**
 * @author Daniel Grout
 **/

public class RunnerEngine implements Job {

	DatasourceManager dsManager;
	SQLProcessor sqlProcessor;
	FileSystemHandler fs;

	public RunnerEngine() throws IOException {
		this.dsManager = new DatasourceManager();
		this.sqlProcessor = new SQLProcessor();
		this.fs = new FileSystemHandler();
	}

	public final void execute(JobExecutionContext context)
			throws JobExecutionException {

		// Grab the elements of the job from the context to pass on
		RunnerJob job = (RunnerJob) context.getJobDetail().getJobDataMap().get(
				"runnerJob");

		try {

			if (job.getIsBurst()) {
				processBurstedReport(job);
			} else {
				processSingleReport(job);
			}
		} catch (InstantiationException e) {
			throw new JobExecutionException(e);
		} catch (IllegalAccessException e) {
			throw new JobExecutionException(e);
		} catch (ClassNotFoundException e) {
			throw new JobExecutionException(e);
		} catch (SQLException e) {
			throw new JobExecutionException(e);
		} catch (NamingException e) {
			throw new JobExecutionException(e);
		} catch (IOException e) {
			throw new JobExecutionException(e);
		} catch (JRException e) {
			throw new JobExecutionException(e);
		} catch (EmailException e) {
			throw new JobExecutionException(e);
		}
	}

	private void processBurstedReport(RunnerJob job) throws IOException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, JRException, SQLException, NamingException,
			EmailException {
		List<String> fileUrls = new LinkedList<String>();

		Connection conn;

		conn = dsManager.getDataConnection(job.getDatasource());
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
			}
			String fileNameValue = ""
					+ burstResults.getObject(job
							.getBurstFileNameParameterName());

			// process the query with the results in

			ResultSet results = sqlProcessor.getResults(conn, job.getQuery(),
					populatedParams);

			// if we are not outputting this anywhere (must be emailing) then
			// dump this as a temp file
			String outUrl = fs.getFinalUrl(job.getOutputUrl(), job.getPk()
					.getJobName(), job.getPk().getGroup().getGroupName(), job
					.getFileFormat().toString().toLowerCase());

			int lastDot = outUrl.lastIndexOf(".");
			// insert the bursted filename value into the url
			outUrl = outUrl.substring(0, lastDot) + "_" + fileNameValue
					+ outUrl.substring(lastDot);

			doReport(results, outUrl, job.getJasperReport(), job
					.getFileFormat().toString());

			conn.close();
			fileUrls.add(outUrl);

			// send email if need be
			if ((job.getTargetEmailAddress() != null)
					&& (!job.getTargetEmailAddress().isEmpty())) {
				EmailHandler email = new EmailHandler();
				email.sendEmail(job.getTargetEmailAddress(), job
						.getFromAddress(), job.getSmtpServer(), fileUrls, job
						.getPk().getJobName(), job.getPk().getGroup()
						.getGroupName());
			}

			// clean up any temp files
			if ((job.getOutputUrl() == null) || (job.getOutputUrl().isEmpty())) {
				for (String url : fileUrls) {
					fs.deleteFile(url);
				}
			}
		}

	}

	private void processSingleReport(RunnerJob job)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException, NamingException, IOException,
			JRException, EmailException {

		Connection conn = dsManager.getDataConnection(job.getDatasource());

		ResultSet results = sqlProcessor.getResults(conn, job.getQuery(), job
				.getParameters());
		// if we are not outputting this anywhere (must be emailing) then
		// dump this as a temp file
		String outUrl = fs.getFinalUrl(job.getOutputUrl(), job.getPk()
				.getJobName(), job.getPk().getGroup().getGroupName(), job
				.getFileFormat().toString().toLowerCase());
		doReport(results, outUrl, job.getJasperReport(), job.getFileFormat()
				.toString());
		conn.close();

		// send email if need be
		if ((job.getTargetEmailAddress() != null)
				&& (!job.getTargetEmailAddress().isEmpty())) {
			EmailHandler email = new EmailHandler();
			email.sendEmail(job.getTargetEmailAddress(), job.getFromAddress(),
					job.getSmtpServer(), outUrl, job.getPk().getJobName(), job
							.getPk().getGroup().getGroupName());
		}

		// clean up any temp files
		if ((job.getOutputUrl() == null) || (job.getOutputUrl().isEmpty())) {
			fs.deleteFile(outUrl);
		}

	}

	private void doReport(ResultSet results, String url, JasperReport jReport,
			String fileFormat) throws IOException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, JRException,
			SQLException {

		OutputStream os = fs.getOutputStreamForUrl(url);
		boolean isJasperReport = jReport != null;
		if (isJasperReport) {
			JasperRenderer jr = new JasperRenderer();
			jr.generateReport(jReport, results, os, fileFormat);
		} else {
			CSVRenderer csvR = new CSVRenderer();
			csvR.generateReport(results, os);
		}
		os.close();
	}

}
