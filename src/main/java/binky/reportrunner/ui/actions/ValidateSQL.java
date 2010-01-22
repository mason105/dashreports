package binky.reportrunner.ui.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.service.impl.DatasourceServiceImpl;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;
import binky.reportrunner.ui.actions.dashboard.edit.GetColumnNamesForQuery;

public class ValidateSQL extends StandardRunnerAction {

	private static final long serialVersionUID = -2392254690021411047L;

	private static final Logger logger = Logger
			.getLogger(GetColumnNamesForQuery.class);

	private String itemQuery;
	private String dataSourceName;
	private DatasourceServiceImpl dataSourceService;
	private Boolean isValid;

	@Override
	public String execute() throws Exception {
		Connection conn = null;

		logger.debug("getting column names for query");

		logger.debug("item query is null = " + (itemQuery == null));
		if (itemQuery == null) {
			super.addActionError("Query passed was null");
			return SUCCESS;
		}

		logger
				.debug("item.dataSourceName is null = " + (dataSourceName) == null);
		if (dataSourceName == null) {
			super.addActionError("Item's datasource passed was null");
			return SUCCESS;
		}

		RunnerDataSource rds = dataSourceService.getDataSource(dataSourceName);

		DataSource ds = dataSourceService.getJDBCDataSource(rds);

		try {

			logger.debug("getting a jdbc connection open");
			conn = ds.getConnection();

			Statement stmt = conn.createStatement();

			logger.debug("running sql: " + itemQuery);
			ResultSet rs = stmt.executeQuery(itemQuery);

			logger.debug("rs is null = " + (rs == null));
			logger.debug("rs meta data is null = "	+ (rs.getMetaData() == null));

			if ((rs == null) || (rs.getMetaData().getColumnCount() == 0)) {
				logger.warn("query failed to return any data");
				super.addActionError("query failed to return any data");
				this.isValid = false;
				return SUCCESS;
			} else {

				logger.debug("looks like this sql is valid");

				logger.debug("column count "
						+ rs.getMetaData().getColumnCount());

				this.isValid = true;

				return SUCCESS;
			}
		} catch (SQLException sqle) {

			logger.warn("query failed with exception", sqle);
			super.addActionError("query failed with exception - "
					+ sqle.getMessage());
			this.isValid=false;
			return SUCCESS;

		} finally {
			if (conn != null)
				conn.close();
		}

	}

	public String getItemQuery() {
		return itemQuery;
	}

	public void setItemQuery(String itemQuery) {
		this.itemQuery = itemQuery;
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

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

}
