package binky.reportrunner.ui.actions.job;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import binky.reportrunner.dao.RunnerDataSourceDao;
import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.data.RunnerJob_pk;
import binky.reportrunner.data.RunnerJob.FileFormat;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.util.XWorkList;

public class SaveJob extends StandardRunnerAction implements Preparable {

	private static final long serialVersionUID = 1L;

	private RunnerJobService jobService;
	private RunnerGroupDao groupDao;
	private List<RunnerDataSource> dataSources;

	private String jobName;
	private String groupName;
	private String outputUrl;
	private String dataSourceName;
	private String description;
	private String query;
	private Date startDate;
	private Date endDate;
	private String cronString;
	private Boolean isBurst;
	private String burstQuery;
	private String burstFileNameParameterName;
	private String targetEmailAddress;
	private String alertEmailAddress;
	private File upload;//The actual file
    private String uploadContentType; //The content type of the file
    private String uploadFileName; //The uploaded file name
  
	private FileFormat fileFormat;
	private boolean alertOnSuccess;
	
	private RunnerDataSourceDao dataSourceDao;
	private XWorkList parameterList;

	public void prepare() throws Exception {
		dataSources=dataSourceDao.listDataSources();
	}

	
	@Override
	public String execute() throws Exception {
		RunnerJob_pk pk = new RunnerJob_pk();
		RunnerGroup group = groupDao.getGroup(groupName);
		pk.setGroup(group);
		pk.setJobName(jobName);
		RunnerDataSource ds= dataSourceDao.getDataSource(dataSourceName);
		List<RunnerJobParameter> parameters = new LinkedList<RunnerJobParameter>();	 
		if (parameterList!=null) {
			parameters.addAll(parameterList);
		}
	    //Get the uploadedFileAndCompile
	    JasperDesign jasperDesign = JRXmlLoader.load(upload);
	    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		RunnerJob job = new RunnerJob(pk, outputUrl, ds, description,
				query, startDate, endDate, cronString, isBurst, burstQuery,
				burstFileNameParameterName, targetEmailAddress,
				alertEmailAddress, jasperReport, fileFormat, alertOnSuccess,
				parameters);
		if (groupName != null && !groupName.isEmpty()
				&& (jobName != null && !jobName.isEmpty())) {
			// security check
			if (super.getUser().getGroups().contains(groupName) || super.getUser().getIsAdmin()) {
				jobService.addUpdateJob(job);
			} else {
				SecurityException se = new SecurityException("Group "
						+ groupName + " not valid for user "
						+ super.getUser().getUserName());
				// logger.fatal(se.getMessage(), se);
				throw se;
			}

		}		
		return SUCCESS;
	}
	
	public void setParameterList(XWorkList parameterList) {
		this.parameterList = parameterList;
	}	
	public void setGroupDao(RunnerGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setOutputUrl(String outputUrl) {
		this.outputUrl = outputUrl;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setCronString(String cronString) {
		this.cronString = cronString;
	}

	public void setIsBurst(Boolean isBurst) {
		this.isBurst = isBurst;
	}

	public void setBurstQuery(String burstQuery) {
		this.burstQuery = burstQuery;
	}

	public void setBurstFileNameParameterName(String burstFileNameParameterName) {
		this.burstFileNameParameterName = burstFileNameParameterName;
	}

	public void setTargetEmailAddress(String targetEmailAddress) {
		this.targetEmailAddress = targetEmailAddress;
	}

	public void setAlertEmailAddress(String alertEmailAddress) {
		this.alertEmailAddress = alertEmailAddress;
	}

	
	
	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setFileFormat(FileFormat fileFormat) {
		this.fileFormat = fileFormat;
	}

	public void setAlertOnSuccess(boolean alertOnSuccess) {
		this.alertOnSuccess = alertOnSuccess;
	}


	public final RunnerJobService getJobService() {
		return jobService;
	}

	public final void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
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

	public void setDataSources(List<RunnerDataSource> dataSources) {
		this.dataSources = dataSources;
	}
	public File getUpload() {
		return upload;
	}


	public String getUploadContentType() {
		return uploadContentType;
	}


	public String getUploadFileName() {
		return uploadFileName;
	}
	
}
