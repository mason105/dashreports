package binky.reportrunner.data;

import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "T_CHART")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RunnerDashboardChart extends RunnerDashboardItem {

	
	private static final long serialVersionUID = 7236467079269602254L;

	public enum Orientation {

		VERTICAL("Vertical"), HORIZONTAL("Horizontal");
		private String displayName;

		Orientation(String displayName) {
			this.displayName = displayName;
		}

		public String getName() {
			return name();
		}

		public String getDisplayName() {
			return displayName;
		}

	}
	
	private Orientation orientation;
	private boolean gridLines;
	private String yLabel;
	private String valueColumn;
	private String seriesNameColumn;
	private String xaxisColumn;

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


	public ChartType getChartType() {
		return chartType;
	}

	public void setChartType(ChartType chartType) {
		this.chartType = chartType;
	}

	
	
	public enum ChartType {

		/*DIAL("Dial"),*/ 
		LINE("Line"),LINE_3D("3D Line"), BAR_3D("3D Bar"), BAR("Bar"), BAR_STACKED("Stacked Bar"), BAR_STACKED_3D("3D Stacked Bar"), AREA("Area"), PIE(
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

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public boolean isGridLines() {
		return gridLines;
	}

	public void setGridLines(boolean gridLines) {
		this.gridLines = gridLines;
	}

	public String getyLabel() {
		return yLabel;
	}

	public void setyLabel(String yLabel) {
		this.yLabel = yLabel;
	}
	
	
}
