package binky.reportrunner.data;

import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "T_THRESHOLD")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((labelColumn == null) ? 0 : labelColumn.hashCode());
		result = prime * result
				+ ((lowerValue == null) ? 0 : lowerValue.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result
				+ ((upperValue == null) ? 0 : upperValue.hashCode());
		result = prime * result
				+ ((valueColumn == null) ? 0 : valueColumn.hashCode());
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
		RunnerDashboardThreshold other = (RunnerDashboardThreshold) obj;
		if (labelColumn == null) {
			if (other.labelColumn != null)
				return false;
		} else if (!labelColumn.equals(other.labelColumn))
			return false;
		if (lowerValue == null) {
			if (other.lowerValue != null)
				return false;
		} else if (!lowerValue.equals(other.lowerValue))
			return false;
		if (type != other.type)
			return false;
		if (upperValue == null) {
			if (other.upperValue != null)
				return false;
		} else if (!upperValue.equals(other.upperValue))
			return false;
		if (valueColumn == null) {
			if (other.valueColumn != null)
				return false;
		} else if (!valueColumn.equals(other.valueColumn))
			return false;
		return true;
	}

	

}
