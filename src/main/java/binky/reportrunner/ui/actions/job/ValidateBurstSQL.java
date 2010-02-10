package binky.reportrunner.ui.actions.job;

import binky.reportrunner.ui.actions.base.ValidateSQL;

public class ValidateBurstSQL extends ValidateSQL {

	private static final long serialVersionUID = 1L;
	private String burstQuery;
	private String dataSourceName;
	@Override
	public String execute() throws Exception {
		super.validateSql(burstQuery, dataSourceName);
		return SUCCESS;
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
