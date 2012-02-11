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


}
