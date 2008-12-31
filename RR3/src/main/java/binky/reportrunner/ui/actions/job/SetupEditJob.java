package binky.reportrunner.ui.actions.job;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Preparable;

import binky.reportrunner.dao.RunnerDataSourceDao;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.data.RunnerJobParameter.DataType;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class SetupEditJob extends StandardRunnerAction implements Preparable {

	private static final long serialVersionUID = 1L;
	private String jobName;
	private String groupName;
	private RunnerJob job;
	private static final Logger logger = Logger.getLogger(SetupEditJob.class);
	private RunnerJobService jobService;

	private RunnerDataSourceDao dataSourceDao;
	private Integer paramCount;

	private List<RunnerDataSource> dataSources;

	public void prepare() throws Exception {
		dataSources = dataSourceDao.listDataSources();

	}

	// TODO:clean up this mess
	@Override
	public String execute() throws Exception {
		if (groupName != null && !groupName.isEmpty()
				&& (jobName != null && !jobName.isEmpty())) {
			// security check
			if (super.getUser().getGroups().contains(groupName)
					|| super.getUser().getIsAdmin()) {
				job = jobService.getJob(jobName, groupName);
				if (job.getParameters() == null) {
					paramCount = 0;
				} else {
					paramCount = job.getParameters().size();
				}

			} else {
				SecurityException se = new SecurityException("Group "
						+ groupName + " not valid for user "
						+ super.getUser().getUserName());
				logger.fatal(se.getMessage(), se);
				throw se;
			}

		} else if (groupName != null && !groupName.isEmpty()) {
			job = new RunnerJob();
			paramCount = 0;
		} else {
			SecurityException se = new SecurityException("Group not passed");
			logger.fatal(se.getMessage(), se);
		}
		return SUCCESS;
	}

	public final RunnerJobService getJobService() {
		return jobService;
	}

	public final void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}

	public final void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public final String getGroupName() {
		return groupName;
	}

	public final void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public final RunnerJob getJob() {
		return job;
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

	public Integer getParamCount() {
		return paramCount;
	}

	public List<RunnerJob.FileFormat> getFileFormats() {
		return Arrays.asList(RunnerJob.FileFormat.values());
	}
	public List<DataType> getDataTypes() {
		return Arrays.asList(RunnerJobParameter.DataType.values());
	}
}
