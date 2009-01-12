package binky.reportrunner.ui.actions.job;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import net.sf.jasperreports.engine.JRException;
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
import binky.reportrunner.data.RunnerJobParameter_pk;
import binky.reportrunner.data.RunnerJobParameter.DataType;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.scheduler.SchedulerException;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

import com.opensymphony.xwork2.Preparable;


public class SaveJob extends StandardRunnerAction implements Preparable {

	private static final long serialVersionUID = 1L;

	private RunnerJobService jobService;
	private RunnerJob job;
	private File upload;// The actual file
	private String uploadContentType; // The content type of the file
	private String uploadFileName; // The uploaded file name
	private RunnerJobParameterDao parameterDao;
	private String activeTab;
	private List<RunnerJobParameter> parameters;

	private RunnerDataSourceDao dataSourceDao;
	private List<RunnerDataSource> dataSources;

	private String dispatchSaveButton;
	private String groupName;
	private static Logger logger = Logger.getLogger(SaveJob.class);

	public void prepare() throws Exception {
		this.dataSources = dataSourceDao.listDataSources();		
	}

	@Override
	public String execute() throws Exception {

		this.groupName = job.getPk().getGroup().getGroupName();
		String jobName = job.getPk().getJobName();

		if (groupName != null && !groupName.isEmpty()
				&& (jobName != null && !jobName.isEmpty())) {
			// security check
			if (super.getUser().getGroups().contains(groupName)
					|| super.getUser().getIsAdmin()) {
				if (dispatchSaveButton.equals("Add Parameter")) {
					logger.debug("dispatching to add parameter");
					this.doAddParameter();
					return INPUT;
				} else if (dispatchSaveButton.equals("Save")) {
					logger.debug("dispatching to save job");
					this.doSaveJob(jobName, groupName);
				} else if (dispatchSaveButton.startsWith("Delete Parameter")) {
					logger.debug("dispatching to delete parameter " + dispatchSaveButton.substring(18));
					int paramIdx = Integer.parseInt(dispatchSaveButton.substring(17));
					this.deleteParameter(paramIdx);
					return INPUT;
				}
						
			} else {
				SecurityException se = new SecurityException("Group "
						+ groupName + " not valid for user "
						+ super.getUser().getUserName());
				logger.fatal(se.getMessage(), se);
				throw se;
			}

		} else {
			logger.error("groupName or jobName missing");
		}
		return SUCCESS;
	}
	private void deleteParameter(int paramIdx){
		/*for (RunnerJobParameter p : parameters) {
			if (p.getPk().getParameterIdx().equals(paramIdx)) {
				parameters.remove(p);
			}
					
		}*/
		parameters.remove(paramIdx-1);
		job.setParameters(parameters);
		this.activeTab="params";
	}

	private void doAddParameter() {
		this.activeTab = "params";
		if (parameters == null) {
			logger.debug("parameters are null so creating new list");
			parameters = new Vector<RunnerJobParameter>();
		}
		/*int maxIdx = 0;
		for (RunnerJobParameter p : parameters) {
			if (p.getPk().getParameterIdx() > maxIdx) {
				maxIdx = p.getPk().getParameterIdx();
			}
		}	
		maxIdx++;
		*/
		
		RunnerJobParameter parameter = new RunnerJobParameter();
		RunnerJobParameter_pk pk = new RunnerJobParameter_pk();
	
		//pk.setParameterIdx(maxIdx);
		pk.setParameterIdx(parameters.size()+1);
		parameter.setPk(pk);
		logger.debug("created new parameter with index of: " + parameters.size()+1); 
		parameters.add(parameter);
		job.setParameters(parameters);		
	}

	private void doSaveJob(String jobName, String groupName)
			throws JRException, SchedulerException {
		this.activeTab="report";
		// Get the uploaded File And Compile into a jasper report
		if ((upload != null) && upload.isFile() && upload.exists()) {
			JasperDesign jasperDesign = JRXmlLoader.load(upload);
			JasperReport jasperReport = JasperCompileManager
					.compileReport(jasperDesign);
			job.setJasperReport(jasperReport);
		}
		// part of my hack work :(
		job.setParameters(null);
		jobService.addUpdateJob(job);
		// hack to do the tabular stuff with parameters
		if (parameters != null) {

			logger.debug("parameter count:" + parameters.size());

			for (RunnerJobParameter p : this.parameters) {
				if (p != null) {
					logger.debug(p.getParameterValue());
					p.getPk().setRunnerJob_pk(job.getPk());
				} else {
					logger.warn("null parameter");
				}
			}
			parameterDao.updateParametersForJob(jobName, groupName, parameters);
		}
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

	public String getDispatchSaveButton() {
		return dispatchSaveButton;
	}

	public void setDispatchSaveButton(String dispatchSaveButton) {
		this.dispatchSaveButton = dispatchSaveButton;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}
	
	
}
