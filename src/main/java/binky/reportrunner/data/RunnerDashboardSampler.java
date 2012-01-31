package binky.reportrunner.data;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
(name = "T_SAMPLING")
public class RunnerDashboardSampler extends RunnerDashboardItem {
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

	@Override
	public ItemType getItemType() {
		return ItemType.Sampling;
	}

	private String yAxisLabel;
	private String valueColumn;
	
	private Window window;
	
	@OneToMany
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
	
}
