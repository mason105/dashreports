package binky.reportrunner.ui.actions.job;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.sf.jasperreports.engine.JasperReport;
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

public class SaveJob extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;

	private RunnerJobService jobService;
	private RunnerGroupDao groupDao;

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
	private JasperReport jasperReport;
	private FileFormat fileFormat;
	private boolean alertOnSuccess;
	private List<RunnerJobParameter> parameters;
	private RunnerDataSourceDao dataSourceDao;

	@Override
	public String execute() throws Exception {
		RunnerJob_pk pk = new RunnerJob_pk();
		RunnerGroup group = groupDao.getGroup(groupName);
		pk.setGroup(group);
		pk.setJobName(jobName);
		RunnerDataSource ds= dataSourceDao.getDataSource(dataSourceName);
	    if (parameters==null) parameters = new LinkedList<RunnerJobParameter>();
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

	public void setJasperReport(JasperReport jasperReport) {
		this.jasperReport = jasperReport;
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

	public void setParameters(List<RunnerJobParameter> parameters) {
		this.parameters = parameters;
	}

	public RunnerDataSourceDao getDataSourceDao() {
		return dataSourceDao;
	}

	public void setDataSourceDao(RunnerDataSourceDao dataSourceDao) {
		this.dataSourceDao = dataSourceDao;
	}
	
}
