package binky.reportrunner.service.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.naming.NamingException;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

import binky.reportrunner.data.Configuration.ConfigurationType;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.engine.beans.ViewerResults;
import binky.reportrunner.engine.renderers.AbstractRenderer;
import binky.reportrunner.engine.renderers.JasperRenderer;
import binky.reportrunner.engine.renderers.StandardRenderer;
import binky.reportrunner.engine.utils.EmailHandler;
import binky.reportrunner.engine.utils.FileSystemHandler;
import binky.reportrunner.engine.utils.SQLProcessor;
import binky.reportrunner.engine.utils.impl.EmailHandlerImpl;
import binky.reportrunner.engine.utils.impl.FileSystemHandlerImpl;
import binky.reportrunner.engine.utils.impl.SQLProcessorImpl;
import binky.reportrunner.exceptions.RenderException;
import binky.reportrunner.service.ConfigurationService;
import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.service.ReportGenerationService;
import binky.reportrunner.service.ReportService;

public class ReportGenerationServiceImpl implements ReportGenerationService {

	private ReportService reportService;

	private DatasourceService datasourceService;

	private FileSystemHandler fs;

	private ConfigurationService configurationService;

	private static final Logger logger = Logger
			.getLogger(ReportGenerationServiceImpl.class);

	public ReportGenerationServiceImpl() throws IOException {
		this.fs = new FileSystemHandlerImpl();
	}

	@Override
	public void processReport(String jobName, String groupName)
			throws IOException, RenderException, EmailException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException, NamingException,
			NumberFormatException, ParseException {
		RunnerJob job = reportService.getJob(jobName, groupName);
		if (job.getIsBurst()) {
			processBurstedReport(jobName, groupName);
		} else {
			processSingleReport(jobName, groupName);
		}
	}

