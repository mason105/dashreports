package binky.reportrunner.ui.actions.job;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.RunnerJobDao;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.data.RunnerJobParameter_pk;
import binky.reportrunner.service.impl.DatasourceServiceImpl;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class Parameters extends StandardRunnerAction {

	private static Logger logger = Logger.getLogger(Parameters.class);
	private static final long serialVersionUID = 1L;

	private List<RunnerJobParameter> parameters;
	private String jobName;
	private String groupName;
	private RunnerJobDao jobDao;
	private List<String> columnNames;
	private int mode = 0;
	private int paramIdx;
	private String dataSourceName;
	private DatasourceServiceImpl dataSourceService;
	private String burstQuery;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub

		switch (mode) {
		case 1:
			// add parameter
			addParameter();
			break;
		case 2:
			// delete a parameter
			deleteParameter(paramIdx);
			break;
		default:
			loadParams();			
		}
	/*	if ((burstQuery!=null)&&(!burstQuery.isEmpty())) {
			if ((dataSourceName!=null)&&(!dataSourceName.isEmpty())) {
				populateFieldDropDowns();
			}
		}*/
		return SUCCESS;
	}

	private void loadParams() {
		logger.debug("loading parameters for job " + jobName + " of group " + groupName);
		if ((jobName != null) && (groupName != null)) {
			RunnerJob job = jobDao.getJob(jobName, groupName);
			this.parameters = job.getParameters();
		}
	}
	private void populateFieldDropDowns()  {
		
		columnNames = new LinkedList<String>();
		columnNames.add("-");

		logger.debug("getting column names for query");


		try {

			RunnerDataSource rds = dataSourceService.getDataSource(dataSourceName);

			DataSource ds = dataSourceService.getJDBCDataSource(rds);

			logger.debug("getting a jdbc connection open");
			Connection  conn = ds.getConnection();

			Statement stmt = conn.createStatement();

			logger.debug("running sql: " + burstQuery);
			ResultSet rs = stmt.executeQuery(burstQuery);

			logger.debug("rs is null = " + (rs == null));
			logger.debug("rs meta data is null = "
							+ (rs.getMetaData() == null));

			if ((rs == null) || (rs.isLast())
					|| (rs.getMetaData().getColumnCount() == 0)) {
				logger.warn("query failed to return any data");
				super.addActionError("query failed to return any data");
			} else {

				logger.debug("looks like this sql is valid");

				logger.debug("column count "
						+ rs.getMetaData().getColumnCount());
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					logger.debug("found column "
							+ rs.getMetaData().getColumnName(i));
					columnNames.add(rs.getMetaData().getColumnName(i));
				}

			}
			conn.close();
		} catch (SQLException sqle) {

			logger.warn("query failed with exception");
			super.addActionError("query failed with exception - "
					+ sqle.getMessage());		
		} 
		
	}

	private void deleteParameter(int paramIdx) {
		logger.debug("removing parameter:" + paramIdx + " for job " + jobName + " of group " + groupName);
		parameters.remove(paramIdx - 1);
	}

	private void addParameter() {
		logger.debug("adding parameter to job " + jobName + " of group " + groupName);
		if (parameters == null) {
			logger.debug("parameters are null so creating new list");
			parameters = new LinkedList<RunnerJobParameter>();
		}

		RunnerJobParameter parameter = new RunnerJobParameter();
		RunnerJobParameter_pk pk = new RunnerJobParameter_pk();

		// pk.setParameterIdx(maxIdx);
		pk.setParameterIdx(parameters.size() + 1);
		parameter.setPk(pk);
		logger.debug("created new parameter with index of: "
				+ parameters.size() + 1);
		parameters.add(parameter);
	}

	public List<RunnerJobParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<RunnerJobParameter> parameters) {
		this.parameters = parameters;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public RunnerJobDao getJobDao() {
		return jobDao;
	}

	public void setJobDao(RunnerJobDao jobDao) {
		this.jobDao = jobDao;
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getParamIdx() {
		return paramIdx;
	}

	public void setParamIdx(int paramIdx) {
		this.paramIdx = paramIdx;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public DatasourceServiceImpl getDataSourceService() {
		return dataSourceService;
	}

	public void setDataSourceService(DatasourceServiceImpl dataSourceService) {
		this.dataSourceService = dataSourceService;
	}

	public String getBurstQuery() {
		return burstQuery;
	}

	public void setBurstQuery(String burstQuery) {
		this.burstQuery = burstQuery;
	}

}
