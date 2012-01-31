package binky.reportrunner.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;

@Embeddable
public class SamplingData_pk implements Serializable {

	private RunnerDashboardSampler sampler;
	private Date sampleTime;
	public RunnerDashboardSampler getSampler() {
		return sampler;
	}
	public void setSampler(RunnerDashboardSampler sampler) {
		this.sampler = sampler;
	}
	public Date getSampleTime() {
		return sampleTime;
	}
	public void setSampleTime(Date sampleTime) {
		this.sampleTime = sampleTime;
	}
	public SamplingData_pk(){
		
	}
	public SamplingData_pk(RunnerDashboardSampler sampler, Date sampleTime) {
		super();
		this.sampler = sampler;
		this.sampleTime = sampleTime;
	}
	
	
}
