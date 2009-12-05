package binky.reportrunner.data;

import javax.persistence.Entity;

@Entity
public class RunnerDashboardThreshold extends RunnerDashboardItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7807770043726494295L;

	private Float topValue;
	private Float lowerValue;
	
	private ThresholdType type;

	
	
	public Float getTopValue() {
		return topValue;
	}



	public void setTopValue(Float topValue) {
		this.topValue = topValue;
	}



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
	
}
