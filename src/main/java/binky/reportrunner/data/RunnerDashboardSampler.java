package binky.reportrunner.data;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import binky.reportrunner.data.RunnerDashboardChart.Orientation;
import binky.reportrunner.data.sampling.SamplingData;
import binky.reportrunner.data.sampling.TrendData;

@Entity
(name = "T_SAMPLING")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
	public enum Interval {

		SECOND("Second"), MINUTE("Minute"), HOUR("Hour"), DAY("Day"),MONTH("Month");
		private String displayName;

		Interval(String displayName) {
			this.displayName = displayName;
		}

		public String getName() {
			return name();
		}

		public String getDisplayName() {
			return displayName;
		}

	}
	@Column(name="refreshInterval")
	private Interval interval;
	private Orientation orientation;
	private boolean gridLines;
	private boolean recordTrendData;
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="sampler" ,orphanRemoval=true)
	@OrderBy("timeString")
	private Set<TrendData> trendData;
	
	@Override
	public ItemType getItemType() {
		return ItemType.Sampler;
	}

	private String yAxisLabel;
	private String valueColumn;
	
	private Window window;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="sampler" ,orphanRemoval=true)
	@OrderBy("sampleTime")
	private Set<SamplingData> samplingData;

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	public Set<SamplingData> getSamplingData() {
		return samplingData;
	}

	public void setSamplingData(Set<SamplingData> samplingData) {
		this.samplingData = samplingData;
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

	public Interval getInterval() {
		return interval;
	}

	public void setInterval(Interval interval) {
		this.interval = interval;
	}

	public boolean isRecordTrendData() {
		return recordTrendData;
	}

	public void setRecordTrendData(boolean recordTrendData) {
		this.recordTrendData = recordTrendData;
	}

	public Set<TrendData> getTrendData() {
		return trendData;
	}

	public void setTrendData(Set<TrendData> trendData) {
		this.trendData = trendData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (gridLines ? 1231 : 1237);
		result = prime * result
				+ ((interval == null) ? 0 : interval.hashCode());
		result = prime * result
				+ ((orientation == null) ? 0 : orientation.hashCode());
		result = prime * result + (recordTrendData ? 1231 : 1237);
		result = prime * result
				+ ((valueColumn == null) ? 0 : valueColumn.hashCode());
		result = prime * result + ((window == null) ? 0 : window.hashCode());
		result = prime * result
				+ ((yAxisLabel == null) ? 0 : yAxisLabel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RunnerDashboardSampler other = (RunnerDashboardSampler) obj;
		if (gridLines != other.gridLines)
			return false;
		if (interval != other.interval)
			return false;
		if (orientation != other.orientation)
			return false;
		if (recordTrendData != other.recordTrendData)
			return false;
		if (samplingData == null) {
			if (other.samplingData != null)
				return false;
		} else if (!samplingData.equals(other.samplingData))
			return false;
		if (trendData == null) {
			if (other.trendData != null)
				return false;
		} else if (!trendData.equals(other.trendData))
			return false;
		if (valueColumn == null) {
			if (other.valueColumn != null)
				return false;
		} else if (!valueColumn.equals(other.valueColumn))
			return false;
		if (window != other.window)
			return false;
		if (yAxisLabel == null) {
			if (other.yAxisLabel != null)
				return false;
		} else if (!yAxisLabel.equals(other.yAxisLabel))
			return false;
		return true;
	}


}
