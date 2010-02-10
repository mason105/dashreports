package binky.reportrunner.ui.actions.dashboard.edit;

import binky.reportrunner.ui.actions.base.ValidateSQL;

public class ValidateItemSQL extends ValidateSQL {

	private static final long serialVersionUID = -5133807904179136826L;
	private String itemQuery;
	private String dataSourceName;
	@Override
	public String execute() throws Exception {
		super.validateSql(itemQuery, dataSourceName);
		return SUCCESS;
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

	
	
}
