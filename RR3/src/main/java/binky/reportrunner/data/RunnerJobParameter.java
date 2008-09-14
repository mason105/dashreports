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

	//1=String 2=Timestamp 3=Boolean 4=Float 5=Long 6=Double 7=BigDecimal
	private Integer parameterType;

	public Integer getParameterType() {
		return parameterType;
	}

	public void setParameterType(Integer parameterType) {
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
