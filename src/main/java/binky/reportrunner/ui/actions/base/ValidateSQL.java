package binky.reportrunner.ui.actions.base;

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

public abstract class ValidateSQL extends StandardRunnerAction {

	private static final long serialVersionUID = -2392254690021411047L;

	private static final Logger logger = Logger
			.getLogger(GetColumnNamesForQuery.class);

	private DatasourceServiceImpl dataSourceService;

	private boolean isValid;
	
	public void validateSql(String sql, String dsName) throws Exception {
		Connection conn = null;

		logger.debug("getting column names for query");

		logger.debug("item query is null = " + (sql == null));
		if (sql == null) {
			super.addActionError("Query passed was null");
			this.isValid=false;
			return;
		}

		logger.debug("item.dataSourceName is null = " + (dsName) == null);
		if (sql == null) {
			super.addActionError("Item's datasource passed was null");
			this.isValid=false;
			return;
		}

		RunnerDataSource rds = dataSourceService.getDataSource(dsName);

		DataSource ds = dataSourceService.getJDBCDataSource(rds);

		try {

			logger.debug("getting a jdbc connection open");
			conn = ds.getConnection();

			Statement stmt = conn.createStatement();

			logger.debug("running sql: " + sql);
			ResultSet rs = stmt.executeQuery(sql);

			logger.debug("rs is null = " + (rs == null));
			logger
					.debug("rs meta data is null = "
							+ (rs.getMetaData() == null));

			if ((rs == null) || (rs.getMetaData().getColumnCount() == 0)) {
				logger.warn("query failed to return any data");
				super.addActionError("query failed to return any data");
				this.isValid=false;
				return;
			} else {

				logger.debug("looks like this sql is valid");

				logger.debug("column count "
						+ rs.getMetaData().getColumnCount());
				this.isValid=true;
				return;
			}
		} catch (SQLException sqle) {
			logger.warn("query failed with exception", sqle);
			super.addActionError("query failed with exception - "
					+ sqle.getMessage());
			this.isValid=false;
			return;

		} finally {
			if (conn != null)
				conn.close();
		}

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
