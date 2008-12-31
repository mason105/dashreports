package binky.reportrunner.data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RunnerJobParameter {
	public enum DataType {
		STRING("String"), DATE("Date"), BOOLEAN("Boolean"),INTEGER("Integer"),LONG("Long"),DOUBLE("Double");
		
		private String displayName;

		DataType(String displayName) {
			this.displayName = displayName;
		}

		public String getName() {
			return name();
		}

		public String getDisplayName() {
			return displayName;
		}
	};

	@Id
	private RunnerJobParameter_pk pk;
	private String parameterValue;
	private String parameterBurstColumn;

	public String getParameterBurstColumn() {
		return parameterBurstColumn;
	}

	public void setParameterBurstColumn(String parameterBurstColumn) {
		this.parameterBurstColumn = parameterBurstColumn;
	}


	private DataType parameterType;

	public DataType getParameterType() {
		return parameterType;
	}

	public void setParameterType(DataType parameterType) {
		this.parameterType = parameterType;
	}

	public RunnerJobParameter_pk getPk() {
		return pk;
	}

	public void setPk(RunnerJobParameter_pk pk) {
		this.pk = pk;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

}
