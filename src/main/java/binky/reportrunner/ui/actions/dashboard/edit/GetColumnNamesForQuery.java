package binky.reportrunner.ui.actions.dashboard.edit;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class GetColumnNamesForQuery extends StandardRunnerAction {

	private static final long serialVersionUID = -2392254690021411047L;

	private static final Logger logger = Logger
			.getLogger(GetColumnNamesForQuery.class);

	private String itemQuery;
	private String dataSourceName;
	private DatasourceService dataSourceService;
	private List<String> columnNames;
	private String valueColumnValue;
	private String xaxisColumnValue;
	private String seriesNameColumnValue;
	private String labelColumnValue;

	@Override
	public String execute() throws Exception {

		Connection conn = null;
		try {
			columnNames = new LinkedList<String>();
			columnNames.add("-");

			logger.debug("getting column names for query");

			logger.debug("item query is null = " + (itemQuery == null));
			if (itemQuery == null) {
				super.addActionError("Query passed was null");
				return SUCCESS;
			}

			logger.debug("item.dataSourceName is null = " + (dataSourceName == null));
			if (dataSourceName == null || dataSourceName.isEmpty()) {
				super.addActionError("no datasource selected!");
				return SUCCESS;

			}

			RunnerDataSource rds = dataSourceService
					.getDataSource(dataSourceName);

			if (rds == null) {
				super.addActionError("invalid datasource name!");
				return SUCCESS;
			}

			DataSource ds = dataSourceService.getJDBCDataSource(rds);

			logger.debug("getting a jdbc connection open");
			conn = ds.getConnection();

			Statement stmt = conn.createStatement();

			logger.debug("running sql: " + itemQuery);
			ResultSet rs = stmt.executeQuery(itemQuery);

			logger.debug("rs is null = " + (rs == null));
			logger.debug("rs meta data is null = " + (rs.getMetaData() == null));

			if ((rs == null) || (rs.getMetaData().getColumnCount() == 0)) {
				logger.debug("query failed to return any data");
				super.addActionError("query failed to return any data");
				return SUCCESS;
			} else {

				logger.debug("looks like this sql is valid");

				logger.debug("column count "
						+ rs.getMetaData().getColumnCount());
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					logger.debug("found column "
							+ rs.getMetaData().getColumnName(i));
					columnNames.add(rs.getMetaData().getColumnName(i));
				}

				return SUCCESS;
			}
		} catch (Throwable t) {
			super.addActionError("query failed with exception - "
					+ t.getMessage());
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

	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public DatasourceService getDataSourceService() {
		return dataSourceService;
	}

	public void setDataSourceService(DatasourceService dataSourceService) {
		this.dataSourceService = dataSourceService;
	}

	public String getValueColumnValue() {
		return valueColumnValue;
	}

	public void setValueColumnValue(String valueColumnValue) {
		this.valueColumnValue = valueColumnValue;
	}

	public String getXaxisColumnValue() {
		return xaxisColumnValue;
	}

	public void setXaxisColumnValue(String xaxisColumnValue) {
		this.xaxisColumnValue = xaxisColumnValue;
	}

	public String getSeriesNameColumnValue() {
		return seriesNameColumnValue;
	}

	public void setSeriesNameColumnValue(String seriesNameColumnValue) {
		this.seriesNameColumnValue = seriesNameColumnValue;
	}

	public String getLabelColumnValue() {
		return labelColumnValue;
	}

	public void setLabelColumnValue(String labelColumnValue) {
		this.labelColumnValue = labelColumnValue;
	}

}
