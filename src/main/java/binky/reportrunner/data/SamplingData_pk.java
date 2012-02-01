package binky.reportrunner.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class SamplingData_pk implements Serializable {


	private static final long serialVersionUID = 9140271137183927058L;
	@ManyToOne(cascade=CascadeType.ALL)
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
		this.sampler = sampler;
		this.sampleTime = sampleTime;
	}
	
	
}
