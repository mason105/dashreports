package binky.reportrunner.ui.actions.job;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.RunnerDataSourceDao;
import binky.reportrunner.dao.RunnerJobParameterDao;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.data.RunnerJobParameter.DataType;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.Validation;

@Validation
public class SaveJob extends StandardRunnerAction implements Preparable {

	private static final long serialVersionUID = 1L;

	private RunnerJobService jobService;
	private RunnerJob job;
	private File upload;// The actual file
	private String uploadContentType; // The content type of the file
	private String uploadFileName; // The uploaded file name
	private RunnerJobParameterDao parameterDao;

	// private List<RunnerJobParameter> parameters = new XWorkList(new
	// ObjectFactory(), new XWorkConverter(), null);
	private List<RunnerJobParameter> parameters;
	private RunnerDataSourceDao dataSourceDao;
	private List<RunnerDataSource> dataSources;
	private static Logger logger = Logger.getLogger(SaveJob.class);

	public void prepare() throws Exception {
		this.dataSources = dataSourceDao.listDataSources();
		/*
		 * ConfigurationManager configurationManager = new
		 * ConfigurationManager(); configurationManager.addContainerProvider(new
		 * XWorkConfigurationProvider()); Configuration config =
		 * configurationManager.getConfiguration(); Container container =
		 * config.getContainer(); XWorkConverter conv =
		 * container.getInstance(XWorkConverter.class); ObjectFactory of =
		 * container.getInstance(ObjectFactory.class); this.parameters = new
		 * XWorkList(of, conv, RunnerJobParameter.class);
		 */
	}

	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {

		String groupName = job.getPk().getGroup().getGroupName();
		String jobName = job.getPk().getJobName();
		// Get the uploaded File And Compile into a jasper report
		if ((upload != null) && upload.isFile() && upload.exists()) {
			JasperDesign jasperDesign = JRXmlLoader.load(upload);
			JasperReport jasperReport = JasperCompileManager
					.compileReport(jasperDesign);
			job.setJasperReport(jasperReport);
		}

		if (groupName != null && !groupName.isEmpty()
				&& (jobName != null && !jobName.isEmpty())) {
			// security check
			if (super.getUser().getGroups().contains(groupName)
					|| super.getUser().getIsAdmin()) {
				//part of my hack work :(
				job.setParameters(null);
				jobService.addUpdateJob(job);
				job = jobService.getJob(jobName, groupName);
				
				// hack to do the tabular stuff with parameters
				if (parameters != null) {

					logger.debug("parameter count:" + parameters.size());

					Iterator it = parameters.iterator();
					while (it.hasNext()) {
						RunnerJobParameter p = (RunnerJobParameter) it.next();						
						if (p != null) {
							logger.debug(p.getParameterValue());
							p.getPk().setRunnerJob(job);
						} else {
							logger.warn("null parameter");
						}
					}
					parameterDao.updateParametersForJob(jobName, groupName,
							parameters);

				} else {
					SecurityException se = new SecurityException("Group "
							+ groupName + " not valid for user "
							+ super.getUser().getUserName());
					logger.fatal(se.getMessage(), se);
					throw se;
				}

			}

		}
		return SUCCESS;
	}

	public RunnerJobService getJobService() {
		return jobService;
	}

	public void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}

	public RunnerJob getJob() {
		return job;
	}

	public void setJob(RunnerJob job) {
		this.job = job;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public List<RunnerJobParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<RunnerJobParameter> parameters) {
		this.parameters = parameters;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public RunnerDataSourceDao getDataSourceDao() {
		return dataSourceDao;
	}

	public void setDataSourceDao(RunnerDataSourceDao dataSourceDao) {
		this.dataSourceDao = dataSourceDao;
	}

	public List<RunnerDataSource> getDataSources() {
		return dataSources;
	}
	public RunnerJobParameterDao getParameterDao() {
		return parameterDao;
	}

	public void setParameterDao(RunnerJobParameterDao parameterDao) {
		this.parameterDao = parameterDao;
	}

	public void setDataSources(List<RunnerDataSource> dataSources) {
		this.dataSources = dataSources;
	}

	public List<RunnerJob.FileFormat> getFileFormats() {
		return Arrays.asList(RunnerJob.FileFormat.values());
	}
	public List<DataType> getDataTypes() {
		return Arrays.asList(RunnerJobParameter.DataType.values());
	}

}
