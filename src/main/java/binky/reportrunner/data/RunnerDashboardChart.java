package binky.reportrunner.data;

import javax.persistence.Entity;

@Entity(name = "T_CHART")
public class RunnerDashboardChart extends RunnerDashboardItem {

	
	private static final long serialVersionUID = 7236467079269602254L;

	private String yLabel;
	private String valueColumn;
	private String seriesNameColumn;
	private String xaxisColumn;
	private XAxisStep xAxisStep;
	private ChartType chartType;

	public String getYLabel() {
		return yLabel;
	}

	public void setYLabel(String label) {
		yLabel = label;
	}

	public String getValueColumn() {
		return valueColumn;
	}

	public void setValueColumn(String valueColumn) {
		this.valueColumn = valueColumn;
	}

	public String getSeriesNameColumn() {
		return seriesNameColumn;
	}

	public void setSeriesNameColumn(String seriesNameColumn) {
		this.seriesNameColumn = seriesNameColumn;
	}

	public String getXaxisColumn() {
		return xaxisColumn;
	}

	public void setXaxisColumn(String xaxisColumn) {
		this.xaxisColumn = xaxisColumn;
	}

	public XAxisStep getXAxisStep() {
		return xAxisStep;
	}

	public void setXAxisStep(XAxisStep axisStep) {
		xAxisStep = axisStep;
	}

	public ChartType getChartType() {
		return chartType;
	}

	public void setChartType(ChartType chartType) {
		this.chartType = chartType;
	}

	public enum XAxisStep {

		One("One"), Two("Two"), Four("Four"), Eight("Eight"), Sixteen(""), ThirtyTwo("Thirty Two");
		private String displayName;

		XAxisStep(String displayName) {
			this.displayName = displayName;
		}

		public String getName() {
			return name();
		}

		public String getDisplayName() {
			return displayName;
		}

	}
	
	public enum ChartType {

		/*DIAL("Dial"),*/ 
		LINE("Line"), STACKED_BAR("Stacked Bar"), CLUSTERED_BAR("Clustered Bar"), AREA("Area"), PIE(
				"Pie");

		private String displayName;

		ChartType(String displayName) {
			this.displayName = displayName;
		}

		public String getName() {
			return name();
		}

		public String getDisplayName() {
			return displayName;
		}

	}

	@Override
	public ItemType getItemType() {
		return ItemType.Chart;
	}
}
