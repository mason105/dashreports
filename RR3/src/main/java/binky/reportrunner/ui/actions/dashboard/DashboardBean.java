package binky.reportrunner.ui.actions.dashboard;

import org.apache.commons.beanutils.RowSetDynaClass;

public class DashboardBean {

	private String alertName;
	private boolean isChart;
	private String chartUID;
	private RowSetDynaClass data;
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
	
	
}
