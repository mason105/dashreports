package binky.reportrunner.ui.actions.datasource;

import binky.reportrunner.dao.RunnerDataSourceDao;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class SaveDataSource extends AdminRunnerAction {

	private static final long serialVersionUID = 1L;

	private String jndiName;
	private String jdbcClass;
	private String jdbcUrl;
	private String username;
	private String password;
	private String dataSourceName;
	private String testConnectionTable;
	private Integer intialPoolSize;
	private Integer minPoolSize;
	private Integer maxPoolSize;

	@Override
	public String execute() throws Exception {
		RunnerDataSource ds = new RunnerDataSource(dataSourceName, jndiName,
				jdbcClass, jdbcUrl, username, password,testConnectionTable, intialPoolSize,minPoolSize,maxPoolSize);

		dataSourceDao.saveUpdateDataSource(ds);

		return SUCCESS;
	}

	private RunnerDataSourceDao dataSourceDao;

	public RunnerDataSourceDao getDataSourceDao() {
		return dataSourceDao;
	}

	public void setDataSourceDao(RunnerDataSourceDao dataSourceDao) {
		this.dataSourceDao = dataSourceDao;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	public void setJdbcClass(String jdbcClass) {
		this.jdbcClass = jdbcClass;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public void setTestConnectionTable(String testConnectionTable) {
		this.testConnectionTable = testConnectionTable;
	}

	public void setIntialPoolSize(Integer intialPoolSize) {
		this.intialPoolSize = intialPoolSize;
	}

	public void setMinPoolSize(Integer minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public void setMaxPoolSize(Integer maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	
	
}
