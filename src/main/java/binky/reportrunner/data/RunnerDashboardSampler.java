package binky.reportrunner.data;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import binky.reportrunner.data.RunnerDashboardChart.Orientation;

@Entity
(name = "T_SAMPLING")
public class RunnerDashboardSampler extends RunnerDashboardItem {

	private static final long serialVersionUID = 6026696206314302405L;

	public enum Window {

		MINUTE("Minute"), HOUR("Hour"), DAY("Day"), WEEK("Week"),MONTH("Month"),YEAR("Year");
		private String displayName;

		Window(String displayName) {
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
	@Override
	public ItemType getItemType() {
		return ItemType.Sampler;
	}

	private String yAxisLabel;
	private String valueColumn;
	
	private Window window;
	
	@OneToMany(cascade=CascadeType.ALL)
	private Collection<SamplingData> data;

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	public Collection<SamplingData> getData() {
		return data;
	}

	public void setData(Collection<SamplingData> data) {
		this.data = data;
	}

	public String getYAxisLabel() {
		return yAxisLabel;
	}

	public void setYAxisLabel(String axisLabel) {
		yAxisLabel = axisLabel;
	}

	public String getValueColumn() {
		return valueColumn;
	}

	public void setValueColumn(String valueColumn) {
		this.valueColumn = valueColumn;
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

	public String getyAxisLabel() {
		return yAxisLabel;
	}

	public void setyAxisLabel(String yAxisLabel) {
		this.yAxisLabel = yAxisLabel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
