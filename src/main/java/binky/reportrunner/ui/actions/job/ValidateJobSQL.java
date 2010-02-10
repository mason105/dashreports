package binky.reportrunner.ui.actions.job;

import binky.reportrunner.ui.actions.base.ValidateSQL;

public class ValidateJobSQL extends ValidateSQL {

	private static final long serialVersionUID = 1L;
	private String query;
	private String dataSourceName;
	@Override
	public String execute() throws Exception {
		super.validateSql(query, dataSourceName);
		return SUCCESS;
	}
	
	public String getDataSourceName() {
		return dataSourceName;
	}
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	
	
}
