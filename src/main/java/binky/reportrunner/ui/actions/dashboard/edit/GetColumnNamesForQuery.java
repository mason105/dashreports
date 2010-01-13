package binky.reportrunner.ui.actions.dashboard.edit;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerDashboardItem;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.service.impl.DatasourceServiceImpl;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class GetColumnNamesForQuery extends StandardRunnerAction {

	private static final long serialVersionUID = -2392254690021411047L;

	private static final Logger logger = Logger
			.getLogger(GetColumnNamesForQuery.class);

	private RunnerDashboardItem item;
	private String dataSourceName;
	private DatasourceServiceImpl datasourceService;
	private List<String> columnNames;

	@Override
	public String execute() throws Exception {
		Connection conn = null;
		try {
			logger.debug("getting column names for query");

			RunnerDataSource rds = datasourceService
					.getDataSource(dataSourceName);
			DataSource ds = datasourceService.getJDBCDataSource(rds);
			conn = ds.getConnection();
			Statement stmt = conn.createStatement();

			ResultSet rs;
			try {
				rs = stmt.executeQuery(item.getAlertQuery());

				if ((rs == null) || (rs.isClosed()) || (rs.isLast())
						|| (rs.getMetaData().getColumnCount() == 0)) {
					logger.warn("query failed to return any info");
					return ERROR;
				}
			} catch (SQLException sqle) {

				logger.warn("query failed with exception", sqle);
				return ERROR;

			} finally {
				conn.close();
			}

			columnNames = new LinkedList<String>();
			logger.debug("column count" + 0);
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				logger.debug("found column "
						+ rs.getMetaData().getColumnName(i));
				columnNames.add(rs.getMetaData().getColumnName(i));
			}

		} finally {
			if (conn != null)
				conn.close();
		}
		return SUCCESS;
	}

	public RunnerDashboardItem getItem() {
		return item;
	}

	public void setItem(RunnerDashboardItem item) {
		this.item = item;
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public DatasourceServiceImpl getDatasourceService() {
		return datasourceService;
	}

	public void setDatasourceService(DatasourceServiceImpl datasourceService) {
		this.datasourceService = datasourceService;
	}

}
