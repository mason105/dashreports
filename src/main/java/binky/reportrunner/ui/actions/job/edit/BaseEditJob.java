package binky.reportrunner.ui.actions.job.edit;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import binky.reportrunner.dao.RunnerDataSourceDao;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.data.RunnerJobParameter.DataType;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

import com.opensymphony.xwork2.Preparable;

public abstract class BaseEditJob extends StandardRunnerAction implements
		Preparable {

	protected String jobName;
	protected RunnerJob job;
	protected RunnerJobService jobService;
	protected RunnerDataSourceDao dataSourceDao;
	protected List<RunnerDataSource> dataSources;
	protected File template;// The actual file

	protected String templateContentType; // The content type of the file

	protected String templateFileName; // The uploaded file name

	

	protected String activeTab;

	protected String query;
	protected String burstQuery;

	protected String dataSourceName;

	// private QuartzCronSchedule simpleCron;

	protected List<RunnerJobParameter> parameters;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public List<RunnerDataSource> getDataSources() {
		return dataSources;
	}

	public void prepare() throws Exception {
		dataSources = dataSourceDao.listDataSources();

	}

	public void setDataSources(List<RunnerDataSource> dataSources) {
		this.dataSources = dataSources;
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

	public List<RunnerJobParameter> getParameters() {
		return parameters;
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
	

	public List<RunnerJob.FileFormat> getFileFormats() {
		return Arrays.asList(RunnerJob.FileFormat.values());
	}

	public List<RunnerJob.Template> getTemplateTypes() {
		return Arrays.asList(RunnerJob.Template.values());
	}

	public List<DataType> getDataTypes() {
		return Arrays.asList(RunnerJobParameter.DataType.values());
	}


	public String getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}

	public File getTemplate() {
		return template;
	}

	public void setTemplate(File template) {
		this.template = template;
	}

	public String getTemplateContentType() {
		return templateContentType;
	}

	public void setTemplateContentType(String templateContentType) {
		this.templateContentType = templateContentType;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getBurstQuery() {
		return burstQuery;
	}

	public void setBurstQuery(String burstQuery) {
		this.burstQuery = burstQuery;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

}
