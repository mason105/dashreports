package binky.reportrunner.data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RunnerJobParameter {
	
	@Id
	private RunnerJobParameter_pk pk;
	private String parameterValue;
	private Boolean isBurstParameter;
	@SuppressWarnings("unchecked")
	private Class parameterClass;
	
	
	@SuppressWarnings("unchecked")
	public Class getParameterClass() {
		return parameterClass;
	}
	@SuppressWarnings("unchecked")
	public void setParameterClass(Class parameterClass) {
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
	public Boolean getIsBurstParameter() {
		return isBurstParameter;
	}
	public void setIsBurstParameter(Boolean isBurstParameter) {
		this.isBurstParameter = isBurstParameter;
	}
	
}
