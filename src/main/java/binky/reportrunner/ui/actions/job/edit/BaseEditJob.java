package binky.reportrunner.ui.actions.job.edit;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.data.RunnerJobParameter.DataType;
import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;
import binky.reportrunner.ui.util.QuartzCronSchedule;

public abstract class BaseEditJob extends StandardRunnerAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String jobName;
	protected RunnerJob job;
	protected RunnerJobService jobService;
	protected ReportRunnerDao<RunnerDataSource, String> dataSourceDao;
	protected DatasourceService dataSourceService;
	private List<RunnerDataSource> dataSources=new LinkedList<RunnerDataSource>();
	protected File template;// The actual file
	
	protected String templateContentType; // The content type of the file

	protected String templateFileName; // The uploaded file name



	protected String query;
	protected String burstQuery;

	protected String dsName;

	protected QuartzCronSchedule simpleCron;

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

	public void setDataSourceDao(
			ReportRunnerDao<RunnerDataSource, String> dataSourceDao) {
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


	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public DatasourceService getDataSourceService() {
		return dataSourceService;
	}

	public void setDataSourceService(DatasourceService dataSourceService) {
		this.dataSourceService = dataSourceService;
	}

	public QuartzCronSchedule getSimpleCron() {
		return simpleCron;
	}

	public void setSimpleCron(QuartzCronSchedule simpleCron) {
		this.simpleCron = simpleCron;
	}

}
