package binky.reportrunner.ui.actions.dashboard;

import org.apache.commons.beanutils.RowSetDynaClass;

public class DashboardBean {

	private String alertName;
	private Integer alertId;
	private boolean isChart;
	private String chartUID;
	private RowSetDynaClass data;
	private int displayRow;
	public String getAlertName() {
		return alertName;
	}
	public void setAlertName(String alertName) {
		this.alertName = alertName;
	}
	public String getChartUID() {
		return chartUID;
	}
	public void setChartUID(String chartUID) {
		this.chartUID = chartUID;
	}
	public RowSetDynaClass getData() {
		return data;
	}
	public void setData(RowSetDynaClass data) {
		this.data = data;
	}
	public boolean isChart() {
		return isChart;
	}
	public void setChart(boolean isChart) {
		this.isChart = isChart;
	}
	public Integer getAlertId() {
		return alertId;
	}
	public void setAlertId(Integer alertId) {
		this.alertId = alertId;
	}
	public int getDisplayRow() {
		return displayRow;
	}
	public void setDisplayRow(int displayRow) {
		this.displayRow = displayRow;
	}
	
	
}