	@Override
	public List<String> processBurstedReport(String jobName, String groupName)
			throws IOException, RenderException, EmailException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException, NamingException,
			NumberFormatException, ParseException {

		RunnerJob job = reportService.getJob(jobName, groupName);

		DataSource ds = datasourceService
				.getJDBCDataSource(job.getDatasource());

		List<String> fileUrls = new LinkedList<String>();

		AbstractRenderer renderer;

		switch (job.getTemplateType()) {
		case JASPER:
			try {
				renderer = new JasperRenderer(job.getTemplateFile(),
						job.getFileFormat());
			} catch (JRException e) {
				logger.error(e.getMessage(), e);
				throw new RenderException(e.getMessage(), e);
			}
			break;
		default:
			renderer = new StandardRenderer(job.getFileFormat());
		}

		Connection conn = ds.getConnection();

		Map<String, ResultSet> results = getResultsForJob(job, conn);
		try {
			String lastUrl = "";
			for (String fileNameValue : results.keySet()) {
				ResultSet rs = results.get(fileNameValue);
				// if we are not outputting this anywhere (must be emailing)
				// then
				// dump this as a temp file
				String outUrl = fs.getFinalUrl(job.getOutputUrl(), jobName,
						groupName,
						job.getFileFormat().toString().toLowerCase(),
						fileNameValue);

				logger.debug("bursted file being output to: " + outUrl);

				renderReport(rs, fileNameValue, outUrl, renderer);

				if (!lastUrl.equals(outUrl)) {
					fileUrls.add(outUrl);
					lastUrl = outUrl;
				}

			}
		} finally {
			renderer.closeOutputStream();
			conn.close();
		}
		
		String fromAddress=configurationService.getConfigurationItem(ConfigurationType.EMAIL_FROM_ADDRESS).getValue();
		String smtpServer = configurationService.getConfigurationItem(ConfigurationType.EMAIL_SERVER).getValue();
		
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

	@Override
	public String processSingleReport(String jobName, String groupName)
			throws IOException, RenderException, EmailException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException, NamingException,
			NumberFormatException, ParseException {

		RunnerJob job = reportService.getJob(jobName, groupName);

		DataSource ds = datasourceService
				.getJDBCDataSource(job.getDatasource());

		Connection conn = ds.getConnection();
		logger.debug("running single report for:" + groupName + "." + jobName);
		AbstractRenderer renderer;
		switch (job.getTemplateType()) {
		case JASPER:
			try {
				renderer = new JasperRenderer(job.getTemplateFile(),
						job.getFileFormat());
			} catch (JRException e) {
				logger.error(e.getMessage(), e);
				throw new RenderException(e.getMessage(), e);
			}
			break;
		default:
			renderer = new StandardRenderer(job.getFileFormat());
		}

		Map<String, ResultSet> results = getResultsForJob(job, conn);

		// if we are not outputting this anywhere (must be emailing) then
		// dump this as a temp file
		String outUrl = fs.getFinalUrl(job.getOutputUrl(), jobName, groupName,
				job.getFileFormat().toString().toLowerCase());
		try {
			renderReport(results.get("Results"), "Results", outUrl, renderer);
		} finally {
			renderer.closeOutputStream();
			conn.close();
		}
		String fromAddress=configurationService.getConfigurationItem(ConfigurationType.EMAIL_FROM_ADDRESS).getValue();
		String smtpServer = configurationService.getConfigurationItem(ConfigurationType.EMAIL_SERVER).getValue();

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

	public Map<String, RowSetDynaClass> getResultSet(String groupName,
			String jobName) throws NumberFormatException, SQLException,
			ParseException {
		return this.getResultSet(groupName, jobName, null);
	}

	public Map<String, RowSetDynaClass> getResultSet(String groupName,
			String jobName, List<RunnerJobParameter> parameters)
			throws NumberFormatException, SQLException, ParseException {
		RunnerJob job = reportService.getJob(jobName, groupName);
		DataSource ds = datasourceService
				.getJDBCDataSource(job.getDatasource());
		Connection conn = ds.getConnection();
		try {

			// get all the results
			logger.debug("going to get a set of results for job: "
					+ job.getPk().getJobName() + "/"
					+ job.getPk().getGroup().getGroupName());

			if (parameters != null)
				job.setParameters(parameters);

			Map<String, ResultSet> rs = getResultsForJob(job, conn);

			Map<String, RowSetDynaClass> ret = new HashMap<String, RowSetDynaClass>();
			for (String key : rs.keySet()) {
				RowSetDynaClass rsdc = new RowSetDynaClass(rs.get(key), false);
				ret.put(key, rsdc);
			}
			return ret;
		} finally {
			conn.close();
		}
	}

	public Map<String, ViewerResults> getResultsForJob(String jobName,
			String groupName, List<RunnerJobParameter> parameters)
			throws SQLException, NumberFormatException, ParseException,
			RenderException, IOException {

		Map<String, ViewerResults> results = new HashMap<String, ViewerResults>();

		RunnerJob job = reportService.getJob(jobName, groupName);

		AbstractRenderer renderer;
		switch (job.getTemplateType()) {
		case JASPER:
			try {
				renderer = new JasperRenderer(job.getTemplateFile(),
						job.getFileFormat());
			} catch (JRException e) {
				logger.error(e.getMessage(), e);
				throw new RenderException(e.getMessage(), e);
			}
			break;
		default:
			renderer = new StandardRenderer(job.getFileFormat());
		}

		DataSource ds = datasourceService
				.getJDBCDataSource(job.getDatasource());
		Connection conn = ds.getConnection();
		try {
			// get all the results
			logger.debug("going to get a set of results for job: "
					+ job.getPk().getJobName() + "/"
					+ job.getPk().getGroup().getGroupName());

			if (parameters != null)
				job.setParameters(parameters);

			Map<String, ResultSet> rs = getResultsForJob(job, conn);

			logger.debug("converting to dynasets");

			for (String key : rs.keySet()) {
				ResultSet result = rs.get(key);
				int lastRow = 0;
				if ((result != null)) {
					// &&(result.last())){
					// lastRow=result.getRow();
					// result.first();

					String id = UUID.randomUUID().toString();
					renderReport(result, key, "tmp://" + id + ".tmp", renderer);
					results.put(key, new ViewerResults(id));

				}
				logger.debug("Tab name=" + key + " rows=" + lastRow);
			}

			return results;
		} finally {
			renderer.closeOutputStream();
			conn.close();
		}
	}

	public Map<String, ViewerResults> getResultsForJob(String jobName,
			String groupName) throws SQLException, NumberFormatException,
			ParseException, RenderException, IOException {
		return getResultsForJob(jobName, groupName, null);
	}

	@Override
	public Map<RunnerJobParameter, List<Object>> getPossibleParameterValues(
			String jobName, String groupName) throws SQLException,
			NumberFormatException, ParseException {
		Map<RunnerJobParameter, List<Object>> paramValues = new HashMap<RunnerJobParameter, List<Object>>();
		RunnerJob job = reportService.getJob(jobName, groupName);
		DataSource ds = datasourceService.getJDBCDataSource(job.getDatasource());
		Connection conn = ds.getConnection();

		SQLProcessor sqlProcessor = new SQLProcessorImpl();
		if ((job.getIsBurst() == null) || (!job.getIsBurst())) {
			for (RunnerJobParameter p : job.getParameters()) {
				paramValues.put(p, null);
			}
			return paramValues;
		}
		logger.debug("getting burst result for " + jobName + "/" + groupName);
		try {
			ResultSet rs = sqlProcessor.getResults(conn, job.getBurstQuery());

			for (RunnerJobParameter p : job.getParameters()) {

				List<Object> values = new LinkedList<Object>();

				if ((p.getParameterBurstColumn() != null)
						&& (!p.getParameterBurstColumn().isEmpty())) {

					// rs.beforeFirst();
					logger.debug("getting values for parameter: "
							+ p.getDescription());
				
					while (rs.next()) {
						Object value = rs
								.getObject(p.getParameterBurstColumn());
						if (!values.contains(value)) {
							logger.debug("found value: " + value);
							values.add(value);
						}
					}

				}
				paramValues.put(p, values);

			}
		} finally {
			conn.close();
		}
		return paramValues;
	}

	private Map<String, ResultSet> getResultsForJob(RunnerJob job,
			Connection conn) throws SQLException, NumberFormatException,
			ParseException {
		SQLProcessor sqlProcessor = new SQLProcessorImpl();
		Map<String, ResultSet> results = new HashMap<String, ResultSet>();
		List<String> processed = new LinkedList<String>();

		if ((job.getIsBurst() != null) && (job.getIsBurst())) {

			ResultSet burstResults = sqlProcessor.getResults(conn,
					job.getBurstQuery());

			List<RunnerJobParameter> params = job.getParameters();
			while (burstResults.next()) {
				// populate the parameters
				List<RunnerJobParameter> populatedParams = new LinkedList<RunnerJobParameter>();
				String name = "";
				for (RunnerJobParameter param : params) {
					RunnerJobParameter paramNew = new RunnerJobParameter();
					// copy the parameter
					paramNew.setPk(param.getPk());
					paramNew.setParameterBurstColumn(param
							.getParameterBurstColumn());
					paramNew.setParameterType(param.getParameterType());
					paramNew.setParameterValue(param.getParameterValue());
					// is this parameter populated via the burst query
					if ((param.getParameterBurstColumn() != null)
							&& (!param.getParameterBurstColumn().isEmpty())) {
						// Check that the parameter has not already been
						// assigned a value by the user
						if (((param.getParameterValue() == null) || param
								.getParameterValue().isEmpty())) {
							// no value already assigned so pull from DB

							paramNew.setParameterValue(burstResults.getObject(
									param.getParameterBurstColumn()).toString());

							populatedParams.add(paramNew);

							logger.trace("added populated param"
									+ param.getPk().getParameterIdx()
									+ " - value - " + param.getParameterValue());
						} else {
							// value already assigned so carry on using that.
							logger.trace("using overide value"
									+ param.getParameterValue());
							populatedParams.add(paramNew);
						}
					} else {
						// not populated via the bursting query
						logger.trace("standard parameter");
						populatedParams.add(paramNew);
					}
					if (!name.isEmpty()) {
						name = name + "_";
					}
					name = name + paramNew.getParameterValue();
				}

				logger.trace("file/tab is called:" + name);

				// process the query with the results in
				if (!processed.contains(name)) {
					// hack to prevent it repeating its self on the viewer
					ResultSet rs = sqlProcessor.getResults(conn,
							job.getQuery(), populatedParams);
					if (rs.next()) {
						rs.beforeFirst();
						results.put(name, rs);
					}
					processed.add(name);
				}

			}

		} else {
			String name = "Results";

			ResultSet rs;
			if ((job.getParameters() != null)
					&& (job.getParameters().size() > 0)) {
				rs = sqlProcessor.getResults(conn, job.getQuery(),
						job.getParameters());
			} else {
				rs = sqlProcessor.getResults(conn, job.getQuery());
			}

			results.put(name, rs);
		}

		return results;
	}

	private void renderReport(ResultSet results, String label, String url,
			AbstractRenderer renderer) throws RenderException, IOException,
			SQLException {

		renderer.generateReport(results, label, url);
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public void setDatasourceService(DatasourceService datasourceService) {
		this.datasourceService = datasourceService;
	}

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}


}
