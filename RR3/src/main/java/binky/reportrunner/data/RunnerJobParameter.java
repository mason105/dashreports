package binky.reportrunner.data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RunnerJobParameter {

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

	private String parameterClass;

	public String getParameterClass() {
		return parameterClass;
	}

	public void setParameterClass(String parameterClass) {
		this.parameterClass = parameterClass;
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
