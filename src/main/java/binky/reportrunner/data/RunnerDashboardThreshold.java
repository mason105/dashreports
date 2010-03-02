package binky.reportrunner.data;

import javax.persistence.Entity;

@Entity
public class RunnerDashboardThreshold extends RunnerDashboardItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7807770043726494295L;

	private Float upperValue;
	private Float lowerValue;
	private String valueColumn;
	private String labelColumn;

	private ThresholdType type;

	public Float getLowerValue() {
		return lowerValue;
	}

	public void setLowerValue(Float lowerValue) {
		this.lowerValue = lowerValue;
	}

	public ThresholdType getType() {
		return type;
	}

	public void setType(ThresholdType type) {
		this.type = type;
	}

	public final Float getUpperValue() {
		return upperValue;
	}

	public final void setUpperValue(Float upperValue) {
		this.upperValue = upperValue;
	}

	public final String getLabelColumn() {
		return labelColumn;
	}

	public final void setLabelColumn(String labelColumn) {
		this.labelColumn = labelColumn;
	}

	public enum ThresholdType {

		GreaterThan("Greater Than"), LessThan("Less Than");
		private String displayName;

		ThresholdType(String displayName) {
			this.displayName = displayName;
		}

		public String getName() {
			return name();
		}

		public String getDisplayName() {
			return displayName;
		}

	}

	public String getValueColumn() {
		return valueColumn;
	}

	public void setValueColumn(String valueColumn) {
		this.valueColumn = valueColumn;
	}

	@Override
	public ItemType getItemType() {
		return ItemType.Threshold;
	}

	

}
